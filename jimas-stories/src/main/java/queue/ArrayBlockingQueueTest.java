package queue;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * put 时 队列满时，便执行 Condition 的 await 方法，阻塞在此处。
 * Condition  的 await 方法 会先释放锁，再挂起，等待 signal 唤醒，成功唤醒后 继续竞争锁，抢到锁之后就执行后续流程
 *
 * @author liuqj
 */
public class ArrayBlockingQueueTest {
    final ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(2);

    @Test
    public void testPut() {
        new Thread(() -> {
            blockingQueue.add("sa");
            try {
                blockingQueue.put("sa");
                blockingQueue.put("sa1");
                System.out.println("put end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Object e = blockingQueue.poll();
            System.out.println("poll end:" + e);

        }).start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLength() {
        String[] strings = new String[10];
        strings[0] = "11";
        System.out.println(strings.length);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }
}
