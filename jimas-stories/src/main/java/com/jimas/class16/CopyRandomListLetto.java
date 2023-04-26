package com.jimas.class16;

/**
 * @author liuqj
 */
public class CopyRandomListLetto {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node next = head;
        Node current = head;
        //第一个while 构造出 A->A`->B->B`->C->C`->null
        while (next != null) {
            Node copy = new Node(next.val);
            next = next.next;
            current.next = copy;
            copy.next = next;
            current = next;
        }
        next = head;
        //第二个while 构造 random
        while (next != null) {
            Node copy = next.next;
            copy.random = next.random == null ? null : next.random.next;
            next = copy.next;
        }
        //第三个while循环 分离出 A`->B`->C`
        Node newHead = head.next;
        Node copy = null;
        current = head;
        while (current != null) {
            next = current.next.next;
            copy = current.next;
            current.next = next;
            copy.next = next == null ? null : next.next;
            current = next;
        }
        return newHead;
    }

}
