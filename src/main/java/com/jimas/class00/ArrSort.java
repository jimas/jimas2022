package com.jimas.class00;


import com.jimas.RandomArray;

/**
 * @author liuqj
 */
public class ArrSort {

    public void insert() {
        int[] arr = RandomArray.randomArr(10, 15);
        printArr(arr);
        if (arr.length > 1) {
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = i + 1; j > 0; j--) {
                    if (arr[j] < arr[j - 1]) {
                        RandomArray.swap(arr, j - 1, j);
                    }
                }
            }
        }
        printArr(arr);
    }

    public void select() {
        int[] arr = RandomArray.randomArr(10, 15);
        printArr(arr);
        if (arr.length > 1) {
            for (int i = 0; i < arr.length; i++) {
                int min = arr[i];
                int minIndex = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] < min) {
                        min = arr[j];
                        minIndex = j;
                    }
                }
                RandomArray.swap(arr, i, minIndex);
            }
        }
        printArr(arr);
    }


    public void bubble() {
        int[] arr = RandomArray.randomArr(10, 11);
        printArr(arr);
        if (arr.length > 1) {
            //循环多少次
            for (int i = 0; i < arr.length; i++) {
                for (int j = 1; j < arr.length - i; j++) {
                    if (arr[j - 1] > arr[j]) {
                        RandomArray.swap(arr, j - 1, j);
                    }
                }
            }
        }
        printArr(arr);
    }

    public static void printArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }


}
