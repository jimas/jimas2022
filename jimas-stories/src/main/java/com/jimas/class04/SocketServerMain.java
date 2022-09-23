package com.jimas.class04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author liuqj
 */
public class SocketServerMain {

    public static void main(String[] args) throws IOException {
        List<SocketChannel> clients = new ArrayList<>();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(9090));
        socketChannel.configureBlocking(false);
        System.out.println("step01:9090端口 启动成功");
        while (true) {
            SocketChannel client = socketChannel.accept();
            if (client != null) {
                client.configureBlocking(false);
                clients.add(client);
                System.out.println("step02:client:port:" + client.socket().getPort() + "," + client.socket());
            }
            Iterator<SocketChannel> iterator = clients.iterator();
            while (iterator.hasNext()) {
                try {
                    SocketChannel c = iterator.next();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = c.read(buffer);
                    buffer.flip();
                    if (read > 0) {
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes, 0, bytes.length);
                        System.out.println(new String(bytes));
                    } else {
                        iterator.remove();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
