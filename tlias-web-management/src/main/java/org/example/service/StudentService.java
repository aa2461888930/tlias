package org.example.service;

import org.example.pojo.PageResult;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    void deleteByIds(List<Integer> ids);

    void add(Student student);

    Student getStudentById(Integer id);

    void updateById(Student student);

    void updateViolation(Integer id, Integer score);
}
