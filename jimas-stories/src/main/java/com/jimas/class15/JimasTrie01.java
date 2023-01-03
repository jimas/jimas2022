package com.jimas.class15;

import java.util.Objects;

/**
 * 前缀树：
 * 又称查找树，Trie树，哈希树的变种。典型应用是用于统计，排序和保存大量字符串
 * 经常被搜索引擎系统用于文本词频统计。
 * 优点：利用字符串的公共前缀来减少查询时间，最大限度地减少所谓的字符串比较，查询效率比哈希树高
 *
 * @author liuqj
 */
public class JimasTrie01 {
    private NodeTrie root;

    public JimasTrie01() {
        this.root = new NodeTrie();
    }

    public static class NodeTrie {
        int pass;
        int end;
        NodeTrie[] nexts;

        /**
         * 0----a
         * 1----b
         * 2----c
         */
        public NodeTrie() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new NodeTrie[26];
        }


    }

    /**
     * 加入前缀树
     *
     * @param str
     */
    public void add(String str) {
        if (Objects.isNull(str)) {
            return;
        }
        final char[] chars = str.toCharArray();
        NodeTrie current = root;
        root.pass++;
        for (char aChar : chars) {
            int index = aChar - 'a';
            if (current.nexts[index] == null) {
                current.nexts[index] = new NodeTrie();
            }
            current.nexts[index].pass++;
            current = current.nexts[index];
        }
        current.end++;

    }

    /**
     * 查询包含该前缀的字符串数量
     *
     * @param str
     * @return
     */
    public int preNum(String str) {
        if (Objects.isNull(str)) {
            return -1;
        }
        final char[] chars = str.toCharArray();
        NodeTrie current = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            if (current.nexts[index] == null) {
                return 0;
            }
            current = current.nexts[index];
        }
        return current.pass;

    }

    /**
     * 查询字符串次数
     *
     * @param str
     * @return
     */
    public int searchNum(String str) {
        if (Objects.isNull(str)) {
            return -1;
        }
        final char[] chars = str.toCharArray();
        NodeTrie current = root;
        for (char aChar : chars) {
            int index = aChar - 'a';
            if (current.nexts[index] == null) {
                return 0;
            }
            current = current.nexts[index];
        }
        return current.end;
    }

    /**
     * 删除字符串
     *
     * @param str
     */
    private void delete(String str) {
        //该串存在
        if (searchNum(str) > 0) {
            root.pass--;
            final char[] chars = str.toCharArray();
            NodeTrie current = root;
            for (char aChar : chars) {
                int index = aChar - 'a';
                current.nexts[index].pass--;
                current = current.nexts[index];
            }
            current.end--;
        }

    }

    public static void main(String[] args) {
        String[] strs = new String[]{"abc", "abd", "bcd", "okf", "asfi", "tyrew"};
        final JimasTrie01 trie01 = new JimasTrie01();
        for (String str : strs) {
            trie01.add(str);
        }

        System.out.println(trie01.preNum("ab"));
        System.out.println(trie01.preNum("ty"));
        System.out.println(trie01.searchNum("okf"));
        System.out.println(trie01.searchNum("tyrew"));
        trie01.delete("tyrew");
        System.out.println(trie01.searchNum("tyrew"));

    }

}
