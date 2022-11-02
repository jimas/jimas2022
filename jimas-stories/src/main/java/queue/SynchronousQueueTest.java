package queue;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * @author liuqj
 */
public class SynchronousQueueTest {
    @Test
    public void testSyncQueue() throws Exception {
        final SynchronousQueue queue = new SynchronousQueue();
        new Thread(() -> {
            try {
                queue.put("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("PUT end");
        }).start();

        Thread.sleep(400);

        new Thread(
                () -> {
                    final Object take = queue.poll();
                    System.out.println("POLL:" + take);
                }
        ).start();

        System.in.read();

    }
}
