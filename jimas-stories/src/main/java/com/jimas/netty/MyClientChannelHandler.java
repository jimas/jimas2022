package com.jimas.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author liuqj
 */
public class MyClientChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * 用于向服务端发送消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "hello server!\n";
        ByteBuf buffer = ctx.alloc().buffer(4 * msg.length());
        buffer.writeBytes(msg.getBytes());
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 用于接收服务端发送来的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //也可以给服务端发送
        // receiveAndSend(ctx, msg);
        receive(ctx, msg);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 接收并发送
     *
     * @param ctx
     * @param msg
     */
    private void receiveAndSend(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), Charset.defaultCharset());
        System.out.println(charSequence.toString());
        ctx.channel().writeAndFlush(msg);
    }

    /**
     * 也可以只接收
     *
     * @param ctx
     * @param msg
     */
    private void receive(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), Charset.defaultCharset());
        System.out.println("Server said:" + charSequence.toString());
        buf.release();
    }
}
