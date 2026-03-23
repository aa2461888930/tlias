package org.example.config;

import org.apache.el.parser.Token;
import org.example.interceptor.DemoInterceptor;
import org.example.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类。用于配置interceptor拦截的路径
 */
@Configuration  // @Configuration自带@Component,交给IOU容器管理
public class WebConfig implements WebMvcConfigurer {
//    @Autowired
//    private DemoInterceptor demoInterceptor;
//    @Autowired
//    private TokenInterceptor tokenInterceptor;
//
//
//    // 注册器，并指定拦截路径
//    // 注意：* 拦截1级路径； ** 拦截任意级路径
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**") //拦截所有请求
//                .excludePathPatterns("/login"); // 不拦截哪些请求
//
//    }
}
