package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// 无状态的bean: 该bean不保存数据
// 有状态的bean: 该bean保存数据

// 多线程访问 无状态的bean: 由于“无状态的bean”不保存数据，因此就不会多线程共享数据的问题，也就没有“线程安全”问题
// (单例bean)多线程访问 有状态的bean: 不管有多少个线程，无论操作多少次，始终访问同一个bean对象。这样bean的数据就不是“本次连接”的信息，而是"所有连接"的信息
// 如果想要 bean 只保留 "本次连接"的信息 -> 需要多例bean -> 对每个连接，创建一个全新的bean记录数据

// 结论： 无状态bean --> 单例bean    有状态bean --> 多例bean
//       项目绝多数的bean是 单例bean

/**
 * 默认 bean 是单例的 ---> singleton: 默认单例的 bean 是项目启动时创建的，创建后会将该bean存入IOU容器
 */
//@Lazy // 延迟初始化 ----> 延迟到第一次使用的时候，再来创建这个bean
@Scope("prototype")    // 非单例的、多例的，bean需要使用的时候创建
@RestController
public class DeptController {

    public DeptController(){
        // 创建 bean 时，需要调用这个 无参构造函数，
        System.out.println("创建 DeptController 对象");
    }

    @Autowired
    private DeptService deptService;

    /**
     * 查询全部部门
     */
    @GetMapping("/depts")
    public Result findAll(){
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 根据ID删除部门
     */
    @DeleteMapping("/depts")
    public Result delete(Integer id){
        System.out.println("根据ID删除部门数据: " + id);
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门
     */
    @PostMapping("/depts")
    public Result save(@RequestBody Dept dept){
        System.out.println("新增部门数据: " + dept);
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 根据ID查询部门信息
     */
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id){
        System.out.println("根据ID查询部门数据: " + id);
        Dept dept = deptService.getInfo(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     */
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        System.out.println("修改部门数据: " + dept);
        deptService.update(dept);
        return Result.success();
    }
}
