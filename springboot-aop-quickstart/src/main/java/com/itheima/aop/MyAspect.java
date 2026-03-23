package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// 在springboot中，配置文件优先级： 命令行参数 > java系统属性 > properties > yml > yaml
// 外部配置的优先级 > 内部配置的优先级

@Slf4j
@Component
//@Aspect // 声明是切面类
public class MyAspect {
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt() {};

    // 前置通知 - 目标方法运行之前运行
    @Before("pt()") // 引用上面的切入点表达式
    public void before(JoinPoint joinPoint){
        log.info("before ...");

        Object target = joinPoint.getTarget();

    }

    // 环绕通知 - 目标方法运行之前、之后运行
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around .... before ...");

        Object result = pjp.proceed();

        log.info("around... after ...");
        return result;
    }

    // 后置通知 - 目标方法运行之后运行，无论是否有异常都运行
    @After("pt()")
    public void after(){
        log.info("after ...");
    }

    // 返回后通知 - 目标方法运行之后运行，出现异常不会运行
    @AfterReturning("pt()")
    public void afterReturning(){
        log.info("afterReturning ...");
    }

    // 异常后运行 - 目标方法运行之后运行，只有出现异常才运行
    @AfterThrowing("pt()")
    public void afterThrowing(){
        log.info("afterThrowing ...");
    }





}
