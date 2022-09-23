package com.jimas.class08;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * 异或 就是 二进制 无进位相加(相同为 0，不同为1)
 * a^0 = a
 * a^a = 0
 * a^b^c = (a^b)^c = (a^c)^b
 *
 * @author liuqj
 */
public class Class08Test {
    /**
     * 一个数组中 有一个数出现了奇数次，其他数都出现了偶数次，打印这个数。
     */
    @Test
    public void test0801() {
        int[] arr = {-1, 2, 3, 2, 3, 1, 3, 1, 3, 4, 5, 4, 5};
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        if (eor != 0) {
            System.out.println(eor);
        } else {
            System.out.println("没找到这个数");
        }
    }

    /**
     * 不借助其他变量，怎么交互两个数
     * 慎用：数组中同一个位置的数 比如 i=j 位置 这样交互 会导致 这个位置为0
     */
    @Test
    public void test0802() {
        int a = 10;
        int b = 10;
        a = a ^ b;
        //b=a^b^b=a^(b^b)=a^0=a;
        b = a ^ b;
        //a=a^b^a=(a^a)^b=b
        a = a ^ b;
        System.out.println(a + "," + b);
    }

    /**
     * 怎么把一个int类型的数 提取出最右侧的1来
     * ~a + 1 = -a
     */
    @Test
    public void test0803() {
        int a = 801212240;
        printInt32(a);
        int b = a & (~a + 1);
        printInt32(b);
    }

    /**
     * int[] arr = {1, 2, 1, 2, -3, 7, -3, 7, -3, 7, 4, 4, 5, 6, 5, 6};
     * 1、一个int数组中有两种数出现了奇数次，其他都出现了偶数次，怎么找到并打印这两种数？
     * 2、eor = a^b (a b 就是要找的 出现奇数次的数)
     * 3、rightOneNum ：提取出 eor 中最右侧的1 （因为a!=b,那么eor一定不为0,肯定存在某一位为1，即该位置上 a 与 b 不等） 假设这个位置是3
     * 4、数组arr中，一定存在 a的3位置为1 b 的3位置为0（或者b的3位置为1，a的3位置为0），其他偶数次的数也会平均分成两组（3位置为0的一组，3位置为1的一组）
     * 5、我们针对 3 位置为1的一组与 eor 进行 异或 最终得到结果a
     * 6、a 在与 eor 异或 得到 b
     */
    @Test
    public void test0804() {
        for (int i = 0; i < 10000; i++) {
            int[] arr = PileMeter.randomArr0804();
            RandomArray.printArr(arr);
            int eor = 0;
            for (int e : arr) {
                eor ^= e;
            }
            int rightOneNum = eor & (-eor);
            int a = eor;
            for (int e : arr) {
                if ((rightOneNum & e) != 0) {
                    a ^= e;
                }
            }
            System.out.println(a + "," + (a ^ eor));
            System.out.println("=====================");
        }

    }


    /**
     * int k = 1;
     * int m = 3;
     * int[] arr = {11, 11, 11, 13, 2, 3, 4, 2, 3, 4, 2, 3, 4};
     * 有一个数组中，有一种数出现了k次，其他数都出现了m次，其中 k<m, 怎么找到出现k次的数，要求空间复杂度O(1),时间复杂度O(N)。
     */
    @Test
    public void test0805() {
        for (int index = 0; index < 1000; index++) {
            int kinds = 2 + (int) (Math.random() * 2 + 1);
            int k = (int) (Math.random() * 3 + 1);
            int m = k + (int) (Math.random() * 3 + 1);
            int[] arr = PileMeter.randomArr0805(kinds, m, k);
            RandomArray.printArr(arr);
            //定义一个 int 32位数组，记录某个二进制位出现1的次数
            int[] ansArr = new int[32];
            for (int j = 0; j < arr.length; j++) {
                for (int i1 = 0; i1 < ansArr.length; i1++) {
                    ansArr[i1] += ((arr[j] >> i1) & 1);
                }
            }
            int ans = 0;
            for (int j = 0; j < ansArr.length; j++) {
                if (ansArr[j] % m != 0) {
                    ans |= (1 << j);
                }
            }
            System.out.println(ans);
            System.out.println("====================");
        }


    }


    private void printInt32(int a) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((a & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }
}
