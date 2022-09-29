package com.jimas.class10.stack.impl;

import com.jimas.class10.entity.DoubleNode;
import com.jimas.class10.stack.JStack;

/**
 * 双向链表实现栈
 *
 * @author liuqj
 */
public class DoubleNodeStack implements JStack<Integer> {
    /**
     * 容量
     */
    private int capacity;
    private DoubleNode head;
    private DoubleNode tail;
    private int size;

    public DoubleNodeStack(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    @Override
    public boolean push(Integer num) {
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
    public Integer pop() {
        if (size > 0) {
            final int val = tail.val;
            tail = tail.pre;
            size--;
            return val;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Integer peek() {
        if (tail == null) {
            return null;
        }
        return tail.val;
    }
}
