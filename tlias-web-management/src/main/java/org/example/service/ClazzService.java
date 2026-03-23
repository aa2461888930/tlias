package org.example.service;

import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    void add(Clazz clazz);

    Clazz getClazzById(Integer id);

    void update(Clazz clazz);

    void deleteById(Integer id) throws Exception;

    List<Clazz> getAllClazzList();
}
