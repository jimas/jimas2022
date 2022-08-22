package com.jimas.rpc;

import com.jimas.rpc.service.Fly;
import com.jimas.rpc.service.impl.MyFly;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;

/**
 * @author liuqj
 */
public class MyServer {
    public static final int PORT = 9090;
    public static final String HOST = "localhost";

    public static void startNettyServer() throws Exception {
        Dispatcher dispatcher = Dispatcher.getInstance();
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

    public static void startHttpServer() throws Exception {
        Dispatcher dispatcher = Dispatcher.getInstance();
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
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(65536));
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg.toString());
                                String result = "netty http response";
                                MyContext myContext = new MyContext();
                                myContext.setResponse(result);
                                DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                                httpResponse.content().writeBytes(MyPackageUtil.objToBytes(myContext));
                                httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content().readableBytes());
                                httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
                                ctx.writeAndFlush(httpResponse);
                            }
                        });
                    }
                })
                .bind(new InetSocketAddress(HOST, PORT)).sync();

        future.channel().closeFuture().sync();
    }
}
