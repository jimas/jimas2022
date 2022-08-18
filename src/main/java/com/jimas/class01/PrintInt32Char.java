package com.jimas.class01;

/**
 * @author liuqj
 */
public class PrintInt32Char {

    public static void main(String[] args) {
        int num = 100;
        print32(num);
    }

    /**
     * << 左移
     * >> 右移
     *
     * @param num
     */
    private static void print32(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }

    }
}
