package com.jimas.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 2022/8/18
 * Time: 22:06
 *
 * @author jimas
 */
public class ClientFactory {
    private static volatile ClientFactory clientFactory;
    private volatile ConcurrentHashMap<InetSocketAddress, ClientPool> poolMap = new ConcurrentHashMap<>();
    private Random rand = new Random();

    private ClientFactory() {
    }

    public static ClientFactory getInstance() {
        if (clientFactory != null) {
            return clientFactory;
        }
        synchronized (ClientFactory.class) {
            if (clientFactory == null) {
                clientFactory = new ClientFactory();
            }
        }
        return clientFactory;
    }

    public NioSocketChannel getClient(InetSocketAddress address, int poolSize) {
        ClientPool clientPool = poolMap.get(address);
        if (clientPool == null) {
            synchronized (poolMap) {
                clientPool = poolMap.get(address);
                if (clientPool == null) {
                    poolMap.putIfAbsent(address, new ClientPool(poolSize));
                    clientPool = poolMap.get(address);
                }
            }
        }
        int i = rand.nextInt(poolSize);
        if (clientPool.getClients()[i] != null && clientPool.getClients()[i].isActive()) {
            return clientPool.getClients()[i];
        }
        //伴生锁
        synchronized (clientPool.getLock()[i]) {
            if (clientPool.getClients()[i] == null || !clientPool.getClients()[i].isActive()) {
                clientPool.getClients()[i] = createNettyClient(address);
            }
            return clientPool.getClients()[i];
        }
    }

    private NioSocketChannel createNettyClient(InetSocketAddress address) {

        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ChannelFuture future = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyChannelDecoder());
                        p.addLast(new MyRpcClientHandler());
                    }
                })
                .connect(address);

        try {
            return (NioSocketChannel) future.sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}
