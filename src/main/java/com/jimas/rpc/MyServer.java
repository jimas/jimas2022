package com.jimas.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author liuqj
 */
public class MyServer {
    public static final int PORT = 9090;
    public static final String HOST = "localhost";

    public static void start() throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup(10);
        NioEventLoopGroup worker = boss;
        ChannelFuture future = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MyChannelDecoder());
                        pipeline.addLast(new MyRpcServerHandler());
                    }
                })
                .bind(new InetSocketAddress(HOST, PORT)).sync();

        future.channel().closeFuture().sync();
    }
}
