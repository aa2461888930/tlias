package com.itheima;

import cn.hutool.core.io.FileUtil;
import com.example.HeaderConfig;
import com.example.HeaderParser;
import com.example.TokenParser;
import com.google.gson.Gson;
import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.File;

@SpringBootTest
class SpringbootWebTests {
    @Autowired
    private ApplicationContext applicationContext; // 将IOU容器bean注入进来

    // 默认bean是单例的，在IOU容器只存在一个对象
    // 默认 单例的 bean 是项目启动时 创建的

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator; // 注入第三方jar bean,使用 @Bean + 方法

    @Autowired
    private Gson gson; // springboot 自动配置第三方bean,我们省去写配置类

    @Autowired
    private HeaderParser headerParser;

//    @Autowired
//    private TokenParser tokenParser;



    @Test
    public void testJson(){
        System.out.println(gson.toJson(Result.success(null)));
    }

//    @Test
//    public void testTokenParser(){
//        tokenParser.parse();
//    }

    @Test
    public void testHeaderParser(){
        headerParser.parse();
    }


    @Test
    public void testScope(){
        for(int i = 0; i < 1000; i++){
            // 获取 bean 对象，注意 bean 名字 类名首字母小写
            Object deptController = applicationContext.getBean("deptController");
            System.out.println(deptController);
        }
    }

    @Test
    public void testUpload() throws Exception {
        File file = new File("/Users/mac/Downloads/7a8f2b1e8c4d1b725956bfe58505a9f6.jpg");
        String url = aliyunOSSOperator.upload(FileUtil.readBytes(file), "7a8f2b1e8c4d1b725956bfe58505a9f6.jpg");
        System.out.println(url);
    }


}
