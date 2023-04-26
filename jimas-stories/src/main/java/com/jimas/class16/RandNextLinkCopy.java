package com.jimas.class16;

import com.jimas.RandomLinkedNode;

import java.util.HashMap;

/**
 * 特殊链表复制 1234@56789aA
 * 这个链表 不只有next  还多了一个 rand 指针
 * 核心：构造出 原 与 拷贝 之间的对应关系
 * https://leetcode.com/problems/copy-list-with-random-pointer/
 *
 * @author liuqj
 * @see com.jimas.RandomLinkedNode.RandNode
 */
public class RandNextLinkCopy {

    public static void main(String[] args) {
        RandomLinkedNode.RandNode head = new RandomLinkedNode.RandNode(1);
        RandomLinkedNode.RandNode one = new RandomLinkedNode.RandNode(2);
        RandomLinkedNode.RandNode two = new RandomLinkedNode.RandNode(3);
        RandomLinkedNode.RandNode three = new RandomLinkedNode.RandNode(4);
        RandomLinkedNode.RandNode four = new RandomLinkedNode.RandNode(7);
        RandomLinkedNode.RandNode five = new RandomLinkedNode.RandNode(10);

        head.setNext(one);
        one.setNext(two);
        two.setNext(three);
        three.setNext(four);
        four.setNext(five);

        head.setRand(five);
        five.setRand(one);
        four.setRand(two);
        three.setRand(null);
        two.setRand(head);
        System.out.println(head);
//        RandomLinkedNode.RandNode newHead = executeByMap(head);
        RandomLinkedNode.RandNode newHead = executeByLink(head);
        System.out.println(newHead);

    }

    private static RandomLinkedNode.RandNode executeByLink(RandomLinkedNode.RandNode head) {
        if (head == null) {
            return null;
        }
        RandomLinkedNode.RandNode next = head;
        RandomLinkedNode.RandNode current = head;
        //第一个while 构造出 A->A`->B->B`->C->C`->null
        while (next != null) {
            RandomLinkedNode.RandNode copy = new RandomLinkedNode.RandNode(next.getVal());
            next = (RandomLinkedNode.RandNode) next.getNext();
            current.setNext(copy);
            copy.setNext(next);
            current = next;
        }
        //第二个while循环 构造 rand
        next = head;
        while (next != null) {
            RandomLinkedNode.RandNode copy = (RandomLinkedNode.RandNode) next.getNext();
            copy.setRand(next.getRand() == null ? null : next.getRand().getNext());
            next = (RandomLinkedNode.RandNode) next.getNext().getNext();
        }
        next = head;
        current = head;
        RandomLinkedNode.RandNode newHead = (RandomLinkedNode.RandNode) head.getNext();
        //第三个while 分离出 A`->B`->C`
        while (next != null) {
            next = (RandomLinkedNode.RandNode) next.getNext().getNext();
            RandomLinkedNode.RandNode copy = (RandomLinkedNode.RandNode) current.getNext();
            current.setNext(next);
            copy.setNext(next == null ? null : next.getNext());
        }
        return newHead;
    }


    /**
     * 利用 map  构造出 source 与 copy 的对应关系
     *
     * @param head
     * @return
     */
    private static RandomLinkedNode.RandNode executeByMap(RandomLinkedNode.RandNode head) {
        HashMap<RandomLinkedNode.RandNode, RandomLinkedNode.RandNode> nodeHashMap = new HashMap<>();
        RandomLinkedNode.RandNode next = head;
        //第一个while 循环 构造出 source 与 copy 的对应关系
        while (next != null) {
            nodeHashMap.put(next, new RandomLinkedNode.RandNode(next.getVal()));
            next = (RandomLinkedNode.RandNode) next.getNext();
        }
        //第二个while 循环，从map 中 取出copy 节点，然后 在根据 真实的next、rand 关系  从map 中取出 copy的 next rand
        RandomLinkedNode.RandNode next2 = head;
        while (next2 != null) {
            RandomLinkedNode.RandNode newNode = nodeHashMap.get(next2);
            newNode.setNext(nodeHashMap.get(next2.getNext()));
            newNode.setRand(nodeHashMap.get(next2.getRand()));
            next2 = (RandomLinkedNode.RandNode) next2.getNext();
        }
        return nodeHashMap.get(head);
    }
}
