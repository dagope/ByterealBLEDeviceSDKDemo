/*    */
package com.bytereal.byterealblesdk.threadpoll;

/*    */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class ThreadPoolConfig {
    public static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 3 + 2;
    public static int MAX_POOL_SIZE = CORE_POOL_SIZE * 4;
    public static int KEEP_ALIVE_TIME = 11 - Runtime.getRuntime().availableProcessors();
    public static TimeUnit TIME_UNIT = TimeUnit.MINUTES;
    public static BlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue(Runtime.getRuntime().availableProcessors() * 2);
}


