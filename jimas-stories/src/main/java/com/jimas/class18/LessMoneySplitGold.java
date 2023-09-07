package com.jimas.class18;

import java.util.PriorityQueue;

/**
 * 给定一个数组 [3,5,10,12,15]
 *
 * @author liuqj
 */
public class LessMoneySplitGold {
    public static void main(String[] args) {
        int[] array = new int[]{3, 5, 10, 21, 23};
        int sum = lessMoneyOne(array);
        int sum2 = lessMoneyTwo(array);
        System.out.println(sum==sum2);
    }

    private static int lessMoneyTwo(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return process(array, 0);
    }

    private static int process(int[] array, int pre) {
        if (array.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                ans = Math.min(ans, process(copyAndMerge(array, i, j), pre + array[i] + array[j]));
            }
        }
        return ans;
    }

    private static int[] copyAndMerge(int[] array, int i, int j) {
        int[] ans = new int[array.length - 1];
        int ansi = 0;
        for (int i1 = 0; i1 < array.length; i1++) {
            if (i1 != i && i1 != j) {
                ans[ansi++] = array[i1];
            }
        }
        ans[ansi] = array[i] + array[j];
        return ans;
    }

    private static int lessMoneyOne(int[] array) {
        //默认小根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int arr : array) {
            queue.add(arr);
        }
        int sum = 0;
        int cur;
        while (queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            sum += cur;
            queue.add(cur);
        }
        return sum;
    }

}
