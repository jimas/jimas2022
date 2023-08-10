package com.jimas.class17;

/**
 * 给到二叉树的头节点、任何两个节点都存在距离
 * 返回 整颗二叉树的最大距离
 *
 * @author liuqj
 */
public class MaxDistanceTree {
    /**
     * （1）、不经过头节点 左子树最大距离
     * （2）、不经过头节点 右子树最大距离
     * （3）、经过头节点 整棵树的最大距离  == 左子树高+右子树高+1
     * 以上三者 取最大值即可
     */
    private static class Info {
        int height;
        int maxDistance;

        public Info(int height, int maxDistance) {
            this.height = height;
            this.maxDistance = maxDistance;
        }
    }

    public static void main(String[] args) {
        PrintBinaryTree.Node head = new PrintBinaryTree.Node(1);
        head.left = new PrintBinaryTree.Node(2);
        head.left.left = new PrintBinaryTree.Node(3);
        head.left.left.left = new PrintBinaryTree.Node(4);
        System.out.println(printMaxDistance(head));

        head.left = new PrintBinaryTree.Node(2);
        head.left.left = new PrintBinaryTree.Node(3);
        head.left.left.left = new PrintBinaryTree.Node(4);
        head.right = new PrintBinaryTree.Node(5);
        System.out.println(printMaxDistance(head));

        head.left = new PrintBinaryTree.Node(2);
        head.left.left = new PrintBinaryTree.Node(3);
        head.left.left.left = new PrintBinaryTree.Node(4);
        head.right = new PrintBinaryTree.Node(5);

        head.left.right = new PrintBinaryTree.Node(4);
        head.left.right.right = new PrintBinaryTree.Node(4);
        head.left.right.right.right = new PrintBinaryTree.Node(4);
        System.out.println(printMaxDistance(head));

    }

    private static int printMaxDistance(PrintBinaryTree.Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxDistance;
    }

    private static Info process(PrintBinaryTree.Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = 1 + Math.max(leftInfo.height, rightInfo.height);
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
        int maxDistance = Math.max(p3, Math.max(p1, p2));
        return new Info(height, maxDistance);
    }
}
