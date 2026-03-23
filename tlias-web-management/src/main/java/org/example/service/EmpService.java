package org.example.service;

import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.LoginInfo;
import org.example.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    /**
     * 使用 PageHelper 实现分页
     */
//    PageResult<Emp> page(Integer page, Integer pageSize,
//                         String name,
//                         Integer gender,
//                         LocalDate begin,
//                         LocalDate end);
    // 使用对象封装参数 对上一个方法的优化
    PageResult<Emp> page(EmpQueryParam empQueryParam);


    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getEmpById(Integer id);

    void update(Emp emp);

    List<Emp> getAllEmpList();

    LoginInfo login(Emp emp);
}
