package com.aliyun.oss;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "aliyun.oss") // 使用对象封装配置，对 @Value 的优化
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "AliyunOSSProperties{" +
                "endpoint='" + endpoint + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
