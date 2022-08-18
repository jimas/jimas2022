package com.jimas.rpc;

import com.jimas.rpc.service.Fly;
import org.junit.Test;

/**
 * @author liuqj
 */
public class MyMain {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                MyServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("server start......");
    }

    @Test
    public void testProxy(){
        Fly obj = FactoryProxy.getInstance().obj(Fly.class);
        obj.fly("bird fly...");
    }
}
