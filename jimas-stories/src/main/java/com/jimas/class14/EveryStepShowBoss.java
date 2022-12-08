package com.jimas.class14;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 给定一个整型数组 int[] arr 和一个布尔类型数组 boolean[] op ，两个数组长度一样长
 * 比如：
 * arr=[2,3,3,4,4,5,3]
 * op= [T,T,T,F,T,T,F]
 * 依次表示：2、3、3用户购买了一件商品，4退货一件，接着购买一件，5、3购买一件
 * 得奖规则：
 * 分为候选区、得奖区（得奖区限制大小k 比如2）
 * 进入得奖区的条件：
 * 1、得奖区还有空间，购买者直接进入
 * 2、候选区购买数量最多的用户m大于得奖区中购买数量最小的用户n，则m替换得奖区n，即m进入得奖区，n进入候选区
 * 2.1、如果候选区购买最多的用户有多个，取进入最早的用户替换n
 * 2.2、如果得奖区购买最少的有多个，取最早进入得奖区的用户为n
 * 离开区域条件：
 * 如果用户购买数为 0，不管哪个区域都得移除
 * 边界条件：
 * 如果用户购买数为负数，则忽略掉本次行为
 *
 * @author liuqj
 */
public class EveryStepShowBoss {


    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 2, 3, 4, 5, 4, 5, 5, 1, 2, 4, 2, 3, 3};
        boolean[] op = new boolean[]{true, false, true, true, true, true, true, true, true, true, false, false, true, true, false, true};
        int k = 2;
        final List<List<Integer>> lists = violenceExecute(arr, op, k);
        final List<List<Integer>> heapLists = heapExecute(arr, op, k);
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(lists.get(i) + "===" + heapLists.get(i));
        }
    }

    /**
     * 加强堆实现
     *
     * @param arr
     * @param op
     * @param k
     * @return
     */
    private static List<List<Integer>> heapExecute(int[] arr, boolean[] op, int k) {
        Map<Integer, Customer> map = new HashMap<>(arr.length);
        HeapGreater<Customer> heapCandis = new HeapGreater<>(new CustomerCandisComparator());
        HeapGreater<Customer> heapPrize = new HeapGreater<>(new CustomerPriceComparator());
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrReject = op[i];
            map.putIfAbsent(id, new Customer(id, 0, 0));
            final Customer curCustomer = map.get(id);
            if (buyOrReject) {
                curCustomer.buySize++;
            } else {
                curCustomer.buySize--;
            }
            if (curCustomer.buySize <= 0) {
                map.remove(curCustomer.id);
            }
            //首次出现
            if (!heapCandis.contains(curCustomer) && !heapPrize.contains(curCustomer)) {
                curCustomer.enterTime = i;
                if (curCustomer.buySize > 0) {
                    if (heapPrize.getHeapSize() < k) {
                        heapPrize.push(curCustomer);
                    } else {
                        heapCandis.push(curCustomer);
                    }
                }
            } else if (heapPrize.contains(curCustomer)) {
                if (curCustomer.buySize <= 0) {
                    heapPrize.remove(curCustomer);
                } else {
                    heapPrize.resign(curCustomer);
                }
            } else {
                if (curCustomer.buySize <= 0) {
                    heapCandis.remove(curCustomer);
                } else {
                    heapCandis.resign(curCustomer);
                }
            }
            moveHeap(heapCandis, heapPrize, k, i);
            ans.add(getCurPrizes(heapPrize.getAllElements()));
        }
        return ans;
    }

    private static void moveHeap(HeapGreater<Customer> heapCandis, HeapGreater<Customer> heapPrize, int k, int enterTime) {
        if (heapCandis.isEmpty()) {
            return;
        }
        if (heapPrize.getHeapSize() < k) {
            final Customer bestCandis = heapCandis.pop();
            bestCandis.enterTime = enterTime;
            heapPrize.push(bestCandis);
        } else {
            if (heapCandis.peek().buySize > heapPrize.peek().buySize) {
                final Customer bestCandis = heapCandis.pop();
                final Customer lessPrize = heapPrize.pop();
                bestCandis.enterTime = enterTime;
                lessPrize.enterTime = enterTime;
                heapPrize.push(bestCandis);
            }
        }

    }


    /**
     * 暴力执行
     *
     * @param arr
     * @param op
     * @param k
     * @return
     */
    private static List<List<Integer>> violenceExecute(int[] arr, boolean[] op, int k) {
        //记录进入候选区或者得奖区的用户信息
        Map<Integer, Customer> map = new HashMap<>();
        List<Customer> candis = new ArrayList<>();
        List<Customer> prizes = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrReject = op[i];
            map.putIfAbsent(id, new Customer(id, 0, 0));
            final Customer curCustomer = map.get(id);
            //购买
            if (buyOrReject) {
                curCustomer.buySize++;

                //退货
            } else {
                curCustomer.buySize--;
            }
            if (curCustomer.buySize <= 0) {
                map.remove(curCustomer.id);
            }
            //首次出现
            if (!prizes.contains(curCustomer) && !candis.contains(curCustomer)) {
                curCustomer.enterTime = i;
                //得奖区空闲
                if (prizes.size() < k) {
                    prizes.add(curCustomer);
                } else {
                    candis.add(curCustomer);
                }
            }
            cleanZeroCustomer(candis);
            cleanZeroCustomer(prizes);
            candis.sort(new CustomerCandisComparator());
            prizes.sort(new CustomerPriceComparator());
            move(candis, prizes, k, i);
            ans.add(getCurPrizes(prizes));
        }
        return ans;
    }

    private static void move(List<Customer> candis, List<Customer> prizes, int k, int enterTime) {
        if (candis.isEmpty()) {
            return;
        }
        final Customer bestCandis = candis.get(0);
        //没满
        if (prizes.size() < k) {
            bestCandis.enterTime = enterTime;
            prizes.add(bestCandis);
            candis.remove(0);
        } else {
            final Customer lessPrize = prizes.get(0);
            if (bestCandis.buySize > lessPrize.buySize) {
                prizes.remove(0);
                candis.remove(0);
                bestCandis.enterTime = enterTime;
                lessPrize.enterTime = enterTime;
                prizes.add(bestCandis);
                candis.add(lessPrize);
            }
        }

    }

    private static List<Integer> getCurPrizes(List<Customer> prizes) {
        return prizes.stream().map(t -> t.id).collect(Collectors.toList());
    }

    private static void cleanZeroCustomer(List<Customer> customers) {
        customers.removeIf(customer -> customer.buySize <= 0);
    }

    /**
     * 4,2,4 标识 4号用户 购买了两个商品，进入得奖区或者候选区时间为4（可以理解为arr[i] 、op[i]的i）
     */
    public static class Customer {
        /**
         * 用户主键
         */
        public int id;
        /**
         * 购买的商品大小
         */
        public int buySize;
        /**
         * 进入候选区或者得奖区的时间
         */
        public int enterTime;

        public Customer(int id, int buySize, int enterTime) {
            this.id = id;
            this.buySize = buySize;
            this.enterTime = enterTime;
        }
    }

    /**
     * 购买量最从小到大 + enterTime 从小到大 排序
     */
    public static class CustomerPriceComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buySize == o2.buySize ? o1.enterTime - o2.enterTime : o1.buySize - o2.buySize;
        }
    }

    /**
     * 购买量最从大到小 + enterTime 从小到大 排序
     */
    public static class CustomerCandisComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buySize == o2.buySize ? o1.enterTime - o2.enterTime : o2.buySize - o1.buySize;
        }
    }

}
