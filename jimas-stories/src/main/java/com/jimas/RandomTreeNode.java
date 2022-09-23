package com.jimas;

/**
 * @author liuqj
 */
public class RandomTreeNode {
    /**
     * 随机生成二叉树
     *
     * @param maxVal
     * @param maxDepth
     * @return
     */
    public static TreeNode random(int maxVal, int maxDepth) {
        int val = (int) (Math.random() * maxVal);
        int depth = (int) (Math.random() * maxDepth) + 1;
        TreeNode root = new TreeNode(val);
        depth--;
        if (depth > 0) {
            root.left = random(maxVal, depth);
            root.right = random(maxVal, depth);
        }
        return root;
    }

    /**
     * 树高
     *
     * @param node
     * @return
     */
    public static int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = depth(node.left) + 1;
        int rightDepth = depth(node.right) + 1;

        return Math.max(leftDepth, rightDepth);
    }


}