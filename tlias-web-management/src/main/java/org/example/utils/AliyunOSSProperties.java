package org.example.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.oss") // 使用对象封装配置，对 @Value 的优化
@Data
@Component
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}
