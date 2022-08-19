package com.jimas.rpc.service.impl;

import com.jimas.rpc.service.Fly;

/**
 * @author liuqj
 */
public class MyFly implements Fly {
    @Override
    public String fly(String who) {
        return "server tips:" + who;
    }
}
