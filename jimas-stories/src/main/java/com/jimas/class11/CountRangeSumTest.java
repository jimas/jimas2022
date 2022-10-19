package com.jimas.class11;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * 给定一个数组arr，两个整数 lower、upper
 * 返回arr 中 有多少个子数组的累加和在[lower,upper]范围上
 * //[-2147483647,0,-2147483647,2147483647]
 * //        -564
 * //        3864
 *
 * @author liuqj
 */
public class CountRangeSumTest {


    @Test
    public void tetProcess() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            long[] sum = generateSumArr(arr);
            int count = process(sum, 0, sum.length - 1, 3, 5);
            System.out.println("=================count:" + count);
        }
    }

    private int process(long[] sum, int l, int r, int lower, int upper) {
        if (l == r) {
            if (sum[l] >= lower && sum[l] <= upper) {
                return 1;
            } else {
                return 0;
            }
        }
        int mid = l + ((r - l) >> 1);
        return process(sum, l, mid, lower, upper)
                + process(sum, mid + 1, r, lower, upper)
                + merge(sum, l, mid, r, lower, upper);
    }


    private int merge(long[] sum, int l, int mid, int r, int lower, int upper) {
        int ans = 0;
        int winL = l;
        int winR = l;
        for (int i = mid + 1; i <= r; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            while (winR <= mid && sum[winR] <= max) {
                winR++;
            }
            while (winL <= mid && sum[winL] < min) {
                winL++;
            }
            ans += winR - winL;
        }

        long[] help = new long[r - l + 1];
        int i = 0;
        int lp = l;
        int rp = mid + 1;
        while (lp <= mid && rp <= r) {
            help[i++] = sum[lp] > sum[rp] ? sum[rp++] : sum[lp++];
        }
        while (lp <= mid) {
            help[i++] = sum[lp++];
        }
        while (rp <= r) {
            help[i++] = sum[rp++];
        }
        for (int i1 = 0; i1 < help.length; i1++) {
            sum[l + i1] = help[i1];
        }
        return ans;
    }

    /**
     * 构建前 n 项和数组 防止越界 用 long 存储
     * sum[0] = arr[0]
     * sum[1] = arr[0]+arr[1]
     * sum[2] = arr[0]+arr[1]+arr[2]
     * sum[n] = sum[n-1] + arr[n]
     *
     * @param arr
     * @return
     */
    private long[] generateSumArr(int[] arr) {
        long[] sum = new long[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        return sum;
    }
}
