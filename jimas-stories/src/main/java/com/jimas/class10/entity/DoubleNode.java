package com.jimas.class10.entity;

/**
 * @author liuqj
 */
public class DoubleNode {
    public int val;
    public DoubleNode pre;
    public DoubleNode next;

    public DoubleNode(int val, DoubleNode pre, DoubleNode next) {
        this.val = val;
        this.pre = pre;
        this.next = next;
    }

    public DoubleNode() {
    }

    @Override
    public String toString() {
        return "DoubleNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
