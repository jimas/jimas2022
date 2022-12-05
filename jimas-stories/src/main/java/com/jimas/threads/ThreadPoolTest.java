package com.jimas.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 */
public class ThreadPoolTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1000000; i++) {
            executor.execute(() -> {
            });
        }
        //暂停接收新任务，队列中任务继续执行 状态置为 SHUTDOWN
        executor.shutdown();
        // 状态 不为 TERMINATED  将继续等待 timeout 时间
        executor.awaitTermination(10, TimeUnit.HOURS);
    }
}
