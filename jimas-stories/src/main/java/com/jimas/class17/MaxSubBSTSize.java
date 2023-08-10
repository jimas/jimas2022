package com.jimas.class17;

/**
 * 返回 最大搜索二叉子树的节点数
 * 在线测试链接 : <a href="https://leetcode.com/problems/largest-bst-subtree">largest-bst-subtree</a>
 *
 * @author liuqj
 */
public class MaxSubBSTSize {

    public static void main(String[] args) {
        PrintBinaryTree.Node head = new PrintBinaryTree.Node(10);
        head.left = new PrintBinaryTree.Node(6);
        head.left.left = new PrintBinaryTree.Node(4);
        head.left.right = new PrintBinaryTree.Node(7);
        head.right = new PrintBinaryTree.Node(14);
        head.right.right = new PrintBinaryTree.Node(15);
        head.right.left = new PrintBinaryTree.Node(13);
        System.out.println(printMaxSubSize(head));

        head.left = new PrintBinaryTree.Node(6);
        head.left.left = new PrintBinaryTree.Node(4);
        head.left.right = new PrintBinaryTree.Node(7);
        head.right = new PrintBinaryTree.Node(14);
        head.right.right = new PrintBinaryTree.Node(15);
        head.right.left = new PrintBinaryTree.Node(16);
        System.out.println(printMaxSubSize(head));

        head.left = new PrintBinaryTree.Node(6);
        head.left.left = new PrintBinaryTree.Node(14);
        head.left.right = new PrintBinaryTree.Node(7);
        head.right = new PrintBinaryTree.Node(14);
        head.right.right = new PrintBinaryTree.Node(15);
        head.right.left = new PrintBinaryTree.Node(16);
        System.out.println(printMaxSubSize(head));
    }

    private static int printMaxSubSize(PrintBinaryTree.Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxSubBstSize;
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
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            size += leftInfo.size;
            p1 = leftInfo.maxSubBstSize;
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            size += rightInfo.size;
            p2 = rightInfo.maxSubBstSize;
        }
        boolean leftBst = leftInfo == null || (leftInfo.maxSubBstSize == leftInfo.size);
        boolean rightBst = rightInfo == null || (rightInfo.maxSubBstSize == rightInfo.size);
        if (leftBst && rightBst) {
            boolean leftMaxLessX = leftInfo == null || (leftInfo.max < x.value);
            boolean rightMinMoreX = rightInfo == null || (rightInfo.min > x.value);
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.size;
                int rightSize = rightInfo == null ? 0 : rightInfo.size;
                p3 = leftSize + rightSize + 1;
            }
        }
        return new Info(size, Math.max(Math.max(p1, p2), p3), max, min);
    }

    /**
     * size == maxSubBstSize 就表明 是二叉搜索树
     */
    private static class Info {
        /**
         * 子树节点数
         */
        int size;
        /**
         * 最大子树二叉搜索树节点
         */
        int maxSubBstSize;
        /**
         * 子树最大节点值
         */
        int max;
        /**
         * 子树最小节点值
         */
        int min;

        public Info(int size, int maxSubBstSize, int max, int min) {
            this.size = size;
            this.maxSubBstSize = maxSubBstSize;
            this.max = max;
            this.min = min;
        }
    }
}
