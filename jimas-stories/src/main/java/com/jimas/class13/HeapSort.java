package com.jimas.class13;

import com.jimas.RandomArray;
import scala.math.Ordering;

import java.security.NoSuchAlgorithmException;

/**
 * 堆排序 时间复杂度 O(logN)
 * 堆就是完全二叉树，eg：大根堆，小根堆
 * 大根堆：任意一个子树包含自己 根节点都大于或等于左右子节点
 * 小根堆：任意一个子树包含自己 根节点都小于或等于左右子节点
 * <p>
 * 堆 用数组存储，其特性：
 * <p>index位置的数 其父节点位置=(index-1)/2,其左孩子位置为 2*index+1，其右孩子位置为2*index+2<p/>
 *
 * @author liuqj
 */
public class HeapSort {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        for (int j = 0; j < 1; j++) {

//            final int[] randomArr = new int[]{3,1,8,8,4,8,3,5,5};
            final int[] randomArr = RandomArray.randomArr(10, 10);
            if (randomArr.length < 1) {
                continue;
            }
            RandomArray.printArr(randomArr);
            //先排成大根堆
            for (int i = 0; i < randomArr.length; i++) {
                heapInsert(randomArr, i);
            }

            //其实在数组元素已经确认的情况下，可以只用heapify 生成大根堆
            /*for (int length = randomArr.length; length > 0; length--) {
                heapify(randomArr, length - 1, randomArr.length);
            }*/

            RandomArray.printArr(randomArr);
            //把第一个（最大的数）与最后一个交互位置，然后最后一个位置像左移动一位，再把余下的数排成大跟堆
            for (int i = randomArr.length - 1; i >= 0; i--) {
                RandomArray.swap(randomArr, 0, i);
                heapify(randomArr, 0, i);
            }
            RandomArray.printArr(randomArr);
            System.out.println("========================");

        }

    }

    /**
     * arr[index] 刚来的数，往上排
     * 与父亲比较，比父亲大，便交互位置
     * 类似 offer 插入数据，默认插入数组末尾
     *
     * @param arr 数组
     * @param index 最后一个位置索引
     * @see java.util.PriorityQueue#siftUpComparable(int, Object, Object[])
     */
    public static void heapInsert(int[] arr, int index) {
        //父节点不比自己大,如果index=0 时，明显 自己不比自己大
        while (arr[index] > arr[(index - 1) / 2]) {
            RandomArray.swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }

    }

    /**
     * arr[index]位置的数，能否往下移动
     * 类似 pop，默认取数组第一个元素，其他数据要移动 重新搞成大根堆
     *
     * @param arr      数组
     * @param index    当前移动位置
     * @param heapSize 堆大小
     * @see java.util.PriorityQueue#siftDownComparable(int, Object, Object[], int)
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        int leftChild = index * 2 + 1;
        //说明有子树
        while (leftChild < heapSize) {
            int maxChildIndex = leftChild + 1 < heapSize && arr[leftChild + 1] > arr[leftChild] ? leftChild + 1 : leftChild;
            int maxIndex = arr[maxChildIndex] > arr[index] ? maxChildIndex : index;
            if (index == maxIndex) {
                break;
            }
            RandomArray.swap(arr, index, maxIndex);
            index = maxIndex;
            leftChild = index * 2 + 1;
        }
    }

}
