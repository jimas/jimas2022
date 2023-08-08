package com.jimas.class17;


import java.util.Stack;

/**
 * @author liuqj
 */
public class Tree01 {
    private static class Node {
        String value;
        Node left;
        Node right;

        public Node(String value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node head = new Node("a");
        head.left = new Node("b");
        head.right = new Node("c");
        head.left.left = new Node("d");
        head.left.right = new Node("e");
        head.right.left = new Node("f");
        head.right.right = new Node("g");
        head.left.left.left = new Node("h");
        head.left.left.right = new Node("i");
        head.left.right.left = new Node("j");
        head.left.right.right = new Node("k");

        System.out.println("=====pre=======");
        pre(head);
        System.out.println();
        preByStack(head);
        System.out.println("======in======");
        in(head);
        System.out.println();
        inByStack(head);
        System.out.println("======last======");
        last(head);
        System.out.println();
        lastByStack(head);
    }

    public static void preByStack(Node cur) {
        if (cur == null) {
            return;
        }
        //采用栈
        Stack<Node> stack = new Stack<>();
        stack.push(cur);
        while (!stack.empty()) {
            cur = stack.pop();
            if (cur != null) {
                System.out.print(cur.value);
                stack.push(cur.right);
                stack.push(cur.left);
            }
        }
        System.out.println();
    }

    public static void inByStack(Node cur) {
        if (cur == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.empty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                System.out.print(cur.value);
                cur = cur.right;
            }
        }
        System.out.println();
    }

    //先序 头右左  转换成  头左右进栈，出栈元素进新栈
    public static void lastByStack(Node cur) {
        if (cur == null) {
            return;
        }
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(cur);
        while (!stack1.empty()) {
            cur = stack1.pop();
            if (cur != null) {
                //头 右 左
                stack2.push(cur);
                stack1.push(cur.left);
                stack1.push(cur.right);
            }
        }
        // 左 右 头
        while (!stack2.empty()) {
            System.out.print(stack2.pop().value);
        }

        System.out.println();
    }

    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value);
        pre(head.left);
        pre(head.right);
    }

    public static void in(Node head) {
        if (head == null) {
            return;
        }
        in(head.left);
        System.out.print(head.value);
        in(head.right);
    }

    public static void last(Node head) {
        if (head == null) {
            return;
        }
        last(head.left);
        last(head.right);
        System.out.print(head.value);
    }
}
