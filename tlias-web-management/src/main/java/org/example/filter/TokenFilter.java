package org.example.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.CurrentHolder;
import org.example.utils.JwtUtils;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*") // @WebFilter声明为过滤器，拦截所有请求
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // 1、获取到请求路径
        String requestURI = httpServletRequest.getRequestURI();

        // 2、判断是否是登录请求，如果是 /login, 则放行
        if (requestURI.contains("login")){
            log.info("登录请求，放行");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 3、获取请求头中的token
        String token = httpServletRequest.getHeader("token");

        // 4、判断token是否存在，如果不存在，说明用户没有登录，返回错误信息(401状态码)
        if (token == null || token.isEmpty()){
            log.info("令牌为空，响应401");
            httpServletResponse.setStatus(401);
            return;
        }

        // 5、如果token存在，检验令牌，如果校验失败 -> 返回错误信息(401状态码)
        try{
            Claims claims = JwtUtils.parseJwt(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId); // 将该连接的登录用户信息存入当前线程局部变量
            log.info("当前登录员工ID: {}, 将其存入ThreadLocal中", empId);
        }catch (Exception e){
            log.info("令牌非法，响应401");
            httpServletResponse.setStatus(401);
            return;
        }


        // 6、校验通过，放行
        log.info("令牌合法，放行");
        filterChain.doFilter(httpServletRequest, httpServletResponse);

        // 7、放行完，之后再删除ThreadLocal的信息(防止内存泄露)
        CurrentHolder.remove(); // 当前线程存，只能由当前线程删除
    }
}
