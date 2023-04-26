package com.jimas.class16;

import com.jimas.RandomLinkedNode;

/**
 * 链表 分区展示：
 * 给定一个 单链表 头节点head，与一个 边界值 boundVal，
 * 小于这个边界值放左边，
 * 等于边界值放中间，
 * 大于边界值放右边
 * Node{val=5, next=Node{val=18, next=Node{val=2, next=null}}}
 * Node{val=2, next=Node{val=5, next=null}}  boundVal:5
 *
 * @author liuqj
 */
public class PartitionLink {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int maxVal = 20;
//            RandomLinkedNode.Node head = sampleNode();
            RandomLinkedNode.Node head = RandomLinkedNode.randomLinkedList(15, maxVal);
            int boundVal = 5;
            System.out.println(head);
            RandomLinkedNode.Node newHead = partitionLink(head, boundVal);
            System.out.println(newHead + "  boundVal:" + boundVal);
            System.out.println("========================");
        }

    }

    private static RandomLinkedNode.Node sampleNode() {
        RandomLinkedNode.Node head = new RandomLinkedNode.Node(5);
        RandomLinkedNode.Node one = new RandomLinkedNode.Node(18);
        RandomLinkedNode.Node two = new RandomLinkedNode.Node(2);
        head.setNext(one);
        one.setNext(two);
        return head;
    }

    private static RandomLinkedNode.Node partitionLink(RandomLinkedNode.Node head, int boundVal) {
        //6个变量
        RandomLinkedNode.Node sH = null;
        RandomLinkedNode.Node sT = null;
        RandomLinkedNode.Node eH = null;
        RandomLinkedNode.Node eT = null;
        RandomLinkedNode.Node gH = null;
        RandomLinkedNode.Node gT = null;
        RandomLinkedNode.Node next = head;
        while (next != null) {
            if (next.getVal() < boundVal) {
                if (sH == null) {
                    sH = next;
                } else {
                    sT.setNext(next);
                }
                sT = next;
            } else if (next.getVal() == boundVal) {
                if (eH == null) {
                    eH = next;
                } else {
                    eT.setNext(next);
                }
                eT = next;
            } else {
                if (gH == null) {
                    gH = next;
                } else {
                    gT.setNext(next);
                }
                gT = next;
            }
            next = next.getNext();
            //摘掉原链表next指针
            head.setNext(null);
            head = next;
        }

        RandomLinkedNode.Node newHead;
        //sT 连eH, eT 连gH  就大功告成
        if (sH != null) {
            newHead = sH;
            sT.setNext(eH == null ? gH : eH);
            if (eH != null) {
                eT.setNext(gH);
            }
        } else if (eH != null) {
            newHead = eH;
            eT.setNext(gH);
        } else {
            newHead = gH;
        }
        return newHead;
    }
}
