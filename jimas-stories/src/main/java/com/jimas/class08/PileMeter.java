package com.jimas.class08;

import java.util.HashSet;
import java.util.Set;

/**
 * 堆数器
 *
 * @author liuqj
 */
public class PileMeter {
    /**
     * 有一个数组中，有一种数出现了k次，其他数都出现了m次，其中 k<m 。
     * Math.random() -->[0,1)
     *
     * @return
     */
    public static int[] randomArr0805(int kinds,int m,int k) {
        //有多少种数，至少存在两种数
        int[] ints = new int[k + (kinds - 1) * m];
        int i = 0;
        Set<Integer> set = new HashSet<>();
        int uniqueNum = getUniqueNum(set, i);
        for (; i < k; i++) {
            ints[i] = uniqueNum;
        }
        for (int i1 = 0; i1 < kinds - 1; i1++) {
            uniqueNum = getUniqueNum(set, i);
            for (; i < k + m * (i1 + 1); i++) {
                ints[i] = uniqueNum;
            }
        }
        return ints;
    }

    /**
     * 一个int数组中有两种数出现了奇数次，其他都出现了偶数次
     *
     * @return
     */
    public static int[] randomArr0804() {
        //奇数
        int oddNumberTime = 0;
        //偶数
        int evenNumberTime = 0;
        int time = (int) (Math.random() * 3 + 1);
        //time 偶数
        if (time % 2 == 0) {
            evenNumberTime = time;
            oddNumberTime = time + 1;
        } else {
            evenNumberTime = time + 1;
            oddNumberTime = time;
        }
        //有多少种数，至少存在两种数
        int kinds = 2 + (int) (Math.random() * 3 + 1);
        int[] ints = new int[2 * oddNumberTime + (kinds - 2) * evenNumberTime];
        Set<Integer> set = new HashSet<>();
        int i = 0;
        int uniqueNum = getUniqueNum(set, i);
        for (; i < oddNumberTime; i++) {
            ints[i] = uniqueNum;
        }
        uniqueNum = getUniqueNum(set, i);
        for (; i < oddNumberTime + oddNumberTime; i++) {
            ints[i] = uniqueNum;
        }
        for (int i1 = 0; i1 < kinds - 2; i1++) {
            uniqueNum = getUniqueNum(set, i);
            for (; i < 2 * oddNumberTime + evenNumberTime * (i1 + 1); i++) {
                ints[i] = uniqueNum;
            }
        }
        return ints;
    }

    private static int getUniqueNum(Set<Integer> set, int i) {
        int num = getRandomInt(8, set);
        set.add(num);
        return num;
    }

    /**
     * 得到随机值
     *
     * @param maxVal
     * @param set
     * @return
     */
    private static int getRandomInt(int maxVal, Set<Integer> set) {
        int num;
        do {
            num = getRandomInt(maxVal);
        } while (set.contains(num));
        return num;
    }

    /**
     * 得到随机值
     *
     * @param maxVal
     * @return
     */
    private static int getRandomInt(int maxVal) {
        return (int) (Math.random() * maxVal + 1) - (int) (Math.random() * maxVal + 1);
    }
}
