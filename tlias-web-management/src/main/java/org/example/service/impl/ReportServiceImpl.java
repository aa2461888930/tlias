package org.example.service.impl;


import org.example.mapper.EmpMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.JobOption;
import org.example.pojo.StudentCount;
import org.example.pojo.StudentDegree;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public JobOption getEmpJobData() {
        // 1、调用mapper接口，获取统计数据
        List<Map<String, Object>> maps = empMapper.countEmpJobData(); // map: pos=教研主管, num=1

        // 2、封装结构，并返回
        List<Object> jobList = maps.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = maps.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public StudentCount getStudentData() {
        List<Map<String, Object>> maps = studentMapper.countStudentData();
        List<Object> clazzList = maps.stream().map(map -> map.get("clazz_name")).toList();
        List<Object> dataList = maps.stream().map(map -> map.get("data")).toList();

        return new StudentCount(clazzList, dataList);
    }

    @Override
    public List<StudentDegree> getStudentDegreeData() {
        return studentMapper.getStudentDegreeData();
    }
}
