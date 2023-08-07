package com.jimas.class17;

/**
 * 左根右
 * 中序遍历获取某个节点的直接后继：
 * ①最粗糙的方法：先从这个节点的parent指针 一直往上找，找到head节点，在进行中序遍历，
 * 直到打印出当前节点时，
 * 那么这个节点后面的节点便是直接后继节点
 * ②改进方案：
 * 先看是否有右子树，如果存在，则取右子树最左侧节点，
 * 如果不存在右子树，则往上找，如果父节点右孩子不是自身 则返回
 *
 * @author liuqj
 */
public class SucceedNodeTree {

    static class Node {
        int val;
        Node left;
        Node right;
        Node parent;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    ", parent=" + parent +
                    '}';
        }
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;
        printSucceed(head);
        System.out.println();
        Node test = head.left.left;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.left.left.right;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.left;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.left.right;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.left.right.right;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.right.left.left;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.right.left;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.right;
        System.out.println(test.val + " next: " + getSuccessorNode(test).val);
        test = head.right.right; // 10's next is null
        System.out.println(test.val + " next: " + getSuccessorNode(test));

    }

    private static void printSucceed(Node head) {

        if (head == null) {
            return;
        }
        printSucceed(head.left);
        System.out.print(head.val + "\t");
        printSucceed(head.right);

    }

    private static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        //存在右子树
        if (node.right != null) {
            return getLeftMost(node.right);
        } else {
            //无右子树
            Node parent = node.parent;
            while (parent != null && parent.right == node) {//parent 不为null,且当前节点是父节点的右孩子
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }


    }

    /**
     * 获取该节点的最左侧子树
     *
     * @param node 当前节点
     * @return node
     */
    private static Node getLeftMost(Node node) {
        if (node == null) {
            return node;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
