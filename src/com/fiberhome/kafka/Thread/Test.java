package com.fiberhome.kafka.Thread;


import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: threadPool.execute(() -> {…})方式—因为每个-> {..}里的执行代码不一样，因此不能使用for循环控制多个线程，而要写多个execute(()->{})执行多个不同线程任务
 * @author: ws
 * @time: 2020/4/2 8:58
 */
public class Test implements Runnable{
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,20,1, TimeUnit.MINUTES,new SynchronousQueue<Runnable>());

    public static void main(String[] args) {
        Test t = new Test();
        threadPool.execute(new Thread(t));    //通过线程池方式执行一个完整的继承runnable接口的线程任务
//        new Thread(t).start();            //老方法执行一个新线程任务（不推荐）

        //额外启一个线程任务(异步执行，非阻塞，程序会继续往下执行)，不需要继承runnable接口，不会读取run()方法，只会执行{}内的代码
        threadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println("ws111");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("ws222");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("ws333");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("ws444");
//        threadPool.shutdown();
//        System.out.println("ws444");
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("threadName=" + threadName );
        System.out.println("------哈哈-----");
    }
}
