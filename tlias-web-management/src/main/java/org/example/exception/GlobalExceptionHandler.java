package org.example.exception;

// 全局异常处理器

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 使用 全局异常处理器 原因： 统一响应Result对象，从而方便前端渲染展示。
@Slf4j
@RestControllerAdvice // 声明本类是 全局异常处理器
public class GlobalExceptionHandler {

    @ExceptionHandler // 声明本方法是 异常处理器
    public Result handleExcpetion(Exception e){ // 捕获所有的异常
        log.error("程序出错啦～：", e);
        return Result.error("出错啦，请联系管理员～～");
    }

    // 先精准，再宽泛。顺序：先找子类异常，再找父类异常
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){ // 专门处理重复异常：数据库字段违反唯一约束就出现
        // 从报错信息中获取 重复字段信息，并返回前端：从而避免用户输入一个重复手机号，整个服务器奔溃了
        log.error("程序错误啦~", e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2] + "已存在");
    }

    @ExceptionHandler
    public Result handleInvalidDelete(InvalidDeleteException e){
        log.error("程序错误啦~", e);
        return Result.error(e.getMessage());
    }
}
