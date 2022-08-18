package com.jimas.class02;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liuqj
 */
public class SelectorThread implements Runnable {

    Selector selector = null;
    SelectorThreadGroup stg = null;
    LinkedBlockingQueue<Channel> queue = new LinkedBlockingQueue<>(100);

    public SelectorThread(SelectorThreadGroup stg) {
        try {
            this.stg = stg;
            this.selector = Selector.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                //1、select 阻塞 需要 wakeup 唤醒
                int num = selector.select();
                if (num > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    //2、处理key
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        } else if (key.isReadable()) {
                            handleRead(key);
                        } else if (key.isWritable()) {
                            handleWrite(key);
                        }
                    }
                }
                //3、run  all tasks
                if (!queue.isEmpty()) {
                    Channel channel = queue.take();
                    if (channel instanceof ServerSocketChannel) {
                        ServerSocketChannel server = (ServerSocketChannel) channel;
                        server.register(selector, SelectionKey.OP_ACCEPT);
                    } else if (channel instanceof SocketChannel) {
                        SocketChannel client = (SocketChannel) channel;
                        client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(4095));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleWrite(SelectionKey key) {

    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        System.out.println(Thread.currentThread().getName() + " client send data ~~~~ " + client.getRemoteAddress());
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        while (true) {
            int read = client.read(buffer);
            if (read > 0) {
                //反转
                buffer.flip();
                while (buffer.hasRemaining()) {
                    client.write(buffer);
                }
                buffer.clear();
            } else if (read == 0) {
                break;
            } else {
                System.out.println(Thread.currentThread().getName() + " client close :" + client.getRemoteAddress());
                key.cancel();
                break;
            }

        }

    }

    private void handleAccept(SelectionKey key) {
        try {
            System.out.println(Thread.currentThread().getName() + " server accept");
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            stg.nextSelector(client);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
