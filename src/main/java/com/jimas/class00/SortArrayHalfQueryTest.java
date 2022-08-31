package com.jimas.class00;

import org.junit.Test;
import scala.Int;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liuqj
 */
public class SortArrayHalfQueryTest {

    /**
     * 在有序数组中，二分查找某个数是否存在
     */
    @Test
    public void test001() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = ArrayTool.random(10, 10);
            if (arr.length == 0) {
                continue;
            }
            int[] copyArr1 = Arrays.copyOf(arr, arr.length);
            //1、得到有序数组
            ArrayTool.sort(arr);
            ArrayTool.printArray(arr);
            //2、halfQuery
            int index = halfQuery(arr, copyArr1[0]);
            System.out.println("find item " + copyArr1[0] + ":index=" + index);
            System.out.println("====================================");
        }
    }

    /**
     * 在有序数组中，找到等于某个数的最左位置
     */
    @Test
    public void test002() {
        for (int i = 0; i < 10000; i++) {
            int[] arr = ArrayTool.random(10, 10);
            if (arr.length == 0) {
                continue;
            }
            int[] copyArr1 = Arrays.copyOf(arr, arr.length);
            //1、得到有序数组
            ArrayTool.sort(arr);
            ArrayTool.printArray(arr);
            //2、halfQuery
            int index = bestLeftQuery(arr, copyArr1[0]);
            System.out.println("find item " + copyArr1[0] + ":最左的index=" + index);
            System.out.println("====================================");
        }
    }

    /**
     * 在有序数组中，找到等于某个数的最右位置
     */
    @Test
    public void test003() {
        for (int i = 0; i < 10000; i++) {
            int[] arr = ArrayTool.random(10, 10);
            if (arr.length == 0) {
                continue;
            }
            int[] copyArr1 = Arrays.copyOf(arr, arr.length);
            //1、得到有序数组
            ArrayTool.sort(arr);
            ArrayTool.printArray(arr);
            //2、halfQuery
            int index = bestRightQuery(arr, copyArr1[0]);
            System.out.println("find item " + copyArr1[0] + ":最右的index=" + index);
            System.out.println("====================================");
        }
    }

    /**
     * 在相邻不重复数组中，找到一个局部最小值
     */
    @Test
    public void test004() {
        for (int i = 0; i < 100; i++) {
            int[] arr = ArrayTool.randomNoRepeat(10, 10);
            if (arr.length == 0) {
                continue;
            }
            ArrayTool.printArray(arr);
            //2、halfQuery
            int index = localMin(arr);
//            int index = localMin(arr);
            System.out.println("find 局部最小的index=" + index + ",val=" + arr[index]);
            System.out.println("====================================");
        }
    }

    private int localMin(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        if (arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        if (arr.length < 2) {
            return -1;
        }
        int L = 1;
        int R = arr.length - 2;
        while (L < R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] < arr[mid + 1] && arr[mid] < arr[mid - 1]) {
                return mid;
            } else if (arr[mid] > arr[mid + 1]) {
                L = mid + 1;
            } else if (arr[mid] > arr[mid - 1]) {
                R = mid - 1;
            }
        }
        return (arr[L] < arr[L + 1] && arr[L] < arr[L - 1]) ? L : -1;
    }

    private int bestRightQuery(int[] arr, int target) {
        int L = 0;
        int R = arr.length - 1;
        int index = -1;
        while (L < R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] > target) {
                R = mid - 1;
            } else if (arr[mid] < target) {
                L = mid + 1;
            } else {
                index = mid;
                L = mid + 1;
            }
        }
        return arr[L] == target ? L : index;
    }

    private int bestLeftQuery(int[] arr, int target) {
        int L = 0;
        int R = arr.length - 1;
        int index = -1;
        while (L < R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] > target) {
                R = mid - 1;
            } else if (arr[mid] < target) {
                L = mid + 1;
            } else {
                index = mid;
                R = mid - 1;
            }
        }
        return arr[L] == target ? L : index;
    }

    private int halfQuery(int[] arr, int target) {
        int L = 0;
        int R = arr.length - 1;
        while (L < R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] > target) {
                R = mid - 1;
            } else if (arr[mid] < target) {
                L = mid + 1;
            } else {
                return mid;
            }
        }
        return arr[L] == target ? L : -1;
    }

}

class ArrayTool {

    public static int[] random(int maxVal, int maxLength) {
        int length = (int) (Math.random() * maxLength);
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
        }
        return arr;
    }

    /**
     * 各元素间不重复
     *
     * @param maxVal
     * @param maxLength
     * @return
     */
    public static int[] randomNoRepeat(int maxVal, int maxLength) {
        Set<Integer> existSet = new HashSet<>();
        int length = Math.min((int) (Math.random() * maxLength), maxVal);
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            int key = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
            while (existSet.contains(key)) {
                key = (int) (Math.random() * maxVal) - (int) (Math.random() * maxVal);
            }
            arr[i] = key;
            existSet.add(key);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //插入排序
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }
}
