package com.jimas.class17;

/**
 * 返回 最大搜索二叉子树的头节点
 * 递归套路：
 * 1、
 *
 * @author liuqj
 */
public class MaxSubBSTHead {

    public static void main(String[] args) {
        PrintBinaryTree.Node head = new PrintBinaryTree.Node(10);
        head.left = new PrintBinaryTree.Node(6);
        head.left.left = new PrintBinaryTree.Node(4);
        head.left.right = new PrintBinaryTree.Node(7);
        head.right = new PrintBinaryTree.Node(14);
        head.right.right = new PrintBinaryTree.Node(15);
        head.right.left = new PrintBinaryTree.Node(13);
        PrintBinaryTree.printTree(head);
        System.out.println(printMaxSubBstHead(head).value);

        head.left = new PrintBinaryTree.Node(6);
        head.left.left = new PrintBinaryTree.Node(4);
        head.left.right = new PrintBinaryTree.Node(7);
        head.right = new PrintBinaryTree.Node(14);
        head.right.right = new PrintBinaryTree.Node(15);
        head.right.left = new PrintBinaryTree.Node(16);
        PrintBinaryTree.printTree(head);
        System.out.println(printMaxSubBstHead(head).value);

        head.left = new PrintBinaryTree.Node(6);
        head.left.left = new PrintBinaryTree.Node(14);
        head.left.right = new PrintBinaryTree.Node(7);
        head.right = new PrintBinaryTree.Node(14);
        head.right.right = new PrintBinaryTree.Node(15);
        head.right.left = new PrintBinaryTree.Node(16);
        PrintBinaryTree.printTree(head);
        System.out.println(printMaxSubBstHead(head).value);
    }

    private static PrintBinaryTree.Node printMaxSubBstHead(PrintBinaryTree.Node head) {
        if (head == null) {
            return null;
        }
        return process(head).ansHead;
    }

    private static class Info {
        int size;
        int maxSubBSTSize;
        int max;
        int min;
        PrintBinaryTree.Node ansHead;

        public Info(int size, int maxSubBSTSize, int max, int min, PrintBinaryTree.Node ansHead) {
            this.size = size;
            this.maxSubBSTSize = maxSubBSTSize;
            this.max = max;
            this.min = min;
            this.ansHead = ansHead;
        }
    }

    private static Info process(PrintBinaryTree.Node x) {
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int size = 1;
        int max = x.value;
        int min = x.value;
        int p1 = -1;
        int p2 = -1;
        int p3 = -1;
        PrintBinaryTree.Node ansHead = null;
        if (leftInfo != null) {
            size += leftInfo.size;
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            p1 = leftInfo.maxSubBSTSize;
            ansHead = leftInfo.ansHead;
        }
        if (rightInfo != null) {
            size += rightInfo.size;
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            p2 = rightInfo.maxSubBSTSize;
            if (p2 > p1) {
                ansHead = rightInfo.ansHead;
            }
        }
        boolean leftBST = leftInfo == null || leftInfo.size == leftInfo.maxSubBSTSize;
        boolean rightBST = rightInfo == null || rightInfo.size == rightInfo.maxSubBSTSize;
        if (leftBST && rightBST) {
            boolean leftMaxLessX = leftInfo == null || leftInfo.max < x.value;
            boolean rightMinMoreX = rightInfo == null || rightInfo.min > x.value;
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.size;
                int rightSize = rightInfo == null ? 0 : rightInfo.size;
                p3 = leftSize + rightSize + 1;
                ansHead = x;
            }
        }
        return new Info(size, Math.max(p1, Math.max(p2, p3)), max, min, ansHead);
    }
}
