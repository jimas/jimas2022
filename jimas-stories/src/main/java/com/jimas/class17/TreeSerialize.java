package com.jimas.class17;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 * 以下代码全部实现了。
 * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
 * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
 * 比如如下两棵树
 * __2
 * /
 * 1
 * 和
 * 1__
 * \
 * 2
 * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
 *
 * @author liuqj
 */
public class TreeSerialize {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 水平遍历序列化
     *
     * @param head
     * @return
     */
    public static Queue<Integer> levelSerialize(TreeNode head) {
        Queue<Integer> ans = new LinkedList<>();
        doLevelSerial(head, ans);
        return ans;
    }

    /**
     * 后续遍历序列化
     *
     * @param head head
     * @return Queue
     */
    public static Queue<Integer> posSerialize(TreeNode head) {
        Queue<Integer> ans = new LinkedList<>();
        doPosSerial(head, ans);
        return ans;
    }

    public static TreeNode posDeSerialize(Queue<Integer> ans) {
        if (ans == null || ans.isEmpty()) {
            return null;
        }
        Integer val = ans.poll();
        TreeNode head = generateTreeNode(val);
        posBuild(head, ans);
        return head;
    }

    private static void posBuild(TreeNode head, Queue<Integer> ans) {
        Integer val = ans.poll();
        if (val == null) {

        }
    }

    private static void doPosSerial(TreeNode head, Queue<Integer> ans) {
        //左右头
        if (head == null) {
            ans.add(null);
        } else {
            doPosSerial(head.left, ans);
            doPosSerial(head.right, ans);
            ans.add(head.val);
        }
    }


    private static void doLevelSerial(TreeNode head, Queue<Integer> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            Queue<TreeNode> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()) {
                TreeNode cur = nodeQueue.poll();
                ans.add(cur.val);
                if (cur.left != null) {
                    nodeQueue.add(cur.left);
                }
                if (cur.right != null) {
                    nodeQueue.add(cur.right);
                }
            }
        }
    }

    private static void doLevelSerial1(TreeNode head, Queue<Integer> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            Queue<TreeNode> nodeQueue = new LinkedList<>();
            ans.add(head.val);
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()) {
                TreeNode cur = nodeQueue.poll();
                if (cur.left != null) {
                    nodeQueue.add(cur.left);
                    ans.add(cur.left.val);
                } else {
                    ans.add(null);
                }
                if (cur.right != null) {
                    nodeQueue.add(cur.right);
                    ans.add(cur.right.val);
                } else {
                    ans.add(null);
                }
            }
        }
    }

    /**
     * 水平遍历反序列化
     *
     * @param ans
     * @return
     */
    public static TreeNode levelDeserialize(Queue<Integer> ans) {
        if (ans == null || ans.isEmpty()) {
            return null;
        }
        return levelBuild(ans);
    }

    private static TreeNode levelBuild(Queue<Integer> ans) {
        Integer val = ans.poll();
        TreeNode head = generateTreeNode(val);
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        if (head != null) {
            nodeQueue.add(head);
        }
        while (!nodeQueue.isEmpty()) {
            TreeNode treeNode = nodeQueue.poll();
            treeNode.left = generateTreeNode(ans.poll());
            treeNode.right = generateTreeNode(ans.poll());
            if (treeNode.left != null) {
                nodeQueue.add(treeNode.left);
            }
            if (treeNode.right != null) {
                nodeQueue.add(treeNode.right);
            }
        }

        return head;
    }

    private static TreeNode generateTreeNode(Integer val) {
        if (val == null) {
            return null;
        }
        return new TreeNode(val);
    }

    /**
     * 先序遍历序列化
     *
     * @param head 头节点
     * @param ans  序列化结果list
     */
    public static void preSerialize(TreeNode head, Queue<Integer> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(head.val);
            preSerialize(head.left, ans);
            preSerialize(head.right, ans);
        }
    }

    public static TreeNode preDeserialize(Queue<Integer> ans) {
        if (ans == null || ans.isEmpty()) {
            return null;
        }

        return preBuild(ans);
    }

    private static TreeNode preBuild(Queue<Integer> ans) {
        Integer val = ans.poll();
        if (val == null) {
            return null;
        }
        TreeNode head = new TreeNode(val);
        head.left = preBuild(ans);
        head.right = preBuild(ans);
        return head;
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(1);
        head.left = new TreeNode(2);
        head.right = new TreeNode(3);
        head.left.left = new TreeNode(4);
        head.left.right = new TreeNode(5);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(7);
        System.out.println("============= pre serial============");
        Queue<Integer> ans = new LinkedList<>();
        preSerialize(head, ans);
        System.out.println(ans);
        TreeNode treeNode = preDeserialize(ans);
        Queue<Integer> ans1 = new LinkedList<>();
        preSerialize(treeNode, ans1);
        System.out.println(ans1);
        System.out.println("============= level serial============");
        Queue<Integer> levelSerialize = levelSerialize(head);
        System.out.println(levelSerialize);
        System.out.println(levelSerialize(levelDeserialize(levelSerialize)));
        System.out.println("============= pos serial============");

        System.out.println(posSerialize(head));
        ;
    }
}
