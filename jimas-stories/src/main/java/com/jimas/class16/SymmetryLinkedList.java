package com.jimas.class16;

import com.jimas.RandomLinkedNode;

/**
 * 对称链表（链表回文）
 * 比如：1 -> 2 -> 3 -> 2 -> 1
 * 1 -> 2 -> 2 -> 1
 *
 * @author liuqj
 */
public class SymmetryLinkedList {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            RandomLinkedNode.Node head = RandomLinkedNode.randomLinkedList(10, 20);
            //先找出下中点
            RandomLinkedNode.Node middle = RandomLinkedNode.middleDownNode(head);
            //把下中点后面的节点指向逆序
            RandomLinkedNode.Node tail = reversalStartWithMiddleNode(middle);
            RandomLinkedNode.Node h = head;
            RandomLinkedNode.Node t = tail;
            boolean flag = true;
            while (t != middle) {
                if (h.getVal() != t.getVal()) {
                    flag = false;
                    break;
                }
                h = h.getNext();
                t = t.getNext();
            }
            flag = flag && h.getVal() == t.getVal();
            //将链表 恢复原样
            reversalStartWithMiddleNode(tail);
            if (flag) {
                System.out.println("回文链表");
                System.out.println(head);
                System.out.println("===========================");
            }
        }
    }

    /**
     * 从下中节点开始逆序
     *
     * @param middle 下中节点
     * @return 并返回尾节点
     */
    private static RandomLinkedNode.Node reversalStartWithMiddleNode(RandomLinkedNode.Node middle) {
        RandomLinkedNode.Node pre = null;
        RandomLinkedNode.Node next = middle;
        RandomLinkedNode.Node current = middle;
        while (next != null) {
            next = next.getNext();
            current.setNext(pre);
            pre = current;
            current = next;
        }
        return pre;
    }
}
