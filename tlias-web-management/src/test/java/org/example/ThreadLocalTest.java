package org.example;

public class ThreadLocalTest {

    private static ThreadLocal<String> local = new ThreadLocal<>();

    // 每一次请求都有一个独立的线程处理
    // 当前线程存，只能由当前线程删，本质上线程的局部变量
    public static void main(String[] args){
        local.set("Main Message"); // main 线程

        // 创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("Sub Message"); // 子线程
                System.out.println(Thread.currentThread().getName() + ":" + local.get()); // Thread-0:Sub Message
            }
        }).start();;

        System.out.println(Thread.currentThread().getName() + ":" + local.get()); // main:Main Message

        local.remove(); // main 线程

        System.out.println(Thread.currentThread().getName() + ":" + local.get()); // main:null
    }

}
