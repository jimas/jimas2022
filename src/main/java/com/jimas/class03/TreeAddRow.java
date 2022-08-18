package com.jimas.class03;

import com.jimas.RandomTreeNode;
import com.jimas.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉树的根 root 和两个整数 val 和 depth ，在给定的深度 depth 处添加一个值为 val 的节点行。
 * 注意，根节点 root 位于深度 1 。
 * <p>
 * 加法规则如下:
 * <p>
 * 给定整数 depth，对于深度为 depth - 1 的每个非空树节点 cur ，创建两个值为 val 的树节点作为 cur 的左子树根和右子树根。
 * cur 原来的左子树应该是新的左子树根的左子树。
 * cur 原来的右子树应该是新的右子树根的右子树。
 * 如果 depth == 1 意味着 depth - 1 根本没有深度，那么创建一个树节点，值 val 作为整个原始树的新根，而原始树就是新根的左子树。
 *
 * @author liuqj
 */
public class TreeAddRow {

    @Test
    public void testAddRow() {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(7);
        root.right = new TreeNode(5);
        root.right.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        // TreeNode root = RandomTreeNode.random(10, 4);
        System.out.println(root);
        int depth = RandomTreeNode.depth(root);
        int v = (int) (Math.random() * depth) + 1;
        TreeNode newNode = addOneRow(root, 3, 2);
        System.out.println(newNode);
    }

    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        System.out.println("==========" + depth + "=========");
        //depth == 1 只有根节点
        if (depth == 1) {
            TreeNode newNode = new TreeNode(val);
            newNode.left = root;
            return newNode;
        }
        if (depth == 2) {
            root.left = new TreeNode(val, root.left, null);
            root.right = new TreeNode(val, null, root.right);
            return root;
        }

        root.left = addOneRow(root.left, val, depth - 1);
        root.right = addOneRow(root.right, val, depth - 1);

        return root;
    }

    private void findNodesByDepth(List<TreeNode> curTreeNodes, TreeNode node, int depth) {
        if (depth == 1) {
            curTreeNodes.add(node);
            return;
        }
        if (node.left != null) {
            int leftDepth = depth - 1;
            findNodesByDepth(curTreeNodes, node.left, leftDepth);
        }
        if (node.right != null) {
            int rightDepth = depth - 1;
            findNodesByDepth(curTreeNodes, node.right, rightDepth);
        }

    }

    private void insertNewNode(int val, TreeNode cur) {
        if (cur != null) {
            TreeNode nextLeft = cur.left;
            TreeNode nextRight = cur.right;
            cur.left = new TreeNode(val);
            cur.right = new TreeNode(val);
            cur.left.left = nextLeft;
            cur.right.right = nextRight;
        }
    }

}
