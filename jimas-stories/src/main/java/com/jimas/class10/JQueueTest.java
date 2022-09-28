package com.jimas.class10;

import com.jimas.class10.queue.JQueue;
import com.jimas.class10.queue.impl.ArrayQueue;
import org.junit.Test;

/**
 * @author liuqj
 */
public class JQueueTest {
    @Test
    public void testArrayQueue() {
        JQueue<Integer> queue = new ArrayQueue(5);
        for (int i = 0; i < 4; i++) {
            System.out.println(queue.add(i));
        }//3 2 1 0
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.poll());
        }//3
        for (int i = 0; i < 4; i++) {
            System.out.println(queue.add(i));
        }//3 2 1 0 3
        System.out.println(queue.peek());
    }
}
