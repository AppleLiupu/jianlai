package com.lp.jianlai.aqsDemo;

import java.util.concurrent.Semaphore;

/**
 * @Author: liupu
 * @Description: demo
 * @Date: 2019/11/13
 */
public class FirstSemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}
