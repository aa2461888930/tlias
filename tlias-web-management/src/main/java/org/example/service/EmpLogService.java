package org.example.service;

import org.example.pojo.EmpLog;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface EmpLogService {
    // REQUIRES_NEW需要在一个新的事务中运行。如果当前有一个事务，也要新建一个事务；如果没有事务；也要新建一个事务
    // 从而保证插入日志的操作永远是独立的，不会被其他事务影响，永远会commit
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLog(EmpLog empLog);

}
