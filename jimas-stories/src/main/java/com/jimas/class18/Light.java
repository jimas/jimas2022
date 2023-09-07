package com.jimas.class18;

import java.util.Objects;

/**
 * 点灯：要求所有"."都被照亮
 * 给定一个只有”X.“的字符串,
 * X 代表墙，不能点灯，也会阻隔灯光。
 * . 可点灯也不不点灯
 * ... 中间的"." 点灯后 可照亮两边的"."
 * 获取最小的灯数量
 *
 * @author liuqj
 */
public class Light {

    public static void main(String[] args) {
        String str = "X...X...X......X...X...X";
        int count = lessLightCount(str);
        System.out.println(count);
    }

    private static int lessLightCount(String str) {
        if (Objects.isNull(str)) {
            return 0;
        }
        int light = 0;
        char[] charArray = str.toCharArray();
        int i = 0;
        while (i < charArray.length) {
            //墙
            if (charArray[i] == 'X') {
                i = i + 1;
            } else {
                light++;
                if (i + 1 >= charArray.length) {
                    break;
                }
                if (charArray[i + 1] == 'X') {
                    i = i + 2;
                } else {
                    i = i + 3;
                }
            }
        }
        return light;
    }
}
