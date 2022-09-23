package com.jimas.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import static com.jimas.rpc.MyServer.HOST;
import static com.jimas.rpc.MyServer.PORT;

/**
 * @author liuqj
 */
public class MyProxy {
    private static MyProxy proxy = new MyProxy();

    private MyProxy() {
    }

    public static MyProxy getInstance() {
        return proxy;
    }

    /**
     * 接口实现类 代理
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T obj(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MyContext myContext = MyPackageUtil.createBody(clazz, args, method);
                Object obj = Dispatcher.getInstance().getObj(clazz.getName());
                //local
                if (obj != null) {
                    return method.invoke(obj, args);
                } else {
                    CompletableFuture res = new CompletableFuture();
                    //remote
                    //  remoteUrlHttp(myContext,res);
                    remoteNettyHttp(myContext, res);
//                     remoteNettyRpc(myContext, res);

                    return res.get();
                }
            }
        });

    }

    private void remoteNettyHttp(MyContext myContext, CompletableFuture res) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        final ChannelFuture future = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ph) throws Exception {
                        final ChannelPipeline pipeline = ph.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 512));
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                FullHttpResponse httpResponse = (FullHttpResponse) msg;
                                byte[] resBodyTybes = new byte[httpResponse.content().readableBytes()];
                                httpResponse.content().readBytes(resBodyTybes);
                                MyContext o = (MyContext) MyPackageUtil.byteToObj(resBodyTybes);
                                res.complete(o.getResponse());
                            }
                        });
                    }
                }).connect(new InetSocketAddress(HOST, PORT)).sync();

        byte[] array = MyPackageUtil.objToBytes(myContext);
        DefaultFullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/",
                Unpooled.copiedBuffer(array));
        httpRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH, array.length);
        future.channel().writeAndFlush(httpRequest).sync();
    }

    private void remoteUrlHttp(MyContext context, CompletableFuture res) {
        Object obj = null;
        try {
            URL url = new URL("http://localhost:" + PORT);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            ObjectOutputStream oostream = new ObjectOutputStream(urlConnection.getOutputStream());
            oostream.writeObject(context);
            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                MyContext o = (MyContext) ois.readObject();
                obj = o.getResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.complete(obj);
    }

    private void remoteNettyRpc(MyContext myContext, CompletableFuture res) throws Exception {
        byte[] bodyBytes = MyPackageUtil.objToBytes(myContext);
        MyHeader myHeader = MyPackageUtil.createHeader(bodyBytes);
        byte[] headBytes = MyPackageUtil.objToBytes(myHeader);
//                System.out.println("client:head size:" + headBytes.length);

        NioSocketChannel client = ClientFactory.getInstance().getClient(new InetSocketAddress(HOST, PORT), 1);
        ResponseMappingCallback.addCallback(myHeader.getRequestId(), res);
        ByteBuf msg = UnpooledByteBufAllocator.DEFAULT.directBuffer(headBytes.length + bodyBytes.length);
        msg.writeBytes(headBytes);
        msg.writeBytes(bodyBytes);
        client.writeAndFlush(msg);
    }
}
