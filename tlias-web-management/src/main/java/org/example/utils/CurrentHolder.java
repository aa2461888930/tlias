package org.example.utils;

// static 修饰 CURRENT_LOCAL 是关键，这样确保是全局一把锁
// 假如不是 static 修饰，Filter使用的是 CURRENT_LOCAL_1 存入token数据，AOP使用的是 CURRENT_LOCAL_2 获取数据，这样根本获取不到数据！！！


public class CurrentHolder {
    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    public static void setCurrentId(Integer employeeId) {
        CURRENT_LOCAL.set(employeeId);
    }

    // 核心点：CURRENT_LOCAL对象就是this, 作用是作为Map的Key, 所有线程都拿着相同的Key去各自的Map找value
    //        简单说：Key相同，Map不同，value也就不同
    public static Integer getCurrentId() {
        return CURRENT_LOCAL.get();
    }

    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}
