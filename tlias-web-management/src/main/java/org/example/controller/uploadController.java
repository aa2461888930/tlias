package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class uploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    /*
       本地磁盘存储
       使用 MultipartFile file 接收文件
     */
//    @PostMapping("/upload")
//    public Result upload(String name,
//                         Integer age,
//                         MultipartFile file) throws IOException {
//        log.debug("开始上传...{},{},{}", name, age, file);
//        String originalName = file.getOriginalFilename();
//
//        String fileName = UUID.randomUUID().toString();
//        String extension = originalName.substring(originalName.lastIndexOf("."));
//        String newFileName = fileName + extension;
//
//        file.transferTo(new File("/Users/mac/Downloads",newFileName));
//        return Result.success();
//    }

    @PostMapping("/upload") // 接收文件：JSON字段名 与 方法形参名保持一致，即简单参数接收
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传: {}", file.getOriginalFilename());
        // 将文件交给阿里云OSS进行上传
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传到OSS的url：{}",url);
        return Result.success(url); // 返回文件url给前端，展示给用户
    }
}
