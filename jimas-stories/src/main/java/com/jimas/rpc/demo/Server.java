package com.jimas.rpc.demo;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liuqj
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("started。。。。。。");

        Socket clientSocket = serverSocket.accept();

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        User u1 = (User) objectInputStream.readObject();
        System.out.println(u1.getName());
        System.out.println(u1.getAge());
        //用两个不同的输入流 接收会报错 invalid stream header: 7371007E
        /*ObjectInputStream objectInputStream2 = new ObjectInputStream(clientSocket.getInputStream());
        User u2 = (User) objectInputStream2.readObject();
        System.out.println(u2.getName());
        System.out.println(u2.getAge());*/
        //用相同的输入流接收正常
        User u2 = (User) objectInputStream.readObject();
        System.out.println(u2.getName());
        System.out.println(u2.getAge());
        clientSocket.close();
        serverSocket.close();
    }
}
