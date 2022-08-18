package com.jimas.class02;

import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuqj
 */
public class SelectorThreadGroup {

    SelectorThread[] slt = null;
    AtomicInteger xid = new AtomicInteger(0);
    SelectorThreadGroup worker = this;

    public SelectorThreadGroup(String name, int num) {
        slt = new SelectorThread[num];
        for (int i = 0; i < num; i++) {
            slt[i] = new SelectorThread(this);
            new Thread(slt[i], name + "-" + i).start();
        }
    }

    public void bind(int port) {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(port);
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(socketAddress);
            nextSelector(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextSelector(Channel channel) throws InterruptedException {
        SelectorThread selectorThread;
        if (channel instanceof ServerSocketChannel) {
            selectorThread = next();
        } else {
            selectorThread = nextWorker();
        }
        selectorThread.queue.put(channel);
        selectorThread.selector.wakeup();
    }

    private SelectorThread next() {
        int index = xid.getAndIncrement() % slt.length;
        return slt[index];
    }

    private SelectorThread nextWorker() {
        int index = xid.getAndIncrement() % worker.slt.length;
        return worker.slt[index];
    }

    public void setWorker(SelectorThreadGroup worker) {
        this.worker = worker;
    }
}
