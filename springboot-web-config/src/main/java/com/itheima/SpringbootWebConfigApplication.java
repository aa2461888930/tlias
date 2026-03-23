package com.itheima;

import com.example.EnableHeaderConfig;
import com.example.HeaderConfig;
import com.example.MyImportSelector;
import com.example.TokenParser;
import com.itheima.utils.AliyunOSSOperator;
import com.itheima.utils.AliyunOSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

// @SpringBootApplication具备组件扫描功能，但是默认扫描的是启动类所在包及其子包，也就是扫描本模块的com.itheima，扫描不了下载第三方itheima-utils模块下的内容
// 因此在itheima-utils中的类加上@Component也是无法被该启动类扫描到的，也就是无法注入bean

// 自动配置方案一：(下载第三方代码模块放到我们项目中，同样需要写在pom.xml中)自动配置实现方案一：@Component + @ComponentScan
//@ComponentScan(basePackages = {"com.example", "com.itheima"}) // 手动指定包扫描的范围，从而可以注入”第三方包的bean“

// 自动配置方案二：@Import + 普通类
//@Import(TokenParser.class)

// 自动配置方案三：@Import + 配置类（配置类中所有bean都会注入IOU容器中）
//@Import(HeaderConfig.class) // 配置类，就是AliyunOperator中通过 @Configuration + @Bean + 方法 注入第三方bean

// 自动配置方案四：@Import + ImportSelector的实现类   批量导入多个类到IOU容器，缺点：开发人员必须知道得哪些类要注入，增加开发成本
//@Import(MyImportSelector.class)

// 自动配置方案五：第三方提供的注解。    第三方依赖提供者最知道哪些类重要，该怎么导入，提供 EnableXXXX类，提供给普通开发者一次性导入该依赖的核心。解决方案四缺点
@EnableHeaderConfig

// 1、@SpringBootApplication自带@Configuration注解，也是配置类,因此可以通过 "@Bean+方法" 注入bean
// 2、@SpringBootApplication自带@ComponentScan注解，所以具有组件扫描的功能，默认是当前包机器子包
// 3、@SpringBootApplication自带@EnableAutoConfiguration注解，@EnableAutoConfigurationz自带@Import(xxxImportSelector.class)具有批量导入IOU类功能
// 其中有一个getCandidateConfigurations方法，读取一个”.imports“结尾的文件，该文件的内容是 ”所有要注入类的全类名“，从而springboot可以将第三方依赖注入成bean
// 其中知名第三方依赖例如GsonAutoConfiguration类，其实有@Configuration注解，就是 "@Bean + 方法" 从而注入bean

// springboot自动配置原理：@SpringBootApplication --> @EnableAutoConfiguration --> @Import --> ImportSelector实现类.class批量导入
//                  --> .imports文件 --> @Conditional根据条件将依赖中的bean导入IOU容器

@SpringBootApplication
public class SpringbootWebConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebConfigApplication.class, args);
    }


}
