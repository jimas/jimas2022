package com.jimas.class10.stack.impl;

import com.jimas.class10.stack.JStack;

/**
 * 数组实现 栈
 *
 * @author liuqj
 */
public class ArrayStack implements JStack<Integer> {
    /**
     * 容量
     */
    private int capacity;
    /**
     * 真实数据存储
     */
    private Integer[] array;
    /**
     * 栈顶指针
     */
    private int index;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        array = new Integer[capacity];
        this.index = -1;
    }

    @Override
    public boolean push(Integer num) {
        if (index == capacity - 1) {
            return false;
        }
        array[++index] = num;
        return true;
    }

    @Override
    public Integer pop() {
        if (index < 0) {
            return null;
        }
        return array[index--];
    }

    @Override
    public boolean isEmpty() {
        return index < 0;
    }

    @Override
    public int size() {
        return index + 1;
    }

    @Override
    public Integer peek() {
        return array[index];
    }
}
