package com.jimas.web.utils;

/**
 * @author liuqj
 */
public class RandomUtils {
    /**
     * 得到随机值
     *
     * @param minVal
     * @param maxVal
     * @return
     */
    public static int random(int minVal, int maxVal) {
        return (int) (Math.random() * (maxVal - minVal)) + minVal;
    }

    /**
     * 得到随机值
     *
     * @param maxVal
     * @return
     */
    public static int random(int maxVal) {
        return random(0, maxVal);
    }
}
