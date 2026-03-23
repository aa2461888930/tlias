package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
//@Component // 注释 component， 则关掉拦截器
public class TokenInterceptor implements HandlerInterceptor {

    // 执行顺序： 先过滤器Filter(更底层)，再拦截器Interceptor(sprig框架)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1、获取到请求路径
        String requestURI = request.getRequestURI();

        // 2、判断是否是登录请求，如果是 /login, 则放行
        if (requestURI.contains("login")){
            log.info("登录请求，放行");
            return true; // 放行
        }

        // 3、获取请求头中的token
        String token = request.getHeader("token");

        // 4、判断token是否存在，如果不存在，说明用户没有登录，返回错误信息(401状态码)
        if (token == null || token.isEmpty()){
            log.info("令牌为空，响应401");
            response.setStatus(401);
            return false; // 不放行
        }

        // 5、如果token存在，检验令牌，如果校验失败 -> 返回错误信息(401状态码)
        try{
            JwtUtils.parseJwt(token);
        }catch (Exception e){
            log.info("令牌非法，响应401");
            response.setStatus(401);
            return false; // 不放行
        }


        // 6、校验通过，放行
        log.info("令牌合法，放行");
        return true; // 放行
    }
}
