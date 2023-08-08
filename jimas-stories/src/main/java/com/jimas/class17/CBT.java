package com.jimas.class17;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 完全二叉树 complete binary Tree
 * ①：当节点左子树为空，右子树不为空 不是
 * ②：当中序遍历，遇到左右子树不全的节点时，且中序遍历的后续节点不是叶子节点  不是
 * 一棵深度为k的有n个结点的二叉树，对树中的结点按从上至下、从左到右的顺序进行编号，如果编号为i（1≤i≤n）的结点与满二叉树中编号为i的结点在二叉树中的位置相同，则这棵二叉树称为完全二叉树
 * @author liuqj
 */
public class CBT {

    public static void main(String[] args) {
        PrintBinaryTree.Node head = generateNodeTree(3);
        System.out.println(isCbt(head));
        PrintBinaryTree.printTree(head);
    }

    private static boolean isCbt(PrintBinaryTree.Node head) {
        if (head == null) {
            return false;
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
