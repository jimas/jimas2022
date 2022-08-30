package com.jimas.class00;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * 选择
 *
 * @author liuqj
 */
public class SortTest {
    @Test
    public void testSort001() {
        for (int i = 0; i < 100; i++) {
            insertSort();
        }

    }

    /**
     * 先把最小的放在0号位置，次小的放在1号位置~
     */
    private void selectSort() {
        int[] arr = RandomArray.randomArr(10, 10);
        if (arr == null || arr.length < 2) {
            return;
        }
        RandomArray.printArr(arr);
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            RandomArray.swap(arr, i, minIndex);
        }
        RandomArray.printArr(arr);
        System.out.println("===========================");
    }

    /**
     * 先把最最大的放在N号位置，次大的放在N-1号位置~
     */
    private void bubbleSort() {
        int[] arr = RandomArray.randomArr(10, 10);
        if (arr == null || arr.length < 2) {
            return;
        }
        RandomArray.printArr(arr);
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (arr[j] < arr[j - 1]) {
                    RandomArray.swap(arr, j, j - 1);
                }
            }
        }
        RandomArray.printArr(arr);
        System.out.println("================");
    }

    private void insertSort() {
        int[] arr = RandomArray.randomArr(10, 10);
        if (arr == null || arr.length < 2) {
            return;
        }
        RandomArray.printArr(arr);
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    RandomArray.swap(arr, j, j - 1);
                }
            }
        }
        RandomArray.printArr(arr);
        System.out.println("===========================");
    }
}
