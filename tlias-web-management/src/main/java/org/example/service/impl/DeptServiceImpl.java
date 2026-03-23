package org.example.service.impl;

import org.example.exception.InvalidDeleteException;
import org.example.mapper.DeptMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.Dept;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Integer num = empMapper.getCountById(id);
        if (num > 0){
            throw new InvalidDeleteException("对不起，当前部门下有员工，不能直接删除！");
        }
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Dept dept) {
        // 1、补全基础属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        // 1、补全基础属性
        dept.setUpdateTime(LocalDateTime.now());

        // 2、调用持久层方法
        deptMapper.update(dept);
    }


}
