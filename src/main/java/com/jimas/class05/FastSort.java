package com.jimas.class05;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * [1,4,5,6,7,4,3,4]
 * 1、小于等于区下一个 小于等于 P，当前数据与小于等于区下一个数据交互位置，小于等于区 往右扩充一个()
 * 2、小于等于区下一个 大于P，不移动
 *
 * @author liuqj
 */
public class FastSort {

    /**
     * 快速排序
     */
    @Test
    public void testFast01() {
        for (int i = 0; i < 100; i++) {
            int[] arr = RandomArray.randomArr(10, 10);
            if (arr.length <= 1) {
                continue;
            }
            RandomArray.printArr(arr);
            process(arr, 0, arr.length - 1);
            RandomArray.printArr(arr);
            System.out.println("====================");
        }

    }


    public void process(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        if (L == R) {
            return;
        }
        int[] partition = partition(arr, L, R);
        if (partition.length > 1) {
            process(arr, L, partition[0] - 1);
            process(arr, partition[1] + 1, R);
        }
    }

    /**
     * 分区 得到
     * @param arr
     * @param L
     * @param R
     * @return
     */
    private int[] partition(int[] arr, int L, int R) {
        int lessR = L - 1;
        int greaterL = R;
        int index = L;
        int P = arr[R];
        while (index < greaterL) {
            if (arr[index] < P) {
                RandomArray.swap(arr, ++lessR, index++);
            } else if (arr[index] > P) {
                RandomArray.swap(arr, index, --greaterL);
            } else {
                index++;
            }
        }
        RandomArray.swap(arr, greaterL, R);
        return new int[]{lessR + 1, greaterL};
    }

    /**
     * [1,4,5,6,7,4,3,4]
     * 1、当前数据 小于 P，当前数据与小于区下一个数据交互位置，小于区 往右扩充一个
     * 2、当前数据 等于P，不移动，当前位置进入下一个
     * 3、当前数据 大于P, 当前位置不变，当前数据与d大于区下一个数据交互位置，大于区 往左扩充一个
     */
    @Test
    public void testPartition2() {
        int[] arr = RandomArray.randomArr(10, 10);
        if (arr.length <= 1) {
            return;
        }
        RandomArray.printArr(arr);
        int lessR = -1;
        int greaterL = arr.length - 1;
        int index = 0;
        int P = arr[arr.length - 1];
        while (index < greaterL) {
            if (arr[index] < P) {
                RandomArray.swap(arr, ++lessR, index++);
            } else if (arr[index] > P) {
                RandomArray.swap(arr, index, --greaterL);
            } else {
                index++;
            }
        }
        RandomArray.swap(arr, greaterL, arr.length - 1);
        RandomArray.printArr(arr);
    }


    /**
     * [1,4,5,6,7,4,3,4]
     * 1、小于等于区下一个 小于等于 P，当前数据与小于等于区下一个数据交互位置，小于等于区 往右扩充一个()
     * 2、小于等于区下一个 大于P，不移动
     */
    @Test
    public void testPartition1() {
        int[] arr = RandomArray.randomArr(10, 10);
        if (arr.length <= 1) {
            return;
        }
        RandomArray.printArr(arr);
        int lessEqIndex = -1;
        int index = 0;
        int mostR = arr.length - 1;
        int P = arr[mostR];
        while (index <= mostR) {
            if (arr[index] <= P) {
                RandomArray.swap(arr, index++, ++lessEqIndex);
            } else {
                index++;
            }
        }
        RandomArray.printArr(arr);
    }
}
