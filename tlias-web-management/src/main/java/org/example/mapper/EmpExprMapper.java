package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.EmpExpr;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    /*
        批量保存员工工作经历信息
     */
    public void insertBatch(List<EmpExpr> empExprList);

    public void deleteByEmpIdList(List<Integer> empIdList);

    @Select("select id, begin, end, company, job,emp_id from emp_expr where emp_id=#{empId}")
    List<EmpExpr> getEmpExprByEmpId(Integer empId);

}
