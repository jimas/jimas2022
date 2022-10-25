package com.jimas.class12;

import com.jimas.RandomArray;
import org.junit.Test;

import java.util.Stack;

/**
 * 快速排序
 * 给定一个数组 arr ,假如 arr 中 最后一个元素是A，
 * 实现大于A的在右边，小于A的在左边，等于A的在中间，
 * 返回等于A的数组角标[i,j]:标识 数组中 i位置到j位置 都是A
 *
 * @author liuqj
 */
public class FastSortTest {

    /**
     * 取 数组 最后一个数 当作x
     */
    @Test
    public void test01() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            partition3(arr, 0, arr.length - 1);
            RandomArray.printArr(arr);
            System.out.println("==================");
        }
    }

    /**
     * 快速快序
     * x 是 最后一个数
     *
     * @param arr
     * @param l
     * @param r
     */
    private void partition(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int x = arr[r];
        int[] equals = process(arr, x, l, r);
        partition(arr, l, equals[0] - 1);
        partition(arr, equals[1] + 1, r);
    }

    /**
     * 随机快排
     * x 是随机产生的数
     *
     * @param arr
     * @param l
     * @param r
     */
    private void partition2(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int x = arr[l + RandomArray.randomNum(r - l + 1)];
        int[] equals = process(arr, x, l, r);
        partition2(arr, l, equals[0] - 1);
        partition2(arr, equals[1] + 1, r);
    }

    /**
     * 非递归实现 快排
     *
     * @param arr
     * @param l
     * @param r
     */
    private void partition3(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int x = arr[l + RandomArray.randomNum(r - l + 1)];
        int[] equals = process(arr, x, l, r);
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, equals[0] - 1));
        stack.push(new Op(equals[1] + 1, r));
        while (!stack.empty()) {
            final Op pop = stack.pop();
            if (pop.l < pop.r) {
                x = arr[pop.l + RandomArray.randomNum(pop.r - pop.l + 1)];
                equals = process(arr, x, pop.l, pop.r);
                stack.push(new Op(pop.l, equals[0] - 1));
                stack.push(new Op(equals[1] + 1, pop.r));
            }
        }


    }

    public static class Op {
        public int l;
        public int r;

        public Op(int left, int right) {
            l = left;
            r = right;
        }
    }

    /**
     * 返回 等于区的角标位置数组
     *
     * @param arr
     * @return
     */
    private static int[] process(int[] arr, int x, int l, int r) {
        int lessZone = l - 1;
        int moreZone = r + 1;
        int currentIndex = l;
        while (currentIndex < moreZone) {
            //当前位置 小于x，将当前数与小于区的下一位置交互位置，小于区往右边扩一下,当前位置往右边移
            if (arr[currentIndex] < x) {
                RandomArray.swap(arr, currentIndex++, ++lessZone);
            } else if (arr[currentIndex] > x) {
                //当前位置 大于x，将当前数与大于区的下一位置交互位置，大于区往左边扩一下,当前位置不变
                RandomArray.swap(arr, currentIndex, --moreZone);
            } else {
                // 若 等于x，则 当前位置往右边移
                currentIndex++;
            }
        }
        return new int[]{lessZone + 1, moreZone - 1};
    }


}
