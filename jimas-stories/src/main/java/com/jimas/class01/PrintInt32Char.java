package com.jimas.class01;

/**
 * @author liuqj
 */
public class PrintInt32Char {

    public static void main(String[] args) {
        int num = 100;
        print32((-1 << 29));
        print32((0 << 29));
        print32((1 << 29));
        print32((2 << 29));
        print32((3 << 29));
    }

    /**
     * << 左移
     * >> 右移
     *
     * @param num
     */
    private static void print32(int num) {
        System.out.println(num);
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }
}
