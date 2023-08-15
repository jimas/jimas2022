package com.jimas.class17;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 完全二叉树 complete binary Tree
 * ①：当节点左子树为空，右子树不为空 不是
 * ②：当中序遍历，遇到左右子树不全的节点时，且中序遍历的后续节点不是叶子节点  不是
 * 一棵深度为k的有n个结点的二叉树，对树中的结点按从上至下、从左到右的顺序进行编号，如果编号为i（1≤i≤n）的结点与满二叉树中编号为i的结点在二叉树中的位置相同，则这棵二叉树称为完全二叉树
 *
 * @author liuqj
 */
public class CBT {

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            PrintBinaryTree.Node head = generateNodeTree((int) (Math.random() * 10));
            if (isCbt(head) != isCbt2(head)) {
                System.out.println("error");
                levelPrint(head);
            }
        }
        System.out.println("success");

    }

    private static boolean isCbt(PrintBinaryTree.Node head) {
        if (head == null) {
            return true;
        }
        boolean leaf = false;
        Queue<PrintBinaryTree.Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            PrintBinaryTree.Node curNode = queue.poll();
            if (curNode.left == null && curNode.right != null || leaf && curNode.left != null) {
                return false;
            }
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
            if (curNode.left == null || curNode.right == null) {
                leaf = true;
            }
        }
        return true;
    }

    private static boolean isCbt2(PrintBinaryTree.Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isCBT;
    }

    private static Info process(PrintBinaryTree.Node x) {
        if (x == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCBT = false;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isCBT = true;
        } else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        } else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        }
        return new Info(isCBT, isFull, height);
    }

    /**
     * 递归套路：
     * 如果x节点是完全二叉树，需要 其左子树以及右子树满足什么条件：
     * 1、左子树是满树、且右子树是满树，并且 左高=右高
     * 2、左子树是完全二叉树，且右子树是满树，并且左高=右高+1
     * 3、左子树是满树，且右子树是满树，并且左高=右高+1
     * 4、左子树是满树，且右子树是完全二叉树，并且左高=右高
     */
    private static class Info {
        boolean isCBT;
        boolean isFull;
        int height;

        public Info(boolean isCBT, boolean isFull, int height) {
            this.isCBT = isCBT;
            this.isFull = isFull;
            this.height = height;
        }
    }

    private static void levelPrint(PrintBinaryTree.Node head) {
        if (head == null) {
            return;
        }
        Queue<PrintBinaryTree.Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            PrintBinaryTree.Node curNode = queue.poll();
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
            System.out.print(curNode.value + "\t");
        }
        System.out.println();
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
