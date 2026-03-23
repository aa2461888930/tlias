package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.exception.InvalidDeleteException;
import org.example.mapper.ClazzMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.Emp;
import org.example.pojo.PageResult;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        // 1、调用 mapper 分页查询
        // 1.1 设置分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        // 1.2 执行查询
        List<Clazz> clazzList  = clazzMapper.list(clazzQueryParam);
        // 1.3 解析查询结果，并封装
        Page<Clazz> p = (Page<Clazz>) clazzList;

        List<Clazz> result = p.getResult();

        // 2、结果解析：设置班级状态
        result.forEach(
                clazz -> {
                    LocalDate beginDate = clazz.getBeginDate();
                    LocalDate endDate = clazz.getEndDate();
                    if (endDate != null && LocalDate.now().isAfter(endDate)){
                        clazz.setStatus("已结课");
                    }else if (beginDate != null && LocalDate.now().isBefore(beginDate)){
                        clazz.setStatus("未开班");
                    }else{
                        clazz.setStatus("在读中");
                    }
                }
        );
        return new PageResult<Clazz>(p.getTotal(), result);
    }

    @Override
    public void add(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.add(clazz);
    }

    @Override
    public Clazz getClazzById(Integer id) {
        return clazzMapper.getClazzById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        // 1、查询
        Integer studentCount = studentMapper.getStudentCount(id);
        if (studentCount > 0 ){
            throw new InvalidDeleteException("对不起, 该班级下有学生, 不能直接删除~~");
        }
        clazzMapper.deleteById(id);
    }

    @Override
    public List<Clazz> getAllClazzList() {
        return clazzMapper.getAllClazzList();
    }

}
