package com.jimas.class19;

/**
 * 并查集数组实现
 *
 * @author liuqj
 */
public class UnionFindArray {
    /**
     * parents[i]=k ,代表i的父是k
     */
    int[] parents;
    /**
     * 这里只有代表节点（父是自己）才有意义
     * size[j]=m,表示代表节点j 这组元素大小是m
     */
    int[] size;
    /**
     * 类似栈的作用
     */
    int[] help;
    /**
     * 最终并查集大小
     */
    int sets;

    /**
     * parents 初始化时 自己的父亲是自己
     * size 初始化，每个元素都是代表节点，且大小为1
     *
     * @param N 数组长度
     */
    public UnionFindArray(int N) {
        parents = new int[N];
        size = new int[N];
        help = new int[N];
        sets = N;
        for (int i = 0; i < N; i++) {
            parents[i] = i;
            size[i] = 1;
        }
    }

    public void union(int a, int b) {
        int f1 = findAncestor(a);
        int f2 = findAncestor(b);
        if (f1 == f2) {
            return;
        }
        int big = size[f1] > size[f2] ? a : b;
        int small = big == a ? b : a;
        size[big] = size[f1] + size[f2];
        parents[small] = big;
        sets--;
    }

    public boolean isSameSet(int a, int b) {
        return findAncestor(a) == findAncestor(b);
    }

    public int findAncestor(int curr) {
        int h = 0;
        while (parents[curr] != curr) {
            help[h++] = curr;
            curr = parents[curr];
        }
        for (h--; h > 0; h--) {
            parents[help[h]] = curr;
        }
        return curr;
    }
}
