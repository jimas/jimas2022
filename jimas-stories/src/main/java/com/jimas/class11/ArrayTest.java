package com.jimas.class11;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * Master公式用来计算子问题规模确定的递归函数的时间复杂度。
 * 形如
 * T(N) = a * T(N/b) + O(N^d)(其中的a、b、d都是常数)
 * 的递归函数，可以直接通过Master公式来确定时间复杂度
 * 如果 log(b,a) < d，复杂度为O(N^d)
 * 如果 log(b,a) > d，复杂度为O(N^log(b,a))
 * 如果 log(b,a) == d，复杂度为O(N^d * logN)
 * Date: 2022/10/8
 * Time: 22:51
 *
 * @author jimas
 */
public class ArrayTest {
    @Test
    public void testFindMax() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            int max = process(arr, 0, arr.length - 1);
            System.out.println(max);
            System.out.println("============================");
        }

    }

    @Test
    public void testSortArray() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            mergeSort(arr, 0, arr.length - 1);
            RandomArray.printArr(arr);
            System.out.println("============================");

        }

    }

    /**
     * 实现 L 到 R 位置上有序
     *
     * @param arr
     * @param L
     * @param R
     */
    private void mergeSort(int[] arr, int L, int R) {
        //base case
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 2);
        mergeSort(arr, L, mid);
        mergeSort(arr, mid + 1, R);
        merge(arr, L, mid, R);

    }

    private void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int pl = l;
        int pr = mid + 1;
        while (pl <= mid && pr <= r) {
            help[i++] = arr[pl] > arr[pr] ? arr[pr++] : arr[pl++];
        }
        while (pl <= mid) {
            help[i++] = arr[pl++];
        }
        while (pr <= r) {
            help[i++] = arr[pr++];
        }
        for (int i1 = 0; i1 < help.length; i1++) {
            arr[l + i1] = help[i1];
        }
    }

    /**
     * 递归实现
     * 求 L 到 R 位置上的最大值
     * T(N) = 2*T(T/2)+O(N^0) 套用master公式，其中 a=b=2，d=0
     * O(N)
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    private int process(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int mid = L + ((R - L) >> 2);
        int leftMax = process(arr, 0, mid);
        int rightMax = process(arr, mid + 1, R);
        return Math.max(leftMax, rightMax);
    }
}
