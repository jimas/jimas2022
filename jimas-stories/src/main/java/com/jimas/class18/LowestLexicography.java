package com.jimas.class18;

import com.mchange.v1.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author liuqj
 */
public class LowestLexicography {

    public static void main(String[] args) {
        String[] strArray = new String[]{"b", "ba", "c", "ac"};

        System.out.println(lowestString2(strArray));
        System.out.println(lowestString(strArray));
    }

    private static String lowestString2(String[] strArray) {
        if (strArray == null || strArray.length == 0) {
            return "";
        }
        TreeSet<String> set = process(strArray);
        return set.isEmpty() ? "" : set.first();
    }

    private static TreeSet<String> process(String[] strArray) {
        TreeSet<String> set = new TreeSet<>();
        if (strArray == null || strArray.length == 0) {
            set.add("");
            return set;
        }

        for (int i = 0; i < strArray.length; i++) {
            String first = strArray[i];
            String[] targetArray = removeIndexItem(strArray, i);
            TreeSet<String> treeSet = process(targetArray);
            for (String s : treeSet) {
                set.add(first + s);
            }
        }
        return set;
    }

    private static String[] removeIndexItem(String[] strArray, int index) {
        String[] targetStrArray = new String[strArray.length - 1];
        int newIndex = 0;
        for (int i = 0; i < strArray.length; i++) {
            if (i != index) {
                targetStrArray[newIndex++] = strArray[i];
            }
        }
        return targetStrArray;
    }

    private static String lowestString(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        StringBuilder ans = new StringBuilder();
        Arrays.sort(strs, new MyComparator());
        for (String str : strs) {
            ans.append(str);
        }
        return ans.toString();
    }

    private static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo((b + a));
        }
    }


}
