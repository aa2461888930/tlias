package org.example.service;

import org.example.pojo.JobOption;
import org.example.pojo.StudentCount;
import org.example.pojo.StudentDegree;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ReportService {
    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    StudentCount getStudentData();

    List<StudentDegree> getStudentDegreeData();
}
