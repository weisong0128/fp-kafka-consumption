package com.fiberhome.kafka.Thread;

import java.util.concurrent.*;

/**
 * @description: kafka生产者，利用线程池多线程生产及FP消费
 * 方式一：通过threadPool.execute(new TaskThread())执行任务线程对象方式执行线程，for循环控制多线程
 * 方式二：通过CountDownLatch计数器指定执行的多线程数量
 * 共同点：都是通过for循环同时执行多个线程
 * @author: ws
 * @time: 2020/4/1 10:42
 */



public class KafkaProducerTest {
    //需要启动的线程数量
    private static final int THREAD_NUMBER = 5 ;    //通过threadPool.execute(new TaskThread())执行任务线程对象方式执行线程，for循环控制多线程
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,20,1,TimeUnit.MINUTES,new SynchronousQueue<Runnable>());
    private static int CLIENT_COUNT = 7;    //通过CountDownLatch控制多个线程执行任务

    public static void main(String[] args) {

        //方式一：通过创建N个多线程对象实现异步多线程执行
        for (int i = 0; i < THREAD_NUMBER; i++) {
            threadPool.execute(new TaskThread());
        }
        threadPool.shutdown();

        //方式二：通过CountDownLatch分配线程数量
        /*CountDownLatch countDownLatch = new CountDownLatch(CLIENT_COUNT);
        for (int i = 0; i < CLIENT_COUNT; i++) {
            threadPool.execute( () -> {
                new TaskThread().run();
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await(); //说明7个线程任务执行结束(计数器为0)，打开阻塞，开始往下执行代码
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务完成");
        threadPool.shutdown();*/

    }

}
