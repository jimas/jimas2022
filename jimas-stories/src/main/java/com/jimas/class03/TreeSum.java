package com.jimas.class03;

import com.jimas.RandomTreeNode;
import com.jimas.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * 路径和
 * TreeNode{val=7, left=TreeNode{val=2, left=TreeNode{val=3, left=null, right=null}, right=TreeNode{val=1, left=null, right=null}}, right=TreeNode{val=4, left=TreeNode{val=9, left=null, right=null}, right=TreeNode{val=9, left=null, right=null}}}
 * @author liuqj
 */
public class TreeSum {


    public static void main(String[] args) {

        for (int i = 0; i < 30; i++) {
            List<List<Integer>> lists = new LinkedList<>();
            TreeNode root = RandomTreeNode.random(10, 3);
            pathSum(root, 20, lists);
            if (!lists.isEmpty()) {
                System.out.println(lists + "==:" + root);
            }

        }
    }

    private static void pathSum(TreeNode node, int sum, List<List<Integer>> lists) {
        List<Integer> path = new LinkedList<>();
        process(node, 0, sum, path, lists);
    }

    private static void process(TreeNode node, int preSum, int sum, List<Integer> path, List<List<Integer>> lists) {
        path.add(node.val);
        //叶子节点
        if (node.left == null && node.right == null) {
            if ((preSum + node.val) == sum) {
                List<Integer> copy = copyList(path);
                lists.add(copy);
            }
        }
        if (node.left != null) {
            process(node.left, preSum + node.val, sum, path, lists);
        }
        if (node.right != null) {
            process(node.right, preSum + node.val, sum, path, lists);
        }
        //移除当前节点 继续下一次遍历
        path.remove(path.size() - 1);
    }

    private static List<Integer> copyList(List<Integer> path) {
        List<Integer> copy = new LinkedList<>();
        for (Integer n : path) {
            copy.add(n);
        }
        return copy;
    }

}
