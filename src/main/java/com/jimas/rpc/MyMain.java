package com.jimas.rpc;

import com.jimas.rpc.service.Fly;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuqj
 */
public class MyMain {
    static AtomicInteger sand = new AtomicInteger(0);

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                MyServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("server start......");

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


}
