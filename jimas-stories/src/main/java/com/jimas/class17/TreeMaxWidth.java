package com.jimas.class17;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 求二叉树的最大宽度
 *
 * @author liuqj
 */
public class TreeMaxWidth {
    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }

    }

    public static void main(String[] args) {

        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println(maxWidth(head));
    }

    public static int maxWidth(Node head) {
        int max = 0;
        if (head == null) {
            return max;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        //当前层结束节点
        Node curEnd = head;
        //下一层结束节点
        Node nextEnd = null;
        //当前层宽度
        int curWidth = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            curWidth++;
            if (cur == curEnd) {
                curEnd = nextEnd;
                max = Math.max(curWidth, max);
                curWidth = 0;
            }
        }
        return max;
    }
}
