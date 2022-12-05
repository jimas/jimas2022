package com.jimas.class13;

import java.util.*;

/**
 * <p>最大线段重合问题（用堆实现）：</>
 * 给定很多线段，每个线段都有两个数[start,end]
 * 表示线段开始位置和结束位置，左右都是闭区间
 * 规定：
 * 1、线段的开始位置与结束位置 都是整数值
 * 2、线段重合区域长度大于等于1
 * 返回线段最多重合区域中，包含了几条线段
 *
 * @author liuqj
 */
public class SegmentCoincide {

    public static void main(String[] args) {

        List<Segment> segments = generateRandomSegments();
        //按线段开始位置 升序排列
        Collections.sort(segments, new SegmentStartSort());
        int max = 0;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (Segment segment : segments) {
            while (!priorityQueue.isEmpty() && priorityQueue.peek() <= segment.getStart()) {
                priorityQueue.poll();
            }
            priorityQueue.add(segment.getEnd());
            max = Math.max(max, priorityQueue.size());
        }
        System.out.println("===========max:" + max);
        System.out.println(segments);


    }


    public static class Segment {
        private int start;
        private int end;

        public Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return "Segment{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    /**
     * 随机生产线段
     *
     * @return
     */
    public static List<Segment> generateRandomSegments() {
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int min = 1;
            int max = min + (int) (Math.random() * 15) + 1;
            int start = (int) (Math.random() * max);
            int end = max;
            segments.add(new Segment(start, end));
        }
        return segments;
    }

    public static class SegmentStartSort implements Comparator<Segment> {

        @Override
        public int compare(Segment o1, Segment o2) {
            return o1.start - o2.start;
        }
    }

}
