package com.bytereal.byterealblesdk.threadpoll;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPoolHelper {
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        public Thread newThread(Runnable r) {
            return new Thread(r, "myThreadPool thread:" + this.integer.getAndIncrement());
        }
    };

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(ThreadPoolConfig.CORE_POOL_SIZE, ThreadPoolConfig.MAX_POOL_SIZE,
            ThreadPoolConfig.KEEP_ALIVE_TIME, ThreadPoolConfig.TIME_UNIT, ThreadPoolConfig.WORK_QUEUE,
            threadFactory);


    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }
}


