package com.jimas.class01;

/**
 * -javaagent:D:\tmp\jimas-agent.jar
 *
 * hotspot VM 中，对象在内存中的存储布局分为3部分
 * - 对象头
 * - 实例数据
 * - 对齐填充
 * @author liuqj
 */
public class MainApp {

    public static void main(String[] args) {
        System.out.println(3%500);
        System.out.println(ObjectSizeAgent.sizeOf(new Object()));
        System.out.println(ObjectSizeAgent.sizeOf(new int[]{}));
        System.out.println(ObjectSizeAgent.sizeOf(new A()));
    }

    public static class A {
        private Integer a;// 4
        private Long b; // 8
        //private Double c; //8
        private Integer v;// 4
//        private Object o;
//        private byte b1;
//        private byte b2;


    }
}
