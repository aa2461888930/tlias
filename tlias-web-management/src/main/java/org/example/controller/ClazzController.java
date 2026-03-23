package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.anno.Log;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    // 2.1
    @GetMapping("/clazzs")
    public Result Page(ClazzQueryParam clazzQueryParam){
        log.info("班级分页查询： {}", clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @Log // 自定义注解，用于标识AOP作用的范围，记录增删改日志
    // 2.3
    @PostMapping("/clazzs")
    public Result add(@RequestBody  Clazz clazz){
        log.info("添加班级: {}", clazz);
        clazzService.add(clazz);
        return Result.success();
    }

    // 2.4
    @GetMapping("/clazzs/{id}")
    public Result getClazzById(@PathVariable Integer id){
        log.info("查询班级id: {}", id);
        Clazz clazz = clazzService.getClazzById(id);
        return Result.success(clazz);
    }

    @Log // 自定义注解，用于标识AOP作用的范围，记录增删改日志
    // 2.5
    @PutMapping("clazzs")
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级: {}",clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @Log // 自定义注解，用于标识AOP作用的范围，记录增删改日志
    // 2.6
    @DeleteMapping("/clazzs/{id}")
    public Result deleteById(@PathVariable Integer id) throws Exception {
        log.info("删除班级id: {}", id);
        clazzService.deleteById(id);
        return Result.success();
    }


    // 3.1
    @GetMapping("/clazzs/list")
    public Result getAllClazzList(){
        log.info("查询所有班级");
        List<Clazz> clazzList = clazzService.getAllClazzList();
        return Result.success(clazzList);
    }



}
