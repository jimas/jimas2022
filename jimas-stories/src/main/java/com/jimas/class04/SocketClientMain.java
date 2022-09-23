package com.jimas.class04;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author liuqj
 */
public class SocketClientMain {

    public static void main(String[] args) throws Exception {

        SocketChannel channel1 = SocketChannel.open();
        channel1.bind(new InetSocketAddress("192.168.1.11", 46321));
        channel1.connect(new InetSocketAddress("192.168.37.128", 9090));

//        SocketChannel channel2 = SocketChannel.open();
//        channel2.bind(new InetSocketAddress("192.168.152.1", 46321));
//        channel2.connect(new InetSocketAddress("192.168.37.128", 9090));


//        SocketChannel channel3 = SocketChannel.open();
//        channel3.bind(new InetSocketAddress("192.168.37.1", 46321));
//        channel3.connect(new InetSocketAddress("192.168.37.128", 9090));



        Thread.sleep(100000);
    }
}
