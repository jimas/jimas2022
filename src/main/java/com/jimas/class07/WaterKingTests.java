package com.jimas.class07;

import com.jimas.RandomArray;
import org.junit.Test;

/**
 * 水王数
 * 就是该数出现的次数 大于 数组长度的一半 King > N/2;
 *
 * @author liuqj
 */
public class WaterKingTests {
    @Test
    public void testKingNumber() {
        for (int i = 0; i < 100; i++) {
            int[] ints = RandomArray.randomArr(12, 4);
            if (ints.length == 0) {
                continue;
            }
            RandomArray.printArr(ints);
            int king = getKingNum(ints);
            System.out.println("king num:" + king);
            System.out.println("===================");
        }


    }

    private int getKingNum(int[] ints) {
        //1、移除两个不重复的数
        //候选数
        int cand = 0;
        //血量
        int hp = 0;
        for (int num : ints) {
            //血量为0 切换选手
            if (hp == 0) {
                cand = num;
                hp++;
                //两数不同,血量--（并移除这两个数）
            } else if (num != cand) {
                hp--;
            } else {
                //两数一致 血量++
                hp++;
            }
        }
        //血量为0 则没有水王数
        if (hp == 0) {
            return -1;
        }
        //2、校验第一步余下的数 是否为水王数
        int count = 0;
        for (int num : ints) {
            if (cand == num) {
                count++;
            }
        }
        return count > ints.length / 2 ? cand : -1;
    }
}
