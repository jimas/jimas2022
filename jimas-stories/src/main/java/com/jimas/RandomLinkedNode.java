package com.jimas;

/**
 * @author liuqj
 */
public class RandomLinkedNode {
    /**
     * 获取单向链表
     *
     * @param maxLength 最大链表长度
     * @param maxVal    最大链表值
     * @return
     */
    public static Node randomLinkedList(int maxLength, int maxVal) {
        int val = (int) (Math.random() * maxVal);
        int length = (int) (Math.random() * maxLength) + 1;
        Node node = new Node(val);
        length--;
        if (length > 0) {
            node.next = randomLinkedList(length, maxVal);
        }
        return node;
    }

    /**
     * 获取单向存在环形的链表
     *
     * @param maxNoRingLength 最大无环部分长度
     * @param maxRingLength   最大环形部分长度
     * @param maxVal          最大链表值
     * @return
     */
    public static Node randomRingLinkedList(int maxNoRingLength, int maxRingLength, int maxVal) {
        Node root = randomLinkedList(maxNoRingLength, maxVal);
        Node ringFirstNode = randomLinkedList(maxRingLength, maxVal);
        getLastNode(ringFirstNode).next = getLastNode(root);
        getLastNode(root).next = ringFirstNode;
        return root;
    }

    /**
     * 得到单链表的最后一个节点
     *
     * @param node 表头节点
     * @return
     */
    public static Node getLastNode(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        return getLastNode(node.next);
    }

    public static int linkLength(Node head) {
        int length = 0;
        Node next = head;
        while (next != null) {
            length++;
            next = next.next;
        }
        return length;
    }

    /**
     * 下中点
     *
     * @param head
     * @return
     */
    public static Node middleDownNode(Node head) {
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 上中点
     *
     * @param head
     * @return
     */
    public static Node middleUpNode(Node head) {
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static class Node {
        private int val;
        private Node next;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

    }


    public static class RandNode extends Node {
        private Node rand;

        public Node getRand() {
            return rand;
        }

        public void setRand(Node rand) {
            this.rand = rand;
        }

        public RandNode(int val) {
            super(val);
        }

        public RandNode(int val, Node next, Node rand) {
            super(val, next);
            this.rand = rand;
        }

        @Override
        public String toString() {
            return "RandNode{" +
                    "val=" + super.getVal() +
                    ", next=" + super.next +
                    '}';
        }
    }
}