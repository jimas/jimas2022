package com.jimas.class16;

import com.jimas.RandomLinkedNode;

import java.util.function.Function;

/**
 * @author liuqj
 */
public class FastSlowPointer {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            RandomLinkedNode.Node head = RandomLinkedNode.randomLinkedList(10, 20);
            System.out.println(head);
            Function<RandomLinkedNode.Node, RandomLinkedNode.Node> functionOne = (a) -> methodOne(a);
            Function<RandomLinkedNode.Node, RandomLinkedNode.Node> functionTwo = (a) -> methodTwo(a);
            Function<RandomLinkedNode.Node, RandomLinkedNode.Node> functionThree = (a) -> methodThree(a);
            Function<RandomLinkedNode.Node, RandomLinkedNode.Node> functionFour = (a) -> methodFour(a);
            System.out.println(invoke(head, functionOne).getVal());
            System.out.println(invoke(head, functionTwo).getVal());
            System.out.println(invoke(head, functionThree).getVal());
            System.out.println(invoke(head, functionFour).getVal());

            System.out.println("============================");
        }


    }

    public static RandomLinkedNode.Node invoke(RandomLinkedNode.Node head, Function<RandomLinkedNode.Node, RandomLinkedNode.Node> function) {
        return function.apply(head);
    }

    /**
     * 1、输入链表头节点，奇数长度 返回中点，偶数长度返回上中点
     *
     * @param head
     * @return
     */
    private static RandomLinkedNode.Node methodOne(RandomLinkedNode.Node head) {
        if (head == null) {
            return null;
        }
        return RandomLinkedNode.middleUpNode(head);
    }

    /**
     * 2、输入链表头节点，奇数长度 返回中点，偶数长度返回下中点
     *
     * @param head
     * @return
     */
    private static RandomLinkedNode.Node methodTwo(RandomLinkedNode.Node head) {
        if (head == null) {
            return null;
        }
        return RandomLinkedNode.middleDownNode(head);
    }

    /**
     * 3、输入链表头节点，奇数长度 返回中点前一个，偶数长度返回上中点前一个
     *
     * @param head
     * @return
     */
    private static RandomLinkedNode.Node methodThree(RandomLinkedNode.Node head) {
        if (head == null) {
            return null;
        }
        RandomLinkedNode.Node middleUpNode = RandomLinkedNode.middleUpNode(head);
        RandomLinkedNode.Node targetNode = head;
        if (targetNode == middleUpNode) {
            return targetNode;
        }
        while (targetNode != null && targetNode.getNext() != middleUpNode) {
            targetNode = targetNode.getNext();
        }
        return targetNode;
    }

    /**
     * 4、输入链表头节点，奇数长度 返回中点前一个，偶数长度返回下中点前一个
     *
     * @param head
     * @return
     */
    private static RandomLinkedNode.Node methodFour(RandomLinkedNode.Node head) {
        if (head == null) {
            return null;
        }
        RandomLinkedNode.Node middleDownNode = RandomLinkedNode.middleDownNode(head);
        RandomLinkedNode.Node targetNode = head;
        if (targetNode == middleDownNode) {
            return targetNode;
        }
        while (targetNode != null && targetNode.getNext() != middleDownNode) {
            targetNode = targetNode.getNext();
        }
        return targetNode;
    }
}
