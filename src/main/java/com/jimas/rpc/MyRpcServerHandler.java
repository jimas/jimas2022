package com.jimas.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author liuqj
 */
public class MyRpcServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyPackage myPackage = (MyPackage) msg;
//        System.out.println("server requestID:" + myPackage.getMyHeader().getRequestId());
//        System.out.println("server args:" + myPackage.getMyContext().getArgs()[0]);
        MyContext myResBody = new MyContext();
        myResBody.setResponse("server res:" + myPackage.getMyContext().getArgs()[0]);
        ctx.executor().execute(() -> {
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
