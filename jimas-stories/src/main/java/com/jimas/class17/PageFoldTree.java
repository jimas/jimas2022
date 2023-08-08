package com.jimas.class17;

/**
 * 折纸；对折一次、再对折二次、再对折……，这时记录折痕的凹凸状态，并打印。
 * 第一次折痕是凹的，
 * 第二次折痕是凹凹凸
 * 第三次折痕是凹凹凸凹凹凸凸
 *
 * 有点像 一棵树的中序遍历：
 * 凹凸规律如下：
 * 1、根节点是凹
 * 2、左子树是凹
 * 3、右子树是凸
 * @author liuqj
 */
public class PageFoldTree {
    private static class Node {
        String val;
        Node left;
        Node right;

        public Node(String val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        printAllFold(3);
    }

    private static void printAllFold(int n) {
        process(1, n, true);
    }

    private static void process(int i, int n, boolean down) {
        if (i > n) {
            return;
        }
        process(i + 1, n, true);
        System.out.print(down ? "凹" : "凸");
        process(i + 1, n, false);
    }
}
