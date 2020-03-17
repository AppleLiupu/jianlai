package com.lp.jianlai.aqsDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 处理业务线程池
 * @Author: liupu
 * @Date: 2019/7/24
 */
@Slf4j
public class BusinessExecutor {

    public ThreadPoolExecutor pool = null;

    public BusinessExecutor(RejectedExecutionHandler customRejectedEexcution){
        pool = new ThreadPoolExecutor(
                2,
                2,
                30L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new BusinessExecutor.CustomThreadFactory(),
                customRejectedEexcution);
    }

    public ThreadPoolExecutor getPool(){
        return pool;
    }

    private static class CustomThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group,r);
            String threadName = BusinessExecutor.class.getSimpleName() + threadNumber.getAndIncrement();
            t.setName(threadName);
            return t;
        }
    }
}
