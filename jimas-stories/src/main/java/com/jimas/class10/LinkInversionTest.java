package com.jimas.class10;

import com.jimas.class10.entity.DoubleNode;
import com.jimas.class10.entity.Node;
import com.jimas.class10.utils.RandomLinkTools;
import org.junit.Test;

/**
 * 链表反转
 *
 * @author liuqj
 */
public class LinkInversionTest {

    /**
     * 单链表反转
     */
    @Test
    public void nodeInversionTest() {
        for (int i = 0; i < 100; i++) {
            Node head = RandomLinkTools.generateNodes(8, 10);
            System.out.println(head);
            Node newHead = nodeInversion(head);
            System.out.println(newHead);
        }
    }

    /**
     * 单链表反转 返回 新 头节点
     *
     * @param head
     * @return
     */
    private Node nodeInversion(Node head) {
        if (head == null) {
            return null;
        }
        Node newHead = null;
        Node currNode = head;
        while (currNode != null) {
            currNode = currNode.next;
            head.next = newHead;
            newHead = head;
            head = currNode;
        }
        return newHead;
    }

    /**
     * 双链表反转
     */
    @Test
    public void doubleNodeInversionTest() {
        for (int i = 0; i < 100; i++) {
            DoubleNode head = RandomLinkTools.generateDoubleNodes(8, 10);
            System.out.println(head);
            DoubleNode newHead = doubleNodeInversion(head);
            System.out.println(newHead);
        }
    }

    private DoubleNode doubleNodeInversion(DoubleNode head) {
        if (head == null) {
            return null;
        }
        DoubleNode pre = null;
        DoubleNode next;
        while (head != null) {
            next = head.next;
            head.pre = next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
}
