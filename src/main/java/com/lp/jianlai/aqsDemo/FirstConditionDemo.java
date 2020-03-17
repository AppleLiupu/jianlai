package com.lp.jianlai.aqsDemo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: liupu
 * @Description: conditionTest
 * @Date: 2019/11/12
 */
public class FirstConditionDemo {


    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AtomicBoolean flag= new AtomicBoolean(false);

        FirstConditionDemo test = new FirstConditionDemo();

        Thread t1 = new Thread(()->{
            System.out.println("等待："+Thread.currentThread().getName());
            try {
                lock.lock();
                condition.await();
                System.out.println("结束等待："+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(()->{
            System.out.println("唤醒"+Thread.currentThread().getName());
            try {
                lock.lock();
                Thread.sleep(1000L);
                flag.set(true);
                condition.signal();
                System.out.println("唤醒成功"+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t3= new Thread(()->{
            for (int i = 0; i < 100; i++) {
                Thread t = new Thread(() -> {
                    System.out.println("加入等待队列：" + Thread.currentThread().getName());
                    lock.lock();
                    while(true) {
                        if (flag.get()) {
                            System.out.println("队列处理完成：" + Thread.currentThread().getName());
                            lock.unlock();
                            break;
                        }
                    }
                });
                t.start();
            }
        });
        t1.start();

        Thread.sleep(10L);
        t2.start();

        Thread.sleep(10L);
        t3.start();
    }
}
