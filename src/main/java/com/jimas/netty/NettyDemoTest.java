package com.jimas.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * nc -l 192.168.37.128 9090 服务端
 * nc 192.168.37.128 9090    客户端
 * @author liuqj
 */
public class NettyDemoTest {
    @Test
    public void testClient() throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        ChannelFuture future = new Bootstrap().group(boss)
                .channel(NioSocketChannel.class)
                //自定义客户端handler
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyClientChannelHandler());
                    }
                })
                //开启客户端监听
                .connect(new InetSocketAddress("192.168.37.128", 9090))
                .sync();
        //等待数据直到客户端关闭
        future.channel().closeFuture().sync();
    }

    @Test
    public void testServer() throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = boss;
        ChannelFuture future = new ServerBootstrap().group(boss, worker)
                .channel(NioServerSocketChannel.class)
                //自定义客户端handler
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyServerChannelHandler());
                    }
                })//绑定端口，开始接收进来的连接 sync 表示等待直到关闭
                .bind(9090).sync();
        // 等待服务器 socket 关闭 。
        future.channel().closeFuture().sync();
    }
}
