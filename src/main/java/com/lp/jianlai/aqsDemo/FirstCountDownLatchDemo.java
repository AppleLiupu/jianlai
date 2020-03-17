package com.lp.jianlai.aqsDemo;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: 共享锁
 * @Author: liupu
 * @Date: 2019/9/12
 */
public class FirstCountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //定义同步计数器数量2
        CountDownLatch countDownLatch = new CountDownLatch(2);

        //两个countdown线程
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("countdown:"+Thread.currentThread().getName());
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                System.out.println("完成countdown:"+Thread.currentThread().getName());
            }
        });

        Thread t2 = new Thread(() -> {

            try {
                System.out.println("countdown:"+Thread.currentThread().getName());
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                System.out.println("完成countdown:"+Thread.currentThread().getName());
            }
        });

        //两个等待
        Thread t3 = new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println("完成:"+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        Thread t4 = new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println("完成:"+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
