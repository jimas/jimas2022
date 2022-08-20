package com.jimas.rpc;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 简易连接池
 *
 * @author liuqj
 */
public class ClientPool {
    private volatile NioSocketChannel[] clients;
    private volatile Object[] lock;

    public ClientPool(int poolSize) {
        if (poolSize < 1) {
            poolSize = 1;
        }
        clients = new NioSocketChannel[poolSize];
        lock = new Object[poolSize];
        for (int i = 0; i < poolSize; i++) {
            lock[i] = new Object();
        }
    }

    public NioSocketChannel[] getClients() {
        return clients;
    }

    public Object[] getLock() {
        return lock;
    }
}
