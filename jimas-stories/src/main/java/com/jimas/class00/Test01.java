package com.jimas.class00;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 两个有序数组，合并成一个有序数组
 *
 * @author liuqj
 */
public class Test01 {

    public static void main(String[] args) {
        int[] arr01 = new int[]{1, 3, 5};
        int[] arr02 = new int[]{0, 2, 6, 10, 17, 19, 30};

        Integer[] ansOne = methodOne(arr01, arr02);
        int[] ansTwo = methodTwo(arr01, arr02);
        for (int i = 0; i < ansOne.length; i++) {
            if (ansOne[i] != ansTwo[i]) {
                System.out.println("ERROR");
            }
        }
        System.out.println("success");


    }

    private static int[] methodTwo(int[] arrOne, int[] arrTwo) {
        int lengthOne = 0;
        int lengthTwo = 0;
        if (arrOne != null) {
            lengthOne = arrOne.length;
        } else {
            return arrTwo;
        }
        if (arrTwo != null) {
            lengthTwo = arrTwo.length;
        } else {
            return arrOne;
        }
        int[] ansArray = new int[lengthOne + lengthTwo];
        int j = 0;
        int twoIndex = 0;
        int oneIndex = 0;
        for (int i = 0; i < ansArray.length; i++) {
            if (oneIndex == arrOne.length || twoIndex == arrTwo.length) {
                break;
            }
            if (arrOne[oneIndex] > arrTwo[twoIndex]) {
                ansArray[j++] = arrTwo[twoIndex];
                twoIndex++;
            } else {
                ansArray[j++] = arrOne[oneIndex];
                oneIndex++;
            }
        }
        for (int i = twoIndex; i < lengthTwo; i++) {
            ansArray[j++] = arrTwo[i];
        }
        for (int i = oneIndex; i < lengthOne; i++) {
            ansArray[j++] = arrOne[i];
        }
        return ansArray;

    }

    private static Integer[] methodOne(int[] arr01, int[] arr02) {
        List<Integer> objects = new ArrayList<>();
        for (int i = 0; i < arr01.length; i++) {
            objects.add(arr01[i]);
        }
        for (int i = 0; i < arr02.length; i++) {
            objects.add(arr02[i]);
        }
        objects.sort(Comparator.naturalOrder());

        return objects.toArray(new Integer[arr01.length + arr02.length]);


    }


}
