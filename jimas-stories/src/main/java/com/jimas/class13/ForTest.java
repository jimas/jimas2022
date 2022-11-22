package com.jimas.class13;

/**
 * @author liuqj
 */
public class ForTest {
    public static void main(String[] args) {
        int i = 0;
        retry:
        for (; ; ) {
            System.out.println("外层for循环" + i);
            for (; ; ) {
                i++;
                System.out.println("内层for循环" + i);
                if (i % 2 == 0) {
                    continue retry;
                }
                if (i % 5 == 0) {
                    break retry;
                }
            }
        }
        System.out.println("for 循环结束");
    }
}
