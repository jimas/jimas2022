package com.jimas.class10.queue.impl;

import com.jimas.class10.queue.JQueue;

/**
 * 使用数组实现队列
 * 循环数组，可重复利用空间
 *
 * @author liuqj
 */
public class ArrayQueue implements JQueue<Integer> {
    /**
     * 容量
     */
    private int capacity;
    /**
     * 真实数据存储
     */
    private Integer[] array;
    /**
     * 真实的数组大小
     */
    private int size;
    private int inIndex;
    private int outIndex;

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        array = new Integer[capacity];
        this.size = 0;
        this.inIndex = -1;
        this.outIndex = -1;
    }

    @Override
    public boolean add(Integer num) {
        if (size == capacity) {
            return false;
        }
        array[getNextInIndex()] = num;
        size++;
        return true;
    }


    @Override
    public Integer poll() {
        if (size == 0) {
            return null;
        }
        size--;
        return array[getNextOutIndex()];
    }

    @Override
    public Integer peek() {
        return array[outIndex];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private int getNextInIndex() {
        if (inIndex >= capacity - 1) {
            inIndex = -1;
        }
        return ++inIndex;
    }

    private int getNextOutIndex() {
        if (outIndex >= capacity - 1) {
            outIndex = -1;
        }
        return ++outIndex;
    }
}