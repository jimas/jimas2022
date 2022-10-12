package com.jimas.class11;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * Date: 2022/10/9
 * Time: 15:41
 *
 * @author jimas
 */
public class MergeTest {
    @Test
    public void testMergeSort() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);

            mergeSort(arr);
            RandomArray.printArr(arr);
            System.out.println("=====");
        }

    }

    private void mergeSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        int mergeSize = 1;
        int N = arr.length;
        while (mergeSize < N) {
            //当前左组的最左位置
            int L = 0;
            while (L < N) {
                int M = L + mergeSize - 1;
                if (M >= N) {
                    break;
                }
                int R = Math.min(M + mergeSize, N - 1);
                //L ... M  M+1 ... R
                merge(arr, L, M, R);
                L = R + 1;
                //防止溢出
                if (mergeSize > N / 2) {
                    break;
                }
            }
            //步长每次乘以2
            mergeSize <<= 1;
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int pl = l;
        int pr = m + 1;
        int i = 0;
        while (pl <= m && pr <= r) {
            help[i++] = arr[pl] >= arr[pr] ? arr[pr++] : arr[pl++];
        }
        while (pl <= m) {
            help[i++] = arr[pl++];
        }
        while (pr <= r) {
            help[i++] = arr[pr++];
        }
        for (int i1 = 0; i1 < help.length; i1++) {
            arr[l + i1] = help[i1];
        }

    }
}
