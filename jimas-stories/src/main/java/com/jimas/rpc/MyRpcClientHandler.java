package com.jimas.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Date: 2022/8/18
 * Time: 22:32
 *
 * @author com.jimas
 */
public class MyRpcClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyPackage myPackage = (MyPackage) msg;
        ResponseMappingCallback.runCallback(myPackage);
    }
}
