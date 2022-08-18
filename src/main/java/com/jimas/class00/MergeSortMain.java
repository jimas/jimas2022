package com.jimas.class00;

import com.jimas.RandomArray;

/**
 * 归并
 *
 * @author liuqj
 */
public class MergeSortMain {

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int[] arr = RandomArray.randomArr(10, 20);
            ArrSort.printArr(arr);
            if (arr != null && arr.length != 1) {
                //recursiveMergeSort(arr, 0, arr.length - 1);
                noRrecursiveMergeSort(arr);
            }
            ArrSort.printArr(arr);
            System.out.println("======================");
        }
        //noRrecursiveMergeSort(new int[]{17, 11, 5, 0, 13});


    }

    /**
     * 非递归实现
     *
     * @param arr
     */
    private static void noRrecursiveMergeSort(int[] arr) {
        int step = 1;
        int N = arr.length;
        while (step <= N) {
            int L = 0;
            while (L + step <= N) {
                int M = L + step - 1;
                int R = Math.min(M + step, N - 1);
                merge(arr, L, M, R);
                L = R + 1;
            }
            if (step > (N / 2)) {
                break;
            }
            step *= 2;
        }
    }

    /**
     * 递归实现
     *
     * @param arr
     * @param L
     * @param R
     */
    private static void recursiveMergeSort(int[] arr, int L, int R) {
        if (L < R) {
            //等同与 (R+L)/2,这个有越界风险
            int M = L + ((R - L) >> 1);
            recursiveMergeSort(arr, L, M);
            recursiveMergeSort(arr, M + 1, R);
            merge(arr, L, M, R);
        }
    }

    private static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        //两者都不为空
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (int i1 = 0; i1 < help.length; i1++) {
            arr[L + i1] = help[i1];
        }

    }
}