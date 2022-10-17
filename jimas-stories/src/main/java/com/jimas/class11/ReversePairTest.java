package com.jimas.class11;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * https://leetcode.cn/problems/shu-zu-zhong-de-ni-xu-dui-lcof/
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
 * 数组（3，1，4，5，2）的逆序对有(3,1),(3,2),(4,2),(5,2)，共 4 个。
 *
 * @author liuqj
 */
public class ReversePairTest {
    @Test
    public void testReversePair() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            int sum = processReversePair(arr, 0, arr.length - 1);
            RandomArray.printArr(arr);
            System.out.println("=====  sum=" + sum + " =====");
        }

    }

    private int processReversePair(int[] arr, int l, int r) {
        if (r == l) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return processReversePair(arr, l, mid) + processReversePair(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    private int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int lp = l;
        int rp = mid + 1;
        int ans = 0;
        while (lp <= mid && rp <= r) {
            ans += arr[lp] > arr[rp] ? (mid - lp + 1) : 0;
            help[i++] = arr[lp] > arr[rp] ? arr[rp++] : arr[lp++];
        }
        while (lp <= mid) {
            help[i++] = arr[lp++];
        }
        while (rp <= r) {
            help[i++] = arr[rp++];
        }
        for (int i1 = 0; i1 < help.length; i1++) {
            arr[l + i1] = help[i1];
        }
        return ans;
    }
}
