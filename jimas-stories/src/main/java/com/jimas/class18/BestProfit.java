package com.jimas.class18;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入正整数数组costs、正整数数组profits、正整数k，正整数M
 * costs[i]:表示i项目的花费
 * profits[i]:表示i项目带来的收益
 * k：表示串行做k个项目
 * M：表示启动资金
 *
 * @author liuqj
 */
public class BestProfit {

    public static void main(String[] args) {
        //3+1+2+4
        int[] costs = new int[]{2, 5, 4, 6, 8};
        int[] profits = new int[]{1, 3, 2, 4, 3};
        int k = 2;
        int m = 3;

        int sum = methodOne(costs, profits, k, m);

        System.out.println(sum);

    }

    public static int methodOne(int[] costs, int[] profits, int k, int m) {
        //搞一个小根堆、一个大根堆
        PriorityQueue<CostProfit> lessCostPriorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        PriorityQueue<CostProfit> mostProfitQueue = new PriorityQueue<>((o1, o2) -> o2.cost - o1.cost);
        for (int i = 0; i < costs.length; i++) {
            lessCostPriorityQueue.add(new CostProfit(costs[i], profits[i]));
        }
        int sumM = m;
        for (int i = 0; i < k; i++) {
            for (int i1 = 0; i1 < costs.length; i1++) {
                CostProfit costProfit = lessCostPriorityQueue.peek();
                if (costProfit == null) {
                    return sumM;
                }
                if (costProfit.cost <= sumM) {
                    mostProfitQueue.add(lessCostPriorityQueue.poll());
                }
            }
            if (!mostProfitQueue.isEmpty()) {
                CostProfit costProfit = mostProfitQueue.poll();
                sumM += costProfit.profit;
            }
        }
        return sumM;

    }

    private static class CostProfit {
        int cost;
        int profit;

        public CostProfit(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }
}
