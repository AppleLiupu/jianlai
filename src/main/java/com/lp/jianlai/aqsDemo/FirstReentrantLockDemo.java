package com.lp.jianlai.aqsDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: eentrantLock
 * @Author: liupu
 * @Date: 2019/9/11
 */
public class FirstReentrantLockDemo {

    private static void print1(Lock lock){
        lock.lock();
        System.out.println("第一个方法开始！！！");
    }

    private static void print2(Lock lock){
        lock.lock();
        System.out.println("进入第二个方法！！！");
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock(true);
        Thread t1 = new Thread(() -> {
            print1(lock);
            print2(lock);//验证可重入

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                lock.unlock();
            }
            System.out.println("完成t1");

            for (int i = 0; i < 10; i++) {
                Thread t = new Thread(() -> {
                    lock.lock();
                    System.out.println("争抢线程："+Thread.currentThread().getName());
                    lock.unlock();
                });
                t.start();
            }

        });
        t1.start();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                lock.lock();
                System.out.println("等待线程："+Thread.currentThread().getName());
                lock.unlock();
            });
            t.start();
        }

    }

}
