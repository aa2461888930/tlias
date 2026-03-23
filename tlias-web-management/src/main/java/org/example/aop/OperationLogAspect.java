package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.mapper.OperateLogMapper;
import org.example.pojo.OperateLog;
import org.example.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect // 声明为 AOP 类
@Component
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(org.example.anno.Log)") // 自定义注解的全类名，指定通知方法作用的方法范围
    public Object logOperation(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object result = pjp.proceed();

        // 计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        // 构建日志实体
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getCurrentUserId());
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setClassName(pjp.getTarget().getClass().getName());
        operateLog.setMethodName(pjp.getSignature().getName());
        operateLog.setMethodParams(Arrays.toString(pjp.getArgs()));
        operateLog.setReturnValue(result != null ? result.toString() : "void");
        operateLog.setCostTime(costTime);

        // 保存日志
        log.info("记录操作日志: {}", operateLog);
        operateLogMapper.insert(operateLog);

        return result;
    }

    // 原理：jwt令牌中存储 当前登录的员工信息
    private Integer getCurrentUserId(){
        // 重磅：获取当前线程的连接用户的id。Filter存 -> AOP用 -> Filter删
        return CurrentHolder.getCurrentId();
    }

}
