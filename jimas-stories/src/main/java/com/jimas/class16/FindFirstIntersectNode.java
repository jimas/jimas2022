package com.jimas.class16;

/**
 * 两个链表相交：
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2，
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点，
 * 如果不相交，返回 null
 *
 * @author liuqj
 */
public class FindFirstIntersectNode {
    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public Node() {
        }
    }

    /**
     * <p>1、两个单链表都无环</p>
     * <p>2、两个单链表都有环</p>
     * <p>3、两个单链表存在一个有环，另一个无环，这种不会相交（如果相交，无环的就一定存在环）</p>
     *
     * @param head1 单链表一头节点
     * @param head2 单链表二头节点
     * @return 返回相交节点
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        } else if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        } else {
            return null;
        }
    }

    /**
     * 获取第一个入环节点
     * 快慢指针，快 fast，慢 slow
     * 假设未成环的节点长度为a，环节点的长度为b，这样当fast追上slow时，slow 走了s,快走了2s
     * 相遇 只会在环内相遇：s+nb（n>=1）,这样2s=s+nb，s=nb
     * 又因为a+nb还是入环的第一个节点，也就是a+nb=a+s，即从相遇节点再走a就到达了入环的第一个节点。
     * 便slow 从head处与fast从相遇节点处各自同时走一步，再次相遇时一定位于入环的第一个节点。
     *
     * @param head
     * @return
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        //获取相遇节点
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        //
        //
        //慢指针回到起点head处，快指针与慢指针同时走一步，相遇时即为入环第一个节点
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        //链表走到低记录长度
        while (cur1.next != null) {
            cur1 = cur1.next;
            n++;
        }
        //链表走到低记录长度
        while (cur2.next != null) {
            cur2 = cur2.next;
            n--;
        }
        //cur1 指向长链表，cur2 指向短链表
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        //cur1 走n步之后，再与cur2一起走，节点相同时便为相遇节点
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur2 != cur1) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 1、两个有环单链表 不相交：即 loop1
     *
     * @param head1 单链表一头节点
     * @param loop1 单链表一第一个入环节点
     * @param head2 单链表二头节点
     * @param loop2 单链表二第一个入环节点
     * @return
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        if (loop1 != loop2) {
            Node cur1 = loop1.next;
            //cur1 绕一圈后还未找到loop2 说明两个环不相交
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return cur1;
                }
                cur1 = cur1.next;
            }
            return null;
        } else {
            //以loop1、loop2 为end 节点
            Node cur1 = head1;
            Node cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                cur1 = cur1.next;
                n++;
            }
            while (cur2 != loop2) {
                cur2 = cur2.next;
                n--;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                cur1 = cur1.next;
                n--;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }
    }

    public static void main(String[] args) {

        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
