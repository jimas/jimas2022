package com.jimas.class02;

/**
 * @author liuqj
 */
public class MainThread {

    public static void main(String[] args) {

        SelectorThreadGroup boss = new SelectorThreadGroup("boss",3);
        SelectorThreadGroup worker = new SelectorThreadGroup("worker",3);
        boss.setWorker(worker);
        boss.bind(9999);
        boss.bind(8888);
        boss.bind(7777);
    }
}
