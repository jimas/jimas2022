package com.jimas.rpc;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liuqj
 */
public class FactoryProxy {

    private static FactoryProxy proxy = new FactoryProxy();

    private FactoryProxy() {
    }

    public static FactoryProxy getInstance() {
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
                System.out.println(args[0]);
                //TODO 通过 netty 获取 远程结果
                MyContext myContext = new MyContext();
                myContext.setArgs(args);
                myContext.setMethodName(method.getName());
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream oot = new ObjectOutputStream(byteOut);
                oot.writeObject(myContext);
                byte[] bodyBytes = byteOut.toByteArray();
                MyHeader myHeader = MyClient.createHeader(bodyBytes);
                oot.reset();
                oot.writeObject(myHeader);
                byte[] headBytes = byteOut.toByteArray();


                return null;
            }
        });

    }
}
