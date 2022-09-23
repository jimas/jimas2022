package com.jimas.jdk11;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author liuqj
 */
public class Demo01 {

    public static void main(String[] args) {
        typeInference();

    }

    /**
     * var 类型推断
     */
    private static void typeInference() {
        var s = "sssas";
        var list = new ArrayList<Integer>();

        var linkedList = new LinkedList<String>();

        list.add(12);
        list.add(13);
        list.add(14);

        linkedList.add(s);
        System.out.println(linkedList);
        System.out.println(list);
    }
}
