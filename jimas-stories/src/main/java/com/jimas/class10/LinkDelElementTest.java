package com.jimas.class10;

import com.jimas.class10.entity.DoubleNode;
import com.jimas.class10.entity.Node;
import com.jimas.class10.utils.RandomLinkTools;
import org.junit.Test;

import java.util.Objects;

/**
 * 链表删除指定值
 *
 * @author liuqj
 */
public class LinkDelElementTest {
    /**
     * 单链表删除值
     */
    @Test
    public void testDelLinkNode() {

        for (int i = 0; i < 100; i++) {
            Node head = RandomLinkTools.generateNodes(10, 10);
            System.out.println(head);
            Node newHead = delLinkNode(head, RandomLinkTools.randomNum(10));
            System.out.println(newHead);
            System.out.println("================");
        }

    }

    /**
     * 双链表删除值
     */
    @Test
    public void testDelLinkDoubleNode() {

        for (int i = 0; i < 100; i++) {
            DoubleNode head = RandomLinkTools.generateDoubleNodes(10, 10);
            System.out.println(head);
            DoubleNode newHead = delLinkDoubleNode(head, RandomLinkTools.randomNum(10));
            System.out.println(newHead);
            System.out.println("================");
        }

    }

    private DoubleNode delLinkDoubleNode(DoubleNode head, int num) {
        if (head == null) {
            return null;
        }
        System.out.println("num:" + num);
        //先找到新的头节点
        DoubleNode newHead = head;
        while (newHead.val == num) {
            newHead = newHead.next;
            //全部移除
            if (newHead == null) {
                return null;
            }
        }
        DoubleNode next = newHead.next;
        newHead.next = null;
        DoubleNode currNode = newHead;
        while (next != null) {
            if (!Objects.equals(next.val, num)) {
                currNode.next = next;
                currNode = next;
            }
            next = next.next;
        }
        currNode.next = null;
        return newHead;
    }

    private Node delLinkNode(Node head, int num) {
        if (head == null) {
            return null;
        }
        System.out.println("num:" + num);
        //找到新head
        Node newHead = head;
        while (newHead.val == num) {
            newHead = newHead.next;
            //全部移除
            if (newHead == null) {
                return null;
            }
        }
        Node next = newHead.next;
        newHead.next = null;
        Node currNode = newHead;
        while (next != null) {
            if (!Objects.equals(next.val, num)) {
                currNode.next = next;
                currNode = next;
            }
            next = next.next;
        }
        currNode.next = null;
        return newHead;
    }

}
