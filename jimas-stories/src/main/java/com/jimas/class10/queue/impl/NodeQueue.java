package com.jimas.class10.queue.impl;

import com.jimas.class10.queue.JQueue;

/**
 * 单链表实现队列
 *
 * @author liuqj
 */
public class NodeQueue implements JQueue<Integer> {
    @Override
    public boolean add(Integer integer) {
        return false;
    }

    @Override
    public Integer poll() {
        return null;
    }

    @Override
    public Integer peek() {
        return null;
    }
}
