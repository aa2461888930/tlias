package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Dept;

import java.util.List;
// springboot框架：1、将数据库查询出来的数据封装成Dept对象  2、返回Dept对象，又转换成JSON再返回给前端
//                3、接收JSON数据，有自动封装到Dept对象  (JSON、Dept类、数据库字段)这三者互相转换，只需保证名字相同，或者驼峰命名规范
@Mapper // 加上该注释，在运行时自动创建该接口实现类对象，并由IOU容器管理
public interface DeptMapper {

    // 数据库字段名 与 类属性名不一致, 则不能自动封装，需要映射、或者起别名等方式
    @Select("select id,name,create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    @Delete("delete from dept where id=#{id}")
    void deleteById(Integer id);

    // #{}是dept对象的成员变量，格式为驼峰命名
    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    @Select("select id,name,create_time, update_time from dept where id=#{id}")
    Dept getById(Integer id);

    @Update("update dept set name=#{name},update_time=#{updateTime} where id=#{id}")
    void update(Dept dept);
}
