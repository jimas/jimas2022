package com.jimas.class03;

import com.jimas.RandomTreeNode;
import com.jimas.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liuqj
 */
public class TreeTraverse {

    /**
     * 先序遍历
     * 根结点 ---> 左子树 ---> 右子树
     *
     * @param root
     * @return
     */
    public static List<Integer> preOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        preProcess(root, list);
        return list;
    }

    /**
     * 中序遍历
     * 左子树---> 根结点 ---> 右子树
     *
     * @param root
     * @return
     */
    public static List<Integer> inOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        inProcess(root, list);
        return list;
    }

    /**
     * 后序遍历
     * 左子树 ---> 右子树 ---> 根结点
     *
     * @param root
     * @return
     */
    public static List<Integer> postOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        postProcess(root, list);
        return list;
    }


    /**
     * 层次遍历：只需按层次遍历即可
     *
     * @return
     */
    public static List<Integer> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            //循环size 必须固定（因为 for 会增加size）
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return list;
    }

    private static void preProcess(TreeNode root, List<Integer> list) {
        list.add(root.val);
        if (root.left != null) {
            preProcess(root.left, list);
        }
        if (root.right != null) {
            preProcess(root.right, list);
        }
    }

    private static void inProcess(TreeNode root, List<Integer> list) {
        if (root.left != null) {
            inProcess(root.left, list);
        }
        list.add(root.val);
        if (root.right != null) {
            inProcess(root.right, list);
        }
    }


    private static void postProcess(TreeNode root, List<Integer> list) {
        if (root.left != null) {
            postProcess(root.left, list);
        }
        if (root.right != null) {
            postProcess(root.right, list);
        }
        list.add(root.val);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            TreeNode root = RandomTreeNode.random(10, 10);
            System.out.println("========= tree depth:" + RandomTreeNode.depth(root) + " =========");
            System.out.println(root);
            System.out.println(preOrder(root));
            System.out.println(inOrder(root));
            System.out.println(postOrder(root));
            System.out.println(levelOrder(root));
        }
    }
}
