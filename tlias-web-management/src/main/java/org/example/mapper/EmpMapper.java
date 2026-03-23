package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    @Select("select id, username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time from emp order by update_time desc")
    public List<Emp> getAllEmpList(); // 基于springboot官方骨架，参数名会保留，不用@ Param 注解

    @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
    public Long count();

//    // mybatis: 数据库结果 封装到 Emp对象，保持对象名一致 或者 驼峰命名
//    // 将d.name起别名deptName, 自动封装到Emp对象成员变量中
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id=d.id order by update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize); // 基于springboot官方骨架，参数名会保留，不用@ Param 注解

//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id=d.id order by update_time desc")
//    public List<Emp> list(String name, Integer gender,
//                          LocalDate begin, LocalDate end); // 基于springboot官方骨架，参数名会保留，不用@ Param 注解

    public List<Emp> list(EmpQueryParam empQueryParam);     //  使用对象封装参数，对上个函数的优化

    @Options(useGeneratedKeys = true, keyProperty = "id") // 插入成功后，获取生成的主键，并赋值给 emp对象 的id成员变量
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values(#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    public void insert(Emp emp); // 注意对象的成员变量，都是驼峰命名，数据库字段是下划线命名


    public void deleteByIdList(List<Integer> idList);

//    @Select("select id,username,gender,image,job,salary,entry_date,dept_id,create_time,update_time from emp where id=#{id}")
//    Emp getEmpById(Integer id); // 数据库结果 封装到 Emp对象，保持对象名一致 或者 驼峰命名

//    @Select("select id,username,gender,image,job,salary,entry_date,dept_id,create_time,update_time from emp where id=#{id}")
    Emp getEmpById(Integer id); // 数据库结果 封装到 Emp对象，保持对象名一致 或者 驼峰命名


    void updateById(Emp emp);

    // 将每一行数据封装到Map字典中，因为只有两个字段，比较少，没有封装到类中。
    List<Map<String, Object>> countEmpJobData();

    // String: 数据库列名，例如 id,pos。 Object: 实际数据值，例如1,老师
    List<Map<String, Object>> countEmpGenderData();

    @Select("select count(*) from emp where dept_id=#{id}")
    Integer getCountById(Integer id);

    @Select("select * from emp where username=#{username} and password=#{password}")
    Emp selectByUserAndPassword(Emp emp);
}
