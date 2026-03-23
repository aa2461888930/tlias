package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.*;
import org.example.service.EmpLogService;
import org.example.service.EmpService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        // 1、调用 Mapper 接口查询总记录数
//        Long total = empMapper.count();
//        // 2、调用 Mapper 接口查询分页结果
//        List<Emp> rows = empMapper.list((page - 1) * pageSize, pageSize);
//        // 3、封装到 PageResult 对象并返回
//        PageResult<Emp> pageResult = new PageResult<>();
//        pageResult.setTotal(total);
//        pageResult.setRows(rows);
//        return pageResult;
//    }

    /*
             使用 PageHelper 实现分页

         */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize,
//                                String name, Integer gender,
//                                LocalDate begin, LocalDate end) {
//        // 1、设置分页参数(PageHelper)
//        PageHelper.startPage(page, pageSize);
//
//        // 2、执行查询
//        List<Emp> empList = empMapper.list(empMapper);
//
//        // 3、解析查询结果，并封装
//        Page<Emp> p = (Page<Emp>) empList;
//        return new PageResult<>(p.getTotal(),  p.getResult());
//    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 1、设置分页参数(PageHelper)
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 2、执行查询
        List<Emp> empList = empMapper.list(empQueryParam);

        // 3、解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<>(p.getTotal(),  p.getResult());
    }

    // 事务管理 - 默认是RuntimeException才会回滚。我们这里设置所有异常都回滚
    @Transactional(rollbackFor = {Exception.class}) // 注解@Transactional，方法开头自动开启事务，方法末尾自动提交/回滚
    @Override      // 注意：一个方法中涉及多个数据库操作，才需要该事务注解，只有1个操作，尽管操作失败，也不影响数据一致性
    public void save(Emp emp) {
        try {
            // 1、保存员工的基本信息
            emp.setCreateTime(LocalDateTime.now()); // 补全前端没有填写的属性
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);  // 插入数据后，mybatis框架'主键返回'，即为emp对象id成员变量赋值



            // 2、保存员工的工作经历
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                // 遍历集合，为empId赋值(逻辑：需要上一步插入emp对象，获得id主键，才知道empId值)
                exprList.stream().forEach(expr -> expr.setEmpId(emp.getId())); // 引用数据类型，直接修改，不用返回一个副本

                empExprMapper.insertBatch(exprList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 在新增员工信息时，无论是成功还是失败，都要记录日志
            // 因此记录日志的操作不能与上面的插入emp放到同一个事务中，否则回滚之后就没有记录日志
            // 因此需要控制事务的传播行为，
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工信息：" + emp);
            empLogService.insertLog(empLog);
        }

    }

    @Transactional(rollbackFor = {Exception.class}) // 多次数据库操作，需要事务保证数据库一致性
    @Override                                       // 级别：出现异常就回滚
    public void delete(List<Integer> ids) {
        try {
            // 1、批量删除员工基本信息
            empMapper.deleteByIdList(ids);

            // 2、批量删除员工工作经历
            empExprMapper.deleteByEmpIdList(ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "删除员工信息：" + ids);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = {Exception.class}) // 多个数据库操作，放到事务中，保证数据一致性
    @Override
    public Emp getEmpById(Integer id) {
        // 1、查询员工基本信息
        Emp emp = empMapper.getEmpById(id);

//        // 2、查询员工工作经历
//        List<EmpExpr> exprList = empExprMapper.getEmpExprByEmpId(id);
//
//        emp.setExprList(exprList);
        return emp;

    }

    @Transactional(rollbackFor = {Exception.class}) // 一个业务涉及多个数据库操作，需要事务。任何异常都要回滚
    @Override
    public void update(Emp emp) {
        // 1、根据ID修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now()); //
        empMapper.updateById(emp);

        // 2、根据ID修改员工的工作经历信息
        // 2.1 先根据员工ID删除员工工作经历
        empExprMapper.deleteByEmpIdList(Arrays.asList(emp.getId()));

        // 2.2 再添加这个员工新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }

    }

    @Override
    public List<Emp> getAllEmpList() {
        List<Emp> allEmpList = empMapper.getAllEmpList();
        return allEmpList;
    }

    @Override
    public LoginInfo login(Emp emp) {
        // 1、调用 mapper 接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUserAndPassword(emp);

        // 2、判断是否存在这个员工，如果存在，组装登录成功信息
        if(e != null){
            log.info("登录成功，员工信息:{}", e);
            // 生成 JWT 令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
            // 将 JWT 令牌返回给前端
            return new LoginInfo(e.getId(), e.getUsername(), e.getName(),jwt);
        }


        // 3、不存在，返回null
        return null;
    }
}
