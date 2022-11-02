package queue;

import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author liuqj
 */
public class PriorityBlockingQueueTest {
    @Test
    public void testQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        for (int i = 1; i < 16; i++) {
            queue.add(i);
        }
        System.out.println(queue.peek());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.peek());

    }
}
