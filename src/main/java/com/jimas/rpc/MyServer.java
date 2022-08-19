package com.jimas.rpc;

import com.jimas.rpc.service.Fly;
import com.jimas.rpc.service.impl.MyFly;
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
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.register(Fly.class.getName(), new MyFly());
        NioEventLoopGroup boss = new NioEventLoopGroup(10);
        NioEventLoopGroup worker = boss;
        ChannelFuture future = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("client port:" + ch.remoteAddress().getPort());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MyChannelDecoder());
                        pipeline.addLast(new MyRpcServerHandler(dispatcher));
                    }
                })
                .bind(new InetSocketAddress(HOST, PORT)).sync();

        future.channel().closeFuture().sync();
    }
}
