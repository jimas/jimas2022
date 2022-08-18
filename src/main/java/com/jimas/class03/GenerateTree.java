package com.jimas.class03;

import com.jimas.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/unique-binary-search-trees-ii/
 *
 * @author liuqj
 */
public class GenerateTree {

    public static void main(String[] args) {

    }

    public int lengthOfLongestSubstring(String s) {
        for (char c : s.toCharArray()) {

        }
        return 0;
    }


    /**
     * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。
     * 可以按 任意顺序 返回答案。
     * 搜索树（ 左边 < 根 < 右边 ） 即中序遍历是有序树
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {

        List<TreeNode> list = new ArrayList<>();

        generateTree(list, n);

        return list;
    }

    private void generateTree(List<TreeNode> list, int n) {
        if (n == 1) {
            list.add(new TreeNode(n));
        } else if (n == 2) {

        }

        for (int i = 1; i <= n; i++) {

        }
    }
}
