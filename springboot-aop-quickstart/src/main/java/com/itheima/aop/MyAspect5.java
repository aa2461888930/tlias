package com.itheima.aop;

import com.itheima.LogOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(10)
@Slf4j
@Component
@Aspect
public class MyAspect5 {
//     * 出现位置：返回类型、包名、方法名、方法名模糊匹配、参数
//     ..出现位置：只能包名、参数
//     ..可以匹配“多级”任意包名，    *只能匹配”该级“任意包名
//     ..可以匹配“多个”任意类型参数， *只能匹配 1 个任意类型参数

//     v1:完整版本
//    @Before("execution(public void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
//    v2: 省略权限修饰符
//    @Before("execution(void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
//    v3: 省略包名.类名(不建议，全包扫描、性能低)、权限修饰符
//    @Before("execution(void delete(java.lang.Integer))")
//    v4: * 返回值类型任意、省略权限修饰符
//    @Before("execution(* com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
//    v5: * 返回值类型任意、该级包名是任意的、该级类名是任意的、该方法名是任意的；省略权限修饰符。有可能匹配多个方法
//    @Before("execution(* com.*.service.impl.*.*(java.lang.Integer))")
//    v6: * 返回值类型任意、该级包名是任意的、该级类名是任意的、该方法名是任意的、形参是任意类型(只有1个)；省略权限修饰符。有可能匹配多个方法
//    @Before("execution(* com.*.service.impl.*.*(*))")
//    v7: * 返回值类型任意、该级包名是任意的、该级类名是任意的、该方法名以del开头、形参是任意类型(只有1个)；省略权限修饰符。有可能匹配多个方法
//    @Before("execution(* com.*.service.impl.*.del*(*))")
//    v8: * 返回值类型任意、该级包名是任意的、该级类名是任意的、该方法名以e结尾、形参是任意类型(只有1个)；省略权限修饰符。有可能匹配多个方法
//    @Before("execution(* com.*.service.impl.*.*e(*))")
//    v9: * 返回值类型任意、..表示包名是任意级、该级类名是任意的、该方法名以e结尾、形参是任意类型任意个；省略权限修饰符。有可能匹配多个方法
//    @Before("execution(* com..service..*.*(..))")

//    需求：匹配delete、list方法
//    v1: 使用 execution方法，需要 || 连接
//    @Before("execution(* com.itheima.service.impl.DeptServiceImpl.list(..)) || " +
//            "execution(* com.itheima.service.impl.DeptServiceImpl.delete(..))")
//    v2: 只要在 delete、list 方法上面加上 @LogOperation 就可以定位到啦，相比上面更简洁
    @Before("@annotation(com.itheima.LogOperation)")
    public void before(){
        log.info("MyAspect4 -> before ...");
    }


}
