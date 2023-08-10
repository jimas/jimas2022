package com.jimas.class17;

/**
 * Binary Search Tree
 * (二叉搜索树，二叉排序树）它或者是一棵空树，或者是具有下列性质的二叉树：
 * 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值
 *
 * @author liuqj
 */
public class BST {

    public static void main(String[] args) {
        PrintBinaryTree.Node node = new PrintBinaryTree.Node(10);
        node.left = new PrintBinaryTree.Node(5);
        node.left.left = new PrintBinaryTree.Node(4);
        node.left.right = new PrintBinaryTree.Node(6);

        node.right = new PrintBinaryTree.Node(14);
        node.right.left = new PrintBinaryTree.Node(12);
        node.right.right = new PrintBinaryTree.Node(15);
        System.out.println(isBst(node));
    }

    private static boolean isBst(PrintBinaryTree.Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBalance;
    }


    private static Info process(PrintBinaryTree.Node node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        boolean isBalance = true;
        int max = node.value;
        int min = node.value;
        if (leftInfo != null && !leftInfo.isBalance) {
            isBalance = false;
        }
        if (rightInfo != null && !rightInfo.isBalance) {
            isBalance = false;
        }
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }
        if (leftInfo != null && node.value <= leftInfo.max) {
            isBalance = false;
        }
        if (rightInfo != null && node.value >= rightInfo.min) {
            isBalance = false;
        }
        return new Info(isBalance, max, min);
    }

    /**
     * 1、子树是否为搜索二叉树
     * 2、子树最大节点值
     * 3、子树最小节点值
     */
    private static class Info {
        boolean isBalance;
        int max;
        int min;

        public Info(boolean isBalance, int max, int min) {
            this.isBalance = isBalance;
            this.max = max;
            this.min = min;
        }
    }
}
