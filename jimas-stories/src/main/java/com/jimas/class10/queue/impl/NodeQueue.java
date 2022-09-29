package com.jimas.class10.queue.impl;

import com.jimas.class10.entity.Node;
import com.jimas.class10.queue.JQueue;

/**
 * 单链表实现队列
 *
 * @author liuqj
 */
public class NodeQueue implements JQueue<Integer> {
    /**
     * 容量
     */
    private int capacity;
    /**
     * 头节点
     */
    private Node head;
    /**
     * 下一个节点
     */
    private Node currNode;
    private int size;

    public NodeQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.head = null;
    }

    @Override
    public boolean add(Integer num) {
        if (size == capacity) {
            return false;
        }
        size++;
        if (head == null) {
            head = new Node(num, null);
            currNode = head;
        } else {
            currNode.next = new Node(num, null);
            currNode = currNode.next;
        }
        return true;
    }

    @Override
    public Integer poll() {
        if (head == null) {
            return null;
        }
        int val = head.val;
        head = head.next;
        size--;
        return val;
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
        if (head == null) {
            return null;
        }
        return head.val;
    }
}
