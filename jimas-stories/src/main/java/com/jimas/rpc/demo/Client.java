package com.jimas.rpc.demo;

import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author liuqj
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket client = new Socket("127.0.0.1", 1234);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        User user1 = new User("小米", 20);
        User user2 = new User("小黑", 18);
        objectOutputStream.writeObject(user1);
        objectOutputStream.writeObject(user2);
        client.close();


    }
}
