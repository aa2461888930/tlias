package com.itheima;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)         // 修饰注解的注解，声明注解类在哪生效，这里是在“方法”上生效
@Retention(RetentionPolicy.RUNTIME) // 声明在运行是生效
public @interface LogOperation {

}
