package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// 连接点：可以被 AOP 控制的方法
// 通知：抽取共性功能形成的方法叫通知Advice。例如recordTime就是通知方法
// 切入点：实际被 AOP 控制的方法。   切入点 < 连接点  切入点表达式：@Around("execution(* com.itheima.service.impl.*.*(..))")
// 切面： 等于 通知 + 切入点。含义是：对指定的方法，执行通知方法
// 目标对象：通知方法 实际 应用的对象，也就是切入点指定类中的对象。

// 对于目标类的list方法，首先通过动态代理创建proxy类 实现与目标类相同的接口xxxService，并通过IOU容器将proxy对象注入controller中(鸠占鹊巢)
// proxy对象的list方法中调用目标类的list方法，并且在上下面补全通知的逻辑，就可以记录方法的耗时啦

// 说白了，针对每个目标类的方法例如list, 通过动态代理构建出一个代理类，代理类的每个方法 是 目标类方法 的增强版

// 执行流程: controller -> xxxService -> proxy对象注入xxxService -> 调用proxy对象的list方法(该list就是通知方法) -> 调用目标类的list方法 -> 继续回到通知方法，执行下面逻辑


@Slf4j
//@Aspect // 表示当前类是一个AOP类
@Component
public class RecordTimeAspect {
    @Around("execution(* com.itheima.service.impl.*.*(..))")

    // AOP程序：面对特定方法根据业务需要编程
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        // 1、记录方法运行的开始时间
        long begin = System.currentTimeMillis();

        // 2、执行原始的方法
        Object result = pjp.proceed(); // result 原始方法返回值


        // 3、记录方法运行的结束时间
        long end = System.currentTimeMillis();
        log.info("方法{} 执行耗时: {}ms",pjp.getSignature() ,end - begin);
        return result;
    }

}
