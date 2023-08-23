package com.jimas.class18;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 会议室预定，最多可以安排几场会议，返回最多得场次
 *
 * @author liuqj
 */
public class BestArrange {

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {
            Room[] rooms = randomRoom();
            if (bestTwo(rooms) != bestOne(rooms)) {
                System.out.println("ERR");
            }
        }

    }

    private static class Room {
        int start;
        int end;

        public Room(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Room{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    /**
     * 暴力解
     * 比如：优先安排第一场，后面
     *
     * @param rooms 会议室
     * @return 场次
     */
    private static int bestOne(Room[] rooms) {
        if (rooms == null || rooms.length == 0) {
            return 0;
        }

        return process(rooms, 0, 0);


    }

    private static int process(Room[] rooms, int timeLine, int done) {
        if (rooms == null || rooms.length == 0) {
            return done;
        }
        int result = done;
        for (int i = 0; i < rooms.length; i++) {
            //遍历 假设第一个符合的先加入会议
            if (timeLine <= rooms[i].start) {
                Room[] nextRooms = removeByIndex(rooms, i);
                result = Math.max(result, process(nextRooms, rooms[i].end, done + 1));
            }
        }
        return result;

    }

    /**
     * 贪心算法：
     * 要求结束时间靠前的优先安排(局部最优解)
     */
    private static int bestTwo(Room[] rooms) {
        if (rooms == null || rooms.length == 0) {
            return 0;
        }
        Arrays.sort(rooms, Comparator.comparingInt(o -> o.end));
        int result = 0;
        int timeLine = 0;
        for (Room room : rooms) {
            if (timeLine <= room.start) {
                result++;
                timeLine = room.end;
            }
        }
        return result;
    }

    private static Room[] removeByIndex(Room[] rooms, int index) {
        Room[] newRooms = new Room[rooms.length - 1];
        int j = 0;
        for (int i = 0; i < rooms.length; i++) {
            if (i != index) {
                newRooms[j++] = rooms[i];
            }
        }
        return newRooms;
    }

    private static Room[] randomRoom() {
        int length = (int) (Math.random() * 10);
        Room[] rooms = new Room[length];
        for (int i = 0; i < length; i++) {
            int start = (int) (Math.random() * 30);
            int end = start + (int) (Math.random() * 10) + 1;
            rooms[i] = new Room(start, end);
        }
        return rooms;
    }

}
