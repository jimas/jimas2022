package com.jimas.class10.queue.impl;

import com.jimas.class10.queue.JQueue;
import com.jimas.class10.stack.JStack;
import com.jimas.class10.stack.impl.ArrayStack;

/**
 * 用两个栈 实现队列
 *
 * @author liuqj
 */
public class DoubleStackQueue implements JQueue<Integer> {
    /**
     * 容量
     */
    private int capacity;
    /**
     * 输入栈
     */
    private JStack<Integer> aStack;
    /**
     * 输出栈
     */
    private JStack<Integer> bStack;

    public DoubleStackQueue(int capacity) {
        this.capacity = capacity;
        this.aStack = new ArrayStack(capacity);
        this.bStack = new ArrayStack(capacity);
    }

    @Override
    public boolean add(Integer num) {
        if (this.size() == this.capacity) {
            return false;
        }
        return aStack.push(num);
    }

    @Override
    public Integer poll() {
        //输出栈 为空 需要把输入栈数据全部压入输出栈，非空 直接pop
        if (bStack.isEmpty()) {
            while (!aStack.isEmpty()) {
                bStack.push(aStack.pop());
            }
        }
        return bStack.pop();
    }

    @Override
    public int size() {
        return aStack.size() + bStack.size();
    }

    @Override
    public Integer peek() {
        return bStack.peek();
    }

    @Override
    public boolean isEmpty() {
        return aStack.isEmpty() && bStack.isEmpty();
    }

}
