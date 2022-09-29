package com.jimas.class10.stack.impl;

import com.jimas.class10.queue.JQueue;
import com.jimas.class10.queue.impl.ArrayQueue;
import com.jimas.class10.stack.JStack;

/**
 * 用队列实现栈
 * 用两个队列来回倒腾
 *
 * @author liuqj
 */
public class DoubleQueueStack implements JStack<Integer> {
    private int capacity;
    private JQueue<Integer> aQueue;
    private JQueue<Integer> bQueue;

    public DoubleQueueStack(int capacity) {
        this.capacity = capacity;
        aQueue = new ArrayQueue(capacity);
        bQueue = new ArrayQueue(capacity);
    }

    /**
     * 始终实现 空队列添加元素后，再把另一个队列元素加入队列，再互换队列
     *
     * @param num
     * @return
     */
    @Override
    public boolean push(Integer num) {
        if (this.size() == capacity) {
            return false;
        }
        boolean flag = bQueue.add(num);
        while (!aQueue.isEmpty()) {
            bQueue.add(aQueue.poll());
        }
        convertQueue();
        return flag;
    }

    @Override
    public Integer pop() {
        return aQueue.poll();
    }


    @Override
    public Integer peek() {
        return aQueue.peek();
    }

    @Override
    public boolean isEmpty() {
        return aQueue.isEmpty();
    }

    @Override
    public int size() {
        return aQueue.size();
    }

    private void convertQueue() {
        JQueue<Integer> tempQueue = aQueue;
        aQueue = bQueue;
        bQueue = tempQueue;
    }

}
