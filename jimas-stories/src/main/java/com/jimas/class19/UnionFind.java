package com.jimas.class19;

import java.util.*;

/**
 * 并查集
 * 每个节点起始都指向自己（自己是自己的父）
 * @author liuqj
 */
public class UnionFind<V> {
    Map<V, V> parents;
    Map<V, Integer> sizeMap;

    public UnionFind(List<V> values) {
        parents = new HashMap<>();
        sizeMap = new HashMap<>();
        for (V value : values) {
            parents.put(value, value);
            sizeMap.put(value, 1);
        }
    }

    public void union(V a, V b) {
        V aAncestor = findAncestor(a);
        V bAncestor = findAncestor(b);
        if (aAncestor != bAncestor) {
            Integer aSize = sizeMap.get(aAncestor);
            Integer bSize = sizeMap.get(bAncestor);
            V big = aSize > bSize ? aAncestor : bAncestor;
            V small = big == aAncestor ? bAncestor : aAncestor;
            sizeMap.put(big, aSize + bSize);
            sizeMap.remove(small);
            parents.put(small, big);
        }
    }

    public boolean isSameSet(V a, V b) {
        return findAncestor(a) == findAncestor(b);
    }

    private V findAncestor(V cur) {
        Stack<V> stack = new Stack<>();
        while (parents.get(cur) != cur) {
            stack.push(cur);
            cur = parents.get(cur);
        }
        while (!stack.isEmpty()) {
            parents.put(stack.pop(), cur);
        }
        return cur;
    }

    public static void main(String[] args) {
        UnionFind<Integer> unionFind = new UnionFind<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        unionFind.union(4, 5);
        unionFind.union(3, 4);
        System.out.println(unionFind.isSameSet(3, 5));
        System.out.println(unionFind.isSameSet(3, 7));

    }
}
