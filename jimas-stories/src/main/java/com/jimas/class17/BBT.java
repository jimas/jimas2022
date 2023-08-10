package com.jimas.class17;

/**
 * Balanced Binary Tree
 * 平衡二叉树：
 *  平衡二叉树也叫 AVL 树。平衡二叉树是具有以下特点的二叉查找树:
 *  它是一棵空树或它的左右两个子树的高度差的绝对值不超过 1,并且左右两个子树都是一棵平衡二叉树
 * @author liuqj
 */
public class BBT {
    public static void main(String[] args) {
        PrintBinaryTree.Node head = generateNodeTree(4);
        System.out.println(process(head).isBalance);
        PrintBinaryTree.printTree(head);
    }

    private static class Info {
        int height;
        boolean isBalance;

        public Info(int height, boolean isBalance) {
            this.height = height;
            this.isBalance = isBalance;
        }
    }

    private static Info process(PrintBinaryTree.Node head) {
        if (head == null) {
            return new Info(0, true);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isBalance = false;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        if (leftInfo.isBalance && rightInfo.isBalance
                && Math.abs(leftInfo.height - rightInfo.height) <= 1) {
            isBalance = true;
        }
        return new Info(height, isBalance);

    }

    private static PrintBinaryTree.Node generateNodeTree(Integer height) {
        if (height < 1) {
            return null;
        }
        int val = (int) (Math.random() * 10);
        PrintBinaryTree.Node node = new PrintBinaryTree.Node(val);
        if (Math.random() > 0.3) {
            node.left = generateNodeTree(height - 1);
        }
        if (Math.random() > 0.3) {
            node.right = generateNodeTree(height - 1);
        }
        return node;

    }
}
