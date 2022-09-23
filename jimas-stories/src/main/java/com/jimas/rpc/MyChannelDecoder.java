package com.jimas.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * client 与 server 共用
 * 可以用 flag 区分
 * Date: 2022/8/18
 * Time: 23:31
 *
 * @author com.jimas
 */
public class MyChannelDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        while (buf.readableBytes() >= 95) {
            byte[] bytes = new byte[95];
            buf.getBytes(buf.readerIndex(), bytes);
            MyHeader myHeader = (MyHeader) MyPackageUtil.byteToObj(bytes);
            if (buf.readableBytes() - 95 >= myHeader.getContentLen()) {
                buf.readBytes(bytes.length);
                bytes = new byte[(int) myHeader.getContentLen()];
                buf.readBytes(bytes);
                MyContext myContext = (MyContext) MyPackageUtil.byteToObj(bytes);
                list.add(new MyPackage(myHeader, myContext));
            } else {
                break;
            }
        }
    }
}
