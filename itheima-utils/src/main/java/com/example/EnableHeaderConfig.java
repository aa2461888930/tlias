package com.example;

import org.springframework.context.annotation.Import;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义starter模块

// 1、stater模块是用于依赖管理的，只需要保留pom.xml即可，不需要任何代码文件

// 2.1、autoconfigure模块是给其他springboot使用, 启动类、properties配置文件、测试包都删掉
// 2.2、在 starter 引用 autoconfigure 依赖, 之后其他项目只需引用 starter 即可

// 3、在 autoconfigure 模块完成 AliyunOSSOperator bean的自动配置
    // 3.1 因为启动类只扫描当前包及子包，加上@Component也无法将当前类注入成bean,因此去掉@Component
    // 3.2 增加 resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports文件
    //     并记录AliyunOSSAutoConfiguration的全类名
    // 3.3 在新的项目的pom.xml中引用 starter 依赖即可

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyImportSelector.class)
public @interface EnableHeaderConfig {
}
