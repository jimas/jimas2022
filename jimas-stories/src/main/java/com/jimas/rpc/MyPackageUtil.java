package com.jimas.rpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author liuqj
 */
public class MyPackageUtil {

    public static MyHeader createHeader(byte[] bodyBytes) {
        return createHeader(bodyBytes, null);
    }

    public static MyHeader createHeader(byte[] bodyBytes, Long requestId) {
        MyHeader myHeader = new MyHeader();
        myHeader.setContentLen(bodyBytes.length);
        myHeader.setFlag(0x14141414);
        if (requestId != null) {
            myHeader.setRequestId(requestId);
        } else {
            myHeader.setRequestId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        }
        return myHeader;
    }

    /**
     * 字节数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object byteToObj(byte[] bytes) {
        try (ByteArrayInputStream byteInput = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteInput)) {
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转字节数组
     *
     * @param obj
     * @return
     */
    public static byte[] objToBytes(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> MyContext createBody(Class<T> clazz, Object[] args, Method method) {
        MyContext myContext = new MyContext();
        myContext.setArgs(args);
        myContext.setName(clazz.getName());
        myContext.setParameterTypes(method.getParameterTypes());
        myContext.setMethodName(method.getName());
        return myContext;
    }
}

class MyPackage {
    private MyHeader myHeader;
    private MyContext myContext;

    public MyPackage() {
    }

    public MyPackage(MyHeader myHeader, MyContext myContext) {
        this.myHeader = myHeader;
        this.myContext = myContext;
    }

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public void setMyHeader(MyHeader myHeader) {
        this.myHeader = myHeader;
    }

    public MyContext getMyContext() {
        return myContext;
    }

    public void setMyContext(MyContext myContext) {
        this.myContext = myContext;
    }
}
