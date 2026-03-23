package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.anno.Log;
import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;
@Slf4j // 该注解自动生成 成员变量log，用于记录日志
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;
    // method：指定请求方式
    @GetMapping("/depts")
//    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    public Result list(){
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request){
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//        System.out.println("根据部门删除部门：" + id);
//        return Result.success();
//    }

    @Log // 自定义注解，用于标识AOP作用的范围，记录增删改日志
    // @RequestParam：请求参数必须存在，否则报错
    @DeleteMapping("/depts")
    public Result delete(@RequestParam(value="id", required = false) Integer id){
        log.info("根据部门删除部门：,{}" , id);
        deptService.deleteById(id);
        return Result.success();
    }

    @Log // 自定义注解，用于标识AOP作用的范围，记录增删改日志
    @PostMapping("/depts")
    public Result  add(@RequestBody Dept dept){ // 将请求中的JSON数据转为Dept对象，确保属性名要一一对应
        log.info("新增部门：{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/depts/{id}") // 使用 @PathVariable 获取路径参数
    public Result getInfo(@PathVariable Integer id){
       log.info("查询部门ID：{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log // 自定义注解，用于标识AOP作用的范围，记录增删改日志
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){ // 使用 @RequestBody 注解 将JSON数据封装到Dept对象中
        log.info("修改部门：{}" , dept);
        deptService.update(dept);
        return Result.success();
    }



}
