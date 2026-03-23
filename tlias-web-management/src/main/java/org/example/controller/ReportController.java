package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.JobOption;
import org.example.pojo.Result;
import org.example.pojo.StudentCount;
import org.example.pojo.StudentDegree;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
//@RequestMapping("")  // 方法都有report路径，统一拿出来
@RestController
public class ReportController {
    // 一个重要结论：Controller, Service, Mapper通常是一一对应的。
    // 也就是说：定义1个Controller，就要定义1个Service，就要定义1个ServiceImpl,就要定义1个Mapper

    @Autowired
    private ReportService reportService;

    // 5.1
    @GetMapping("/report/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别人数");
        List<Map<String, Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    /**
     * 5.2 统计员工职位人数
     */
    @GetMapping("/report/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    // 5.3
    @GetMapping("/report/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("学员学历统计");
        List<StudentDegree> list = reportService.getStudentDegreeData();
        return Result.success(list);
    }


    // 5.4
    @GetMapping("/report/studentCountData")
    public Result getStudentData(){
        log.info("班级人数统计");
        // 核心：在controller最终是要返回一个对象，从而让springboot转为JSON给前端解析
        StudentCount studentCount = reportService.getStudentData();
        return Result.success(studentCount);
    }



}
