package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    // 简单参数接收：相同形参名字。springboot会自动解析并类型转换
    // @RequestParam 指定形参默认值   @DateTimeFormat ：指定前端传来的日期格式
    // 简单参数过多，不方便后期维护和扩展，因此封装到对象中，用对象接收(前端字段名 与 成员变量名字 一致)
    @GetMapping("/emps")
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询: {}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    // 接收前端JSON字段，并解析封装到Emp对象中，只需字段名与成员变量一致。就算是数组字段也能接收
    // 这些用于接收前端JSON数据的“类”，不用提供构造方法，而是提供get/set函数，从而新增/减少成员变量很方便
    @PostMapping("/emps")
    public Result save(@RequestBody Emp emp){
        log.info("保存员工: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    // 简单请求参数
    // 使用 数组 接收，可以不用@RequestParam注解
    // 使用 集合 复杂数据类型接收，需要@RequestParam注解
    @DeleteMapping("/emps")
    public Result delete(@RequestParam List<Integer> ids){
//        String[] strings = ids.split(",");
//        List<Integer> idList = List.of(strings).stream().map(Integer::parseInt).collect(Collectors.toList());
        log.info("删除员工: {}", ids);
        empService.delete(ids);
        return Result.success();
    }

    @GetMapping("/emps/list")
    public Result getAllEmpList(){
        log.info("查询全部员工");
        List<Emp> empList = empService.getAllEmpList();
        return Result.success(empList);
    }

    @GetMapping("/emps/{id}") // 使用 @PathVariable 绑定路径参数给 形参 id
    public Result get(@PathVariable Integer id){
        log.info("查询回显: {}", id);
        Emp emp = empService.getEmpById(id);
        return Result.success(emp);
    }

    @PutMapping("/emps")
    public Result update(@RequestBody Emp emp){ // @RequestBody 参数放在请求body
        log.info("修改员工: {}", emp);
        empService.update(emp);
        return Result.success();
    }




}
