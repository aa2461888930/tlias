package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Student;
import org.example.pojo.StudentDegree;
import org.example.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> list(StudentQueryParam studentQueryParam);


    void deleteByIds(List<Integer> ids);

    @Insert("insert into student(name,no,gender,phone,degree,clazz_id,id_card,is_college,address,graduation_date,create_time,update_time)"+
    "values(#{name}, #{no}, #{gender}, #{phone}, #{degree}, #{clazzId}, #{idCard}, #{isCollege}, #{address}, #{graduationDate},#{createTime},#{updateTime})")
    void add(Student student);

    @Select("select * from student where id=#{id}")
    Student getStudentById(Integer id);

    void updateById(Student student);

    @Select("select count(*) from student where clazz_id=#{id}")
    Integer getStudentCount(Integer id);

    @Select("select clazz.name clazz_name, count(*) data from clazz  left join student\n" +
            "on student.clazz_id=clazz.id group by clazz.name order by count(*)")
    List<Map<String, Object>> countStudentData();

    List<StudentDegree> getStudentDegreeData();
}
