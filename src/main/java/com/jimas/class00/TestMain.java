package com.jimas.class00;

public class TestMain {

    public void printInt32() {
        int num = 63;
        for (int i = 31; i >= 0; i--) {
            //(num & (1 << i)) == 0 代表的数值为0
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
    }

    public void printLong64() {
        long num = Long.MAX_VALUE;
        for (int i = 63; i >= 0; i--) {
            //(num & (1 << i)) == 0 代表的数值为0
            System.out.print((num & (1L << i)) == 0 ? "0" : "1");
        }
    }

    public void addToBitMap() {
        BitMap bitMap = new BitMap(10000);
        bitMap.add(100);
        bitMap.add(0);
        bitMap.add(1);
        System.out.println(bitMap.contains(0));
        System.out.println(bitMap.contains(1));
        System.out.println(bitMap.contains(99));
        System.out.println(bitMap.contains(100));
        bitMap.del(100);
        bitMap.del(0);
        System.out.println(bitMap.contains(0));
        System.out.println(bitMap.contains(1));
        System.out.println(bitMap.contains(99));
        System.out.println(bitMap.contains(100));
    }

    public void bubbleTest() {
        ArrSort arrSort = new ArrSort();
        for (int i = 0; i < 10; i++) {
            arrSort.bubble();
        }
    }

    public void insertTest() {
        ArrSort arrSort = new ArrSort();
        for (int i = 0; i < 10; i++) {
            arrSort.insert();
        }
    }


}
