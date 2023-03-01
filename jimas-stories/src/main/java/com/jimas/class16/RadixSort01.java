package com.jimas.class16;

import com.jimas.RandomArray;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 基数排序 & 计数排序
 * 1、先取出 数组中最大的数，并判断是几位数
 *
 * @author liuqj
 */
public class RadixSort01 {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int[] arr = RandomArray.randomArr(10, 10000);
            if (arr.length == 1) {
                return;
            }
            RandomArray.printArr(arr);
//            radixO1(arr);
            radixO2(arr);
            RandomArray.printArr(arr);
            System.out.println("===================");
        }


    }

    private static void radixO2(int[] arr) {
        int maxNum = getMaxNum(arr);
        int digit = getDigit(maxNum);
        //准备辅助数组
        int[] help = new int[arr.length];
        for (int d = 0; d < digit; d++) {
            //10个空间
            int[] count = new int[10];
            for (int i1 = 0; i1 < arr.length; i1++) {
                int numOfDigit = getDigitNum(arr[i1], d);
                count[numOfDigit]++;
            }
            //count 值累加
            for (int i = 1; i < count.length; i++) {
                count[i] = count[i] + count[i - 1];
            }
            //arr 从右往左遍历
            for (int i2 = arr.length - 1; i2 >= 0; i2--) {
                int digitNum = getDigitNum(arr[i2], d);
                help[count[digitNum] - 1] = arr[i2];
                count[digitNum]--;
            }
            for (int i = 0; i < help.length; i++) {
                arr[i] = help[i];
            }
        }

    }

    /**
     * 利用10个队列 当作桶实现
     *
     * @param arr
     */
    private static void radixO1(int[] arr) {
        int maxNum = getMaxNum(arr);
        int digit = getDigit(maxNum);
        //准备10个桶 用队列实现，先进先出
        Queue<Integer>[] queues = initQueues();
        //最大是几位数，就 “进桶出桶” 几次
        for (int i = 0; i < digit; i++) {
            //进桶
            for (int i1 = 0; i1 < arr.length; i1++) {
                int numOfDigit = getDigitNum(arr[i1], i);
                queues[numOfDigit].add(arr[i1]);
            }
            //出桶
            int newIndex = 0;
            for (Queue<Integer> queue : queues) {
                while (!queue.isEmpty()) {
                    arr[newIndex++] = queue.poll();
                }
            }
            RandomArray.printArr(arr);

        }
    }

    private static int getDigitNum(int num, int digit) {
        String s = Integer.toString(num);
        if (s.length() <= digit) {
            return 0;
        }
        String s1 = s.charAt(s.length() - 1 - digit) + "";
        return Integer.parseInt(s1);
    }

    /**
     * 初始化10个桶
     *
     * @return
     */
    private static Queue<Integer>[] initQueues() {
        Queue[] stacks = new Queue[10];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = new LinkedList();
        }
        return stacks;
    }

    /**
     * 判断数字是几位数
     *
     * @param num
     * @return
     */
    private static int getDigit(int num) {
        return Integer.toString(num).length();
    }

    private static int getMaxNum(int[] arr) {
        int max = 0;
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }
}
