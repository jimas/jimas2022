package com.jimas.class10.stack.impl;

import com.jimas.class10.queue.JQueue;
import com.jimas.class10.queue.impl.ArrayQueue;
import com.jimas.class10.stack.JStack;

/**
 * 用队列实现栈
 * 用单个队列实现
 *
 * @author liuqj
 */
public class QueueStack implements JStack<Integer> {
    /**
     * 容量
     */
    private int capacity;
    private JQueue<Integer> aQueue;

    public QueueStack(int capacity) {
        this.capacity = capacity;
        aQueue = new ArrayQueue(capacity);
    }

    /**
     * 新数据入队列时，将之前的数据重新入队，这样就营造出先进先出
     *
     * @param num
     * @return
     */
    @Override
    public boolean push(Integer num) {
        if (this.size() == capacity) {
            return false;
        }
        final int count = this.size();
        boolean flag = aQueue.add(num);
        for (int i = 0; i < count; i++) {
            aQueue.add(aQueue.poll());
        }
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


}
