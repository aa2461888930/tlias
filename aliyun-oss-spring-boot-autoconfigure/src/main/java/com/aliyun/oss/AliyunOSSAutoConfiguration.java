package com.aliyun.oss;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * AliyunOSS的自动配置类
 */
// 相比@Import多了语义信息：加载一组以特定前缀开头的配置项。并且多了配置属性的相关校验和元数据绑定，让排查BUG更简单
@EnableConfigurationProperties(AliyunOSSProperties.class)
//@Import(AliyunOSSProperties.class)
@AutoConfiguration // 使用@AutoConfiguration相比@Configuration 可以更好控制依赖加载顺序
public class AliyunOSSAutoConfiguration {

    @ConditionalOnMissingBean // 当不存在 aliyunOSSOperator 这个bean,再去创建
    @Bean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties){
        return new AliyunOSSOperator(aliyunOSSProperties);
    }


}
