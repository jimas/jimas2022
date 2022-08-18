package com.jimas.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

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
                MyContext myContext = MyPackageUtil.createBody(args, method);
                byte[] bodyBytes = MyPackageUtil.objToBytes(myContext);
                MyHeader myHeader = MyPackageUtil.createHeader(bodyBytes);
                byte[] headBytes = MyPackageUtil.objToBytes(myHeader);
                NioSocketChannel client = ClientFactory.getInstance().getClient(new InetSocketAddress(MyServer.HOST, MyServer.PORT), 1);
//                System.out.println("client:head size:" + headBytes.length);
                CompletableFuture res = new CompletableFuture();
                ResponseMappingCallback.addCallback(myHeader.getRequestId(), res);
                ByteBuf msg = UnpooledByteBufAllocator.DEFAULT.directBuffer(headBytes.length + bodyBytes.length);
                msg.writeBytes(headBytes);
                msg.writeBytes(bodyBytes);
                ChannelFuture future = client.writeAndFlush(msg);
//                future.sync();
                //阻塞住 获取远程结果
                return res.get();
            }
        });

    }
}
