package com.jimas.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
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
                MyContext myContext = MyPackageUtil.createBody(clazz, args, method);
                byte[] bodyBytes = MyPackageUtil.objToBytes(myContext);
                MyHeader myHeader = MyPackageUtil.createHeader(bodyBytes);
                byte[] headBytes = MyPackageUtil.objToBytes(myHeader);
//                System.out.println("client:head size:" + headBytes.length);
                Object obj = Dispatcher.getInstance().getObj(clazz.getName());
                //local
                if (obj != null) {
                    return method.invoke(obj, args);
                } else {
                    //remote
                    return remoteUrlHttp(myContext);
//                    return remoteNettyRpc(bodyBytes, myHeader, headBytes);
                }
            }
        });

    }

    private Object remoteUrlHttp(MyContext context) {
        try {
            URL url = new URL("http://localhost:" + MyServer.PORT);
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
                return o.getResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object remoteNettyRpc(byte[] bodyBytes, MyHeader myHeader, byte[] headBytes) throws Exception {
        NioSocketChannel client = ClientFactory.getInstance().getClient(new InetSocketAddress(MyServer.HOST, MyServer.PORT), 1);
        CompletableFuture res = new CompletableFuture();
        ResponseMappingCallback.addCallback(myHeader.getRequestId(), res);
        ByteBuf msg = UnpooledByteBufAllocator.DEFAULT.directBuffer(headBytes.length + bodyBytes.length);
        msg.writeBytes(headBytes);
        msg.writeBytes(bodyBytes);
        client.writeAndFlush(msg);
        //阻塞住 获取远程结果
        return res.get();
    }
}
