package com.jimas.rpc.test;

import com.jimas.rpc.MyProxy;
import com.jimas.rpc.MyServer;
import com.jimas.rpc.service.Fly;
import org.eclipse.jetty.server.Server;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuqj
 */
public class MyMainTest {
    static AtomicInteger sand = new AtomicInteger(0);

    @Test
    public void jettyServer() {
        Server server = new Server();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServer() {
        try {
            MyServer.startHttpServer();
//            MyServer.startNettyServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClient() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Fly obj = MyProxy.getInstance().obj(Fly.class);
                String req = "bird" + sand.getAndIncrement();
                String fly = obj.fly(req);
                System.out.println(req + "==" + fly);
            }).start();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同一个虚拟机
     */
    @Test
    public void testClientAndServer() {
        new Thread(() -> testServer()).start();
        System.out.println("server started...");
        testClient();
    }


}
