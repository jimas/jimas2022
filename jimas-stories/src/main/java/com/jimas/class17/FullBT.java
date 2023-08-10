package com.jimas.class17;

/**
 * 满二叉树
 * 一个二叉树，如果每一个层的结点数都达到最大值，则这个二叉树就是满二叉树。
 * 也就是说，如果一个二叉树的深度为K，且结点总数是(2^k) -1 ，则它就是满二叉树。
 *
 * @author liuqj
 */
public class FullBT {

    public static void main(String[] args) {
//        PrintBinaryTree.Node head = new PrintBinaryTree.Node(1);
//        head.left = new PrintBinaryTree.Node(1);
//        head.right = new PrintBinaryTree.Node(1);
//        System.out.println(isFullBt(head));
//
//        head.left = new PrintBinaryTree.Node(2);
//        head.right = new PrintBinaryTree.Node(2);
//        head.right.left = new PrintBinaryTree.Node(2);
//        System.out.println(isFullBt(head));
//
//        head.left = new PrintBinaryTree.Node(2);
//        head.right = new PrintBinaryTree.Node(2);
//        head.right.left = new PrintBinaryTree.Node(2);
//        head.right.right = new PrintBinaryTree.Node(2);
//        head.left.left = new PrintBinaryTree.Node(2);
//        head.left.right = new PrintBinaryTree.Node(2);
//        System.out.println(isFullBt(head));
//        System.out.println(isFullBt2(head));
        for (int i = 0; i < 1000; i++) {
            PrintBinaryTree.Node head = generateNode(17);
            if (isFullBt(head) != isFullBt2(head)) {
                System.out.println("error!!");
            }
        }


    }

    private static boolean isFullBt(PrintBinaryTree.Node head) {
        if (head == null) {
            return true;
        }

        Info info = process(head);
        return info.nodes == ((1 << info.height) - 1);

    }

    private static boolean isFullBt2(PrintBinaryTree.Node head) {
        if (head == null) {
            return true;
        }
        return process2(head).isFull;
    }

    private static Info process(PrintBinaryTree.Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info(height, nodes);
    }

    /**
     * 一个二叉树的深度为K，且结点总数是(2^k) -1
     */
    private static class Info {
        int height;
        int nodes;

        public Info(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    private static Info2 process2(PrintBinaryTree.Node node) {
        if (node == null) {
            return new Info2(true, 0);
        }

        Info2 leftInfo2 = process2(node.left);
        Info2 rightInfo2 = process2(node.right);
        boolean isFull = leftInfo2.isFull && rightInfo2.isFull && leftInfo2.height == rightInfo2.height;
        int height = Math.max(leftInfo2.height, rightInfo2.height) + 1;
        return new Info2(isFull, height);
    }

    /**
     * 第二种方法
     * 收集子树是否是满二叉树
     * 收集子树的高度
     * 左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
     */
    private static class Info2 {
        boolean isFull;
        int height;

        public Info2(boolean isFull, int height) {
            this.isFull = isFull;
            this.height = height;
        }
    }

    private static PrintBinaryTree.Node generateNode(int height) {
        if (height < 1) {
            return null;
        }
        int val = (int) (Math.random() * 10);
        PrintBinaryTree.Node node = new PrintBinaryTree.Node(val);
        if (Math.random() > 0.5) {
            node.left = generateNode(height - 1);
        }
        if (Math.random() > 0.5) {
            node.right = generateNode(height - 1);
        }
        return node;

    }
}
