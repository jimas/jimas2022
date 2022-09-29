package com.jimas.class10;

import com.jimas.class10.queue.JQueue;
import com.jimas.class10.queue.impl.ArrayQueue;
import com.jimas.class10.queue.impl.DoubleNodeQueue;
import com.jimas.class10.queue.impl.DoubleStackQueue;
import com.jimas.class10.queue.impl.NodeQueue;
import org.junit.Test;

/**
 * @author liuqj
 */
public class JQueueTest {
    private static final int capacity = 10;

    @Test
    public void testArrayQueue() {
        JQueue<Integer> queue = new ArrayQueue(capacity);
        testPrint(queue);
        System.out.println("====================");

    }

    @Test
    public void testDoubleNodeQueue() {
        JQueue<Integer> queue = new DoubleNodeQueue(capacity);
        testPrint(queue);
        System.out.println("====================");

    }
    @Test
    public void testNodeQueue() {
        JQueue<Integer> queue = new NodeQueue(capacity);
        testPrint(queue);
        System.out.println("====================");

    }

    @Test
    public void testDoubleQueueStack() {
        JQueue<Integer> queue = new DoubleStackQueue(capacity);
        testPrint(queue);
        System.out.println("====================");

    }


    private void testPrint(JQueue<Integer> queue) {
        for (int i = 0; i < 4; i++) {
            System.out.println(queue.add(i));
        }//3 2 1 0
        for (int i = 0; i < 3; i++) {
            System.out.println(queue.poll());
        }//3
        for (int i = 4; i < 14; i++) {
            System.out.println(queue.add(i));
        }//3 2 1 0 3
        System.out.println(queue.peek());
    }


}
