package com.jimas.class10.stack.impl;

import com.jimas.class10.entity.Node;
import com.jimas.class10.stack.JStack;

/**
 * @author liuqj
 */
public class NodeStack implements JStack<Integer> {
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

    public NodeStack(int capacity) {
        this.capacity = capacity;
        this.head = null;
        this.size = 0;
    }

    @Override
    public boolean push(Integer num) {
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
    public Integer pop() {
        if (currNode == null) {
            return null;
        }
        int val = currNode.val;
        size--;
        currNode = preLastNode(size);
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
        if (currNode == null) {
            return null;
        }
        return currNode.val;
    }

    private Node preLastNode(int count) {
        if (count == 0) {
            currNode = null;
            head = null;
            return null;
        }
        if (count == 1) {
            currNode = head;
            currNode.next = null;
            return currNode;
        }
        Node next = head;
        while (count > 1) {
            next = next.next;
            count--;
        }
        next.next = null;
        return next;
    }
}