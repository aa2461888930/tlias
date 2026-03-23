package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ArrayUtil;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;
import org.example.service.ClazzService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClazzService clazzService;

    // 3.2
    @GetMapping("/students")
    public Result page(StudentQueryParam studentQueryParam){
        log.info("学员条件分页查询接口: {}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    // 3.3
    @PostMapping("/students")
    public Result add(@RequestBody Student student){
        log.info("增加学员信息 {}", student);
        studentService.add(student);
        return Result.success();
    }

    // 3.4
    @GetMapping("/students/{id}")
    public Result getStudentById(@PathVariable Integer id){
        log.info("根据ID查询学员, {}", id);
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }

    // 3.5
    @PutMapping("/students")
    public Result update(@RequestBody Student student){
        log.info("修改学员:{}", student);
        studentService.updateById(student);
        return Result.success();
    }

    // 3.6
    // 简单请求参数
    // 使用 数组 接收，可以不用@RequestParam注解
    // 使用 集合 复杂数据类型接收，需要@RequestParam注解
    @DeleteMapping("/students/{ids}")
    public Result deleteByIds(@PathVariable String ids){
        log.info("删除学员id: {}", ids);
        // 字符串分割 + 转换
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        studentService.deleteByIds(idList);
        return Result.success();
    }

    // 3.7
    @PutMapping("/students/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id,
                                  @PathVariable Integer score){
        log.info("违纪处理, {}, {}", id, score);
        studentService.updateViolation(id, score);
        return Result.success();
    }



}
