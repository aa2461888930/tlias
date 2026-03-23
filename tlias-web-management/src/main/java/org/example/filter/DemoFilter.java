package org.example.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*") // 拦截所有请求
@Slf4j
public class DemoFilter implements Filter {
    // 初始化方法，web服务器启动的时候执行，只执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init 初始化方法...");
    }

    // 拦截到请求之后，执行doFilter方法，并执行多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到了请求... 放行前");

        // 拦截原理：需要登录之后，才会产生token, 才具备拦截的必备条件
        // 因此：对于登录、注册这些 请求路径, 暂时没有产生token, 也就不用拦截了
        //      对于访问网站资源的 请求路径，规定需要登录之后才能看的，已经有token,需要拦截

        // 放行
        filterChain.doFilter(servletRequest, servletResponse);

        log.info("拦截到了请求... 放行后");
    }
    // 销毁方法，web服务器关闭的时候执行，只执行一次.资源释放
    @Override
    public void destroy() {
        log.info("destroy 销毁方法 ...");
    }
}
