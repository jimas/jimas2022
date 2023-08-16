package com.jimas.class17;

import java.util.*;


/**
 * 找到 a、b 两个节点的最小祖先
 * 给到x节点，在以x为头节点的树上 是否能找到 a、b两个节点的最小祖先
 * 1)、经过X节点
 * ①、在左子树找到a节点，在右子树找到b节点
 * ②、在左子树找到b节点，在右子树找到a节点
 * 2)、不经过X节点
 * ①、在左子树同时找到a、b节点
 * ②、在右子树同时找到a、b节点
 *
 * @author liuqj
 */
public class LowestAncestor {

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            int height = (int) (Math.random() * 10);
            PrintBinaryTree.Node head = randomNode(height);
            PrintBinaryTree.Node a = findRandomOne(head);
            PrintBinaryTree.Node b = findRandomOne(head);

            PrintBinaryTree.Node node1 = printLowestAncestor1(head, a, b);
            PrintBinaryTree.Node node2 = printLowestAncestor2(head, a, b);
            if (node1 != node2) {
                System.out.println("error");
            }
        }
        System.out.println("success");
    }

    /**
     * 1、遍历二叉树,
     * 2、封装成 map<node,parentNode> 结构，
     * 3、取a的parentNode以及上级上上级节点的parentNode  放在 set<node>集合里,
     * 4、再取b的parentNode以及上级上上级节点的parentNode 首次出现在 set<Node> 时即为要找到的节点
     *
     * @param x 给到的头节点
     * @param a a
     * @param b b
     * @return res
     */
    private static PrintBinaryTree.Node printLowestAncestor1(PrintBinaryTree.Node x, PrintBinaryTree.Node a, PrintBinaryTree.Node b) {
        if (x == null) {
            return null;
        }
        Map<PrintBinaryTree.Node, PrintBinaryTree.Node> nodeNodeMap = new HashMap<>();
        nodeNodeMap.put(x, null);
        genMap(x, nodeNodeMap);
        Set<PrintBinaryTree.Node> aParentSet = new HashSet<>();
        PrintBinaryTree.Node aParent;
        PrintBinaryTree.Node curNode = a;
        aParentSet.add(a);
        while ((aParent = nodeNodeMap.get(curNode)) != null) {
            aParentSet.add(aParent);
            curNode = aParent;
        }
        curNode = b;
        while (!aParentSet.contains(curNode)) {
            curNode = nodeNodeMap.get(curNode);
        }
        return curNode;
    }

    private static void genMap(PrintBinaryTree.Node x, Map<PrintBinaryTree.Node, PrintBinaryTree.Node> nodeNodeMap) {
        if (x.left != null) {
            nodeNodeMap.put(x.left, x);
            genMap(x.left, nodeNodeMap);
        }
        if (x.right != null) {
            nodeNodeMap.put(x.right, x);
            genMap(x.right, nodeNodeMap);
        }

    }

    private static void fillList(PrintBinaryTree.Node x, List<PrintBinaryTree.Node> nodeList) {
        if (x == null) {
            return;
        }
        nodeList.add(x);
        fillList(x.left, nodeList);
        fillList(x.right, nodeList);
    }

    private static PrintBinaryTree.Node printLowestAncestor2(PrintBinaryTree.Node x, PrintBinaryTree.Node a, PrintBinaryTree.Node b) {
        return process(x, a, b).ans;

    }

    private static Info process(PrintBinaryTree.Node x, PrintBinaryTree.Node a, PrintBinaryTree.Node b) {
        if (x == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);
        boolean findA = x == a || leftInfo.findA || rightInfo.findA;
        boolean findB = x == b || leftInfo.findB || rightInfo.findB;
        PrintBinaryTree.Node ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else if (findA && findB) {
            ans = x;
        }
        return new Info(findA, findB, ans);
    }

    /**
     * 左右两个子树都要获取：
     * 1、是否找到A
     * 2、是否找到B
     * 3、最小祖先
     */
    private static class Info {
        boolean findA;
        boolean findB;
        PrintBinaryTree.Node ans;

        public Info(boolean findA, boolean findB, PrintBinaryTree.Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }


    private static PrintBinaryTree.Node randomNode(int height) {
        if (height < 1) {
            return null;
        }
        int val = (int) (Math.random() * 10);
        PrintBinaryTree.Node head = new PrintBinaryTree.Node(val);
        if (Math.random() > 0.3) {
            head.left = randomNode(height - 1);
        }
        if (Math.random() > 0.5) {
            head.right = randomNode(height - 1);
        }
        return head;
    }

    private static PrintBinaryTree.Node findRandomOne(PrintBinaryTree.Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<PrintBinaryTree.Node> nodeList = new ArrayList<>();
        fillList(head, nodeList);
        int index = (int) (Math.random() * nodeList.size());
        return nodeList.get(index);

    }

}
