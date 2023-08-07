package com.jimas.class17;

/**
 * 左根右
 * 中序遍历获取某个节点的直接后继：
 * ①最粗糙的方法：先从这个节点的parent指针 一直往上找，找到head节点，在进行中序遍历，
 * 直到打印出当前节点时，
 * 那么这个节点后面的节点便是直接后继节点
 * ②改进方案：
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
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        head.left.left.left = new Node(8);
        head.right.right.left = new Node(9);
        Node succeedNode = printSucceedNode(head.right.left);
        System.out.println(succeedNode);
    }

    private static Node printSucceedNode(Node node) {
        if (node == null) {
            return null;
        }
        //

        return null;
    }
}
