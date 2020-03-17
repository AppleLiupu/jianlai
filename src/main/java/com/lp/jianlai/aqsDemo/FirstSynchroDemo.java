package com.lp.jianlai.aqsDemo;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: liupu
 * @Description: 隐式锁
 * @Date: 2019/11/13
 */
public class FirstSynchroDemo {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        AtomicBoolean flag= new AtomicBoolean(false);

        Thread t1 = new Thread(()->{
            try {
                System.out.println("等待："+Thread.currentThread().getName());
                synchronized(lock){
                    lock.wait();
                    System.out.println("结束等待："+Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            try {
                System.out.println("唤醒" + Thread.currentThread().getName());
                synchronized(lock) {
                    Thread.sleep(1000L);
                    lock.notify();
                    flag.set(true);
                    System.out.println("唤醒成功" + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread.sleep(1L);
        t2.start();

        Thread.sleep(500L);
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(()->{
                System.out.println("加入等待队列：" + Thread.currentThread().getName());
                synchronized(lock) {
                    while(true) {
                        if (flag.get()) {
                            System.out.println("队列处理完成：" + Thread.currentThread().getName());
                            break;
                        }
                    }
                }
            });
            t.start();
        }


    }
}
