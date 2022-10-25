package com.jimas;

/**
 * @author liuqj
 */
public class RandomArray {

    public static int[] randomArr(int maxLen, int maxVal) {
        int len = (int) (Math.random() * maxLen);
        int[] ints = new int[len];
        for (int i = 0; i < len; i++) {
            ints[i] = (int) (Math.random() * maxVal);
        }
        return ints;
    }

    public static void printArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println();
    }

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;

    }

    /**
     * 产生随机值
     *
     * @param maxVal
     * @return
     */
    public static int randomNum(int maxVal) {
        return (int) (Math.random() * maxVal);
    }
}
