package com.jimas.class03;

import com.jimas.RandomTreeNode;
import com.jimas.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 树的最大深度（从根节点到最近叶子节点的最长路径上的节点数量）
 * 树的最小深度 (从根节点到最近叶子节点的最短路径上的节点数量)
 *
 * @author liuqj
 */
public class TreeDepth {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            TreeNode root = RandomTreeNode.random(10, 3);
            System.out.println(root);
            List<List<Integer>> paths = new ArrayList<>();
            getPaths(root, paths);
            paths.sort((Comparator.comparingInt(List::size)));
            System.out.println(paths);
            System.out.println(paths.get(0));
            System.out.println(paths.get(paths.size() - 1));

        }
    }

    private static void getPaths(TreeNode root, List<List<Integer>> paths) {
        List<Integer> path = new ArrayList<>();
        process(root, path, paths);

    }

    private static void process(TreeNode node, List<Integer> path, List<List<Integer>> paths) {
        path.add(node.val);
        //叶子节点（到叶子节点标识一个完整的路径）
        if (node.left == null && node.right == null) {
            paths.add(copyList(path));
        }
        if (node.left != null) {
            process(node.left, path, paths);
        }
        if (node.right != null) {
            process(node.right, path, paths);
        }
        //移除最后一个节点，进入后面的迭代
        path.remove(path.size() - 1);

    }

    private static List<Integer> copyList(List<Integer> path) {
        if (path == null || path.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>(path.size());
        for (Integer num : path) {
            list.add(num);
        }
        return list;
    }


}
