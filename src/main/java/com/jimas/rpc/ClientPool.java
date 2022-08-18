package com.jimas.rpc;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuqj
 */
public class ClientPool {
    private ConcurrentHashMap<String, NioSocketChannel> socketMap;
    private Object[] lock;

    public ClientPool(int poolSize) {
        if (poolSize < 1) {
            poolSize = 1;
        }
        socketMap = new ConcurrentHashMap<>(poolSize);
        lock = new Object[poolSize];
    }
}
