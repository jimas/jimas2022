package com.jimas.class10.stack.impl;

import com.jimas.class10.stack.JStack;

/**
 * @author liuqj
 */
public class NodeStack implements JStack<Integer> {
    @Override
    public boolean push(Integer integer) {
        return true;
    }

    @Override
    public Integer pop() {
        return null;
    }

    @Override
    public Integer peek() {
        return null;
    }
}