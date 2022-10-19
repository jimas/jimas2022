package com.jimas.class11;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * 小和问题:
 * 在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和。
 * [1,3,4,2,5]
 * 1左边比1小的数：没有
 * 3左边比3小的数：1
 * 4左边比4小的数：1,3
 * 2左边比2小的数：1
 * 5左边比5小的数：1,3,4,2
 * 所以小和为1+1+3+1+1+3+4+2=16
 * Date: 2022/10/9
 * Time: 16:22
 * 如果直接用两层for循环扫，时间复杂度是O(n^2) ，但是可以通过 归并排序 的方法将时间复杂度降到 O(nlogn)
 *
 * @author jimas
 */
public class MinSumTest {
    @Test
    public void testMinSum() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            int sum = processMinSum(arr, 0, arr.length - 1);
            RandomArray.printArr(arr);
            System.out.println("=====  sum=" + sum + " =====");
        }

    }

    /**
     * 这道题换个角度来想，题目要求的是每个数左边有哪些数比自己小，
     * 其实不就是右边有多少个数比自己大，那么产生的小和就是当前值乘以多少个嘛
     *
     * @param arr
     * @param l
     * @param r
     * @return
     */
    private int processMinSum(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        //merge sort 就是要把左、右两组 变成有序，降低时间复杂度（只要有序后 才能根据 merge 操作 计算出小和）
        return processMinSum(arr, l, mid) + processMinSum(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    /**
     * 该方法 前提是 [l,mid] 上 单调增，[mid+1,r] 上单调增
     *
     * @param arr
     * @param l
     * @param mid
     * @param r
     * @return
     */
    private int merge(int[] arr, int l, int mid, int r) {
        int ans = 0;
        //帮助数组
        int[] help = new int[r - l + 1];
        //帮助数组下标
        int i = 0;
        //左组开始指针
        int lp = l;
        //右组开始指针
        int rp = mid + 1;
        while (lp <= mid && rp <= r) {
            ans += arr[rp] > arr[lp] ? (r - rp + 1) * arr[lp] : 0;
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
