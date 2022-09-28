package com.jimas.class10.queue.impl;

import com.jimas.class10.entity.DoubleNode;
import com.jimas.class10.queue.JQueue;

/**
 * 双链表实现队列
 *
 * @author liuqj
 */
public class DoubleNodeQueue implements JQueue<Integer> {
    /**
     * 容量
     */
    private int capacity;
    private DoubleNode head;
    private DoubleNode tail;
    private int size;

    public DoubleNodeQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    @Override
    public boolean add(Integer num) {
        if (size == capacity) {
            return false;
        }
        if (size == 0) {
            head = new DoubleNode(num, null, null);
            tail = head;
        } else {
            tail.next = new DoubleNode(num, tail, null);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public Integer poll() {
        if (head == null) {
            return null;
        }
        int val = head.val;
        head = head.next;
        return val;
    }

    @Override
    public Integer peek() {
        if (head == null) {
            return null;
        }
        return head.val;
    }
}