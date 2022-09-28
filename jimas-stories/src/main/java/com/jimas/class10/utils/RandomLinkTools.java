package com.jimas.class10.utils;

import com.jimas.class10.entity.DoubleNode;
import com.jimas.class10.entity.Node;

/**
 * 链表生成器
 *
 * @author liuqj
 */
public class RandomLinkTools {
    /**
     * 单链表生成器
     *
     * @param maxLength
     * @param maxVal
     * @return
     */
    public static Node generateNodes(int maxLength, int maxVal) {
        int length = randomNum(maxLength);
        Node head = null;
        Node next = null;
        if (length > 0) {
            head = new Node(randomNum(maxVal), null);
            next = head;
            length--;
        }
        while ((length--) > 0) {
            next.next = new Node(randomNum(maxVal), null);
            next = next.next;
        }
        return head;
    }

    /**
     * 双链表生成器
     *
     * @param maxLength
     * @param maxVal
     * @return
     */
    public static DoubleNode generateDoubleNodes(int maxLength, int maxVal) {
        int length = randomNum(maxLength);
        DoubleNode head = null;
        DoubleNode next = null;
        if (length > 0) {
            head = new DoubleNode(randomNum(maxVal), null, null);
            next = head;
            length--;
        }
        while ((length--) > 0) {
            next.next = new DoubleNode(randomNum(maxVal), next, null);
            next = next.next;
        }
        return head;
    }

    public static int nodeLength(Node node) {
        int length = 0;
        while (node != null) {
            length++;
            node = node.next;
        }

        return length;

    }

    public static int doubleNodeLength(DoubleNode node) {
        int length = 0;
        while (node != null) {
            length++;
            node = node.next;
        }
        return length;

    }

    /**
     * 产生随机值
     *
     * @param maxVal
     * @return
     */
    public static int randomNum(int maxVal) {
        return (int) (Math.random() * maxVal);
    }
}
