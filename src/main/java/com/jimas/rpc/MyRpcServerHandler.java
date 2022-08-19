package com.jimas.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * @author liuqj
 */
public class MyRpcServerHandler extends ChannelInboundHandlerAdapter {
    Dispatcher dispatcher;

    public MyRpcServerHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyPackage myPackage = (MyPackage) msg;
        System.out.println("io thread:" + Thread.currentThread().getName());
        MyContext myResBody = new MyContext();
        //通过反射
        Object obj = dispatcher.getObj(myPackage.getMyContext().getName());
        Class<?> aClass = obj.getClass();
        Method method = aClass.getMethod(myPackage.getMyContext().getMethodName(), myPackage.getMyContext().getParameterTypes());
        Object res = method.invoke(obj, myPackage.getMyContext().getArgs());
        myResBody.setResponse(res.toString());
        ctx.executor().parent().next().execute(() -> {
//                ctx.executor().execute(() -> {
            System.out.println("execute thread:" + Thread.currentThread().getName());
            byte[] bodyBytes = MyPackageUtil.objToBytes(myResBody);
            MyHeader myResHeader = MyPackageUtil.createHeader(bodyBytes, myPackage.getMyHeader().getRequestId());
            byte[] myResHeadBytes = MyPackageUtil.objToBytes(myResHeader);
            ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.directBuffer(myResHeadBytes.length + bodyBytes.length);
            buf.writeBytes(myResHeadBytes);
            buf.writeBytes(bodyBytes);
            ctx.writeAndFlush(buf);
        });
    }
}
