package com.jimas.class12;

import com.jimas.RandomArray;

/**
 * 荷兰国旗
 *
 * @author liuqj
 */
public class NetherlandFlag {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length == 0) {
                continue;
            }
            RandomArray.printArr(arr);
            int x = arr[RandomArray.randomNum(arr.length)];
            //process01(arr, x);
            process02(arr, x);
            RandomArray.printArr(arr);
            System.out.println("=========== " + x + " =========");
        }
    }

    /**
     * 将数组 arr 中 小于等于x 的放在左边，大于x的 放在右边
     *
     * @param arr 数组
     * @param x   给定值
     */
    private static void process01(int[] arr, int x) {
        int lessZone = -1;
        int currentIndex = 0;
        while (currentIndex < arr.length) {
            //当前位置 小于等于x，将当前数与小于等于区的下一位置交互位置，小于等于区往右边扩一下,当前位置往右边移
            if (arr[currentIndex] <= x) {
                RandomArray.swap(arr, currentIndex++, ++lessZone);
            } else {
                // 若 大于x，则 当前位置往右边移，小于等于区不调整
                currentIndex++;
            }
        }
    }

    /**
     * 将数组 arr 中 小于x 的放在左边，等于x的 放在中间，大于x的 放在右边
     *
     * @param arr 数组
     * @param x   给定值
     */
    private static void process02(int[] arr, int x) {
        int lessZone = -1;
        int moreZone = arr.length;
        int currentIndex = 0;
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
    }

}
