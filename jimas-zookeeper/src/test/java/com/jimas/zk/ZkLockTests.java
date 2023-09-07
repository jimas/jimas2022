package com.jimas.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * zk 分布式锁实现
 * 0、先创建parent目录
 * 1、多个客户端在parent目录下都去创建临时顺序节点，排序第一的获取锁
 * 2、比如顺序节点编号：000001、000002、000003、000004
 * 3、编号为 000001 获取锁，编号为000002的监听000001，
 * 4、编号为 000003 的监听000002，编号为 000004 的监听000003，
 * 5、等
 */
public class ZkLockTests {
    static CuratorFramework framework;

    @BeforeClass
    public static void before() {
        ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(1000, 3);
        framework = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(backoffRetry)
                .build();

        framework.start();
    }

    @Test
    public void testLock() throws InterruptedException {
        InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(framework, "/ceshi/semaphore-lock");
        for (int i = 0; i < 20; i++) {
            new Thread(new TestRunnable(i, lock)).start();
        }
        Thread.sleep(100000000);
    }

    @Test
    public void testMultiLock() throws InterruptedException {
        InterProcessMultiLock lock = new InterProcessMultiLock(framework, Arrays.asList("/ceshi/multi-lock1", "/ceshi/multi-lock2"));
        for (int i = 0; i < 20; i++) {
            new Thread(new TestRunnable(i, lock)).start();
        }
        Thread.sleep(100000000);
    }

    @Test
    public void testMutex() throws InterruptedException {
        //分布式可重入排它锁
        InterProcessMutex lock = new InterProcessMutex(framework, "/ceshi/mutex-lock");
        for (int i = 0; i < 20; i++) {
            new Thread(new Test2Runnable(i, lock)).start();
//            new Thread(new TestRunnable(i, lock)).start();
        }
        Thread.sleep(100000000);
    }

    static class TestRunnable implements Runnable {
        private final Integer flag;
        private final InterProcessLock lock;

        public TestRunnable(Integer flag, InterProcessLock lock) {
            this.flag = flag;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                boolean acquire = lock.acquire(3, TimeUnit.SECONDS);
                System.out.printf("Thread:%d,获取了Lock：%s ", flag, acquire);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Test2Runnable implements Runnable {
        private final Integer flag;
        private final InterProcessLock lock;

        public Test2Runnable(Integer flag, InterProcessLock lock) {
            this.flag = flag;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                boolean acquire = lock.acquire(1000, TimeUnit.MILLISECONDS);
                System.out.printf("Thread:%d,获取了Lock：%s ", flag, acquire);
                try {
                    if (acquire) {
                        lock.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
