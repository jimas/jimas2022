package com.jimas.class11;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * 数组中左边数大于二倍右边数的总对数
 * arr:[6,4,2,1]
 * 其中(6,2)(6,1)(4,1)这3对是满足左边数大于右边数的两倍
 *
 * @author liuqj
 */
public class BigThanRightTwiceTest {
    @Test
    public void testProcess() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            int sum = process(arr, 0, arr.length - 1);
            RandomArray.printArr(arr);
            System.out.println("=====  sum=" + sum + " =====");
        }

    }

    private int process(int[] arr, int l, int r) {
        if (r == l) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);

        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    private int merge(int[] arr, int l, int mid, int r) {
        int ans = 0;
        // 目前囊括进来的数，是从[M+1, winR),不包含 winR
        int winR = mid + 1;
        for (int j = l; j <= mid; j++) {
            while (winR <= r && arr[j] > arr[winR] * 2) {
                winR++;
            }
            ans += winR - mid - 1;
        }

        //下面归并排序
        int[] help = new int[r - l + 1];
        int i = 0;
        int lp = l;
        int rp = mid + 1;
        //两个都没有越界
        while (lp <= mid && rp <= r) {
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
