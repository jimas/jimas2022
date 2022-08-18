package com.jimas.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author liuqj
 */
public class MyServerChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        super.channelRegistered(ctx);
    }

    /**
     * 用于读取客户端发送的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        //模拟业务处理消息
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), Charset.defaultCharset());
        System.out.println(charSequence.toString());
        //释放资源
        buf.release();
        //模拟返回数据
        String response = charSequence.toString() + "::" + " hello client!\n";
        ByteBuf buffer = ctx.alloc().buffer(4 * response.length());
        buffer.writeBytes(response.getBytes());
        ctx.channel().writeAndFlush(buffer);
    }
}
