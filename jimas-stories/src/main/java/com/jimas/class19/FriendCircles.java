package com.jimas.class19;

/**
 * https://leetcode.com/problems/friend-circles/
 * 朋友圈
 * M*N 矩阵
 * int[i][j] =1 则 int[j][i]=2 表示i与j互相认识。
 * 而 int[i][i] =1 自己肯定认识自己
 *
 * @author liuqj
 */
public class FriendCircles {

    public static void main(String[] args) {

        System.out.println(findCircleNum(new int[][]{{1, 2, 3}, {1, 2, 3}}));
    }

    public static int findCircleNum(int[][] M) {
        int N = M.length;
        UnionFindArray unionFindArray = new UnionFindArray(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1) {
                    unionFindArray.union(i, j);
                }
            }
        }
        return unionFindArray.sets;
    }
}
