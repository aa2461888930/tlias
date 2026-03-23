package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;

import java.util.List;

@Mapper // 加上该注释，在运行时自动创建该接口实现类对象，并由IOU容器管理
public interface ClazzMapper {

    public List<Clazz> list(ClazzQueryParam clazzQueryParam);

    @Insert("insert into clazz(name,room,begin_date,end_date,master_id,subject,create_time,update_time)" +
            "values(#{name}, #{room},#{beginDate},#{endDate},#{masterId},#{subject},#{createTime},#{updateTime})")
    void add(Clazz clazz);

    @Select("select * from clazz where id=#{id}")
    Clazz getClazzById(Integer id);


    void updateById(Clazz clazz);

    @Select("select * from clazz")
    List<Clazz> getAllClazzList();



    @Delete("delete from clazz where id=#{id}")
    void deleteById(Integer id);
}
