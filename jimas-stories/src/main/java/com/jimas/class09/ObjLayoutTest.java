package com.jimas.class09;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * java 对象布局  markworld  classpoint instanceData pading
 * <p>
 * java -XX:+PrintCommandLineFlags  打印 默认参数
 * 对象 size 8字节对齐 : 必须是8字节得倍数 不足 padding 填充
 *
 * @author liuqj
 */
public class ObjLayoutTest {
    public static class T {
        private int m;
        private String s;
    }

    @Test
    public void testLayout() {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    /**
     * 可以看出JVM刚启动时，对象头的锁状态是无锁状态，大概过了4秒左右 就会变成 biasable 偏向锁状态
     * 加锁后便进入轻量级锁状态
     * -XX:BiasedLockingStartupDelay=4000
     *
     * @throws InterruptedException
     */
    @Test
    public void testLayout2() throws InterruptedException {
        Object o = new Object();
        System.out.println("还没有进入到同步块");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        Thread.sleep(5000);
        System.out.println("5s预热结束");
        Object b = new Object();
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
        synchronized (o) {
            System.out.println("进入到了同步块");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    public static void main(String[] args) {
        Object o = new T();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }


//  com.jimas.class09.ObjLayoutTest$T object internals:
//   OFF  SZ               TYPE DESCRIPTION               VALUE
//    0   8                    (object header: mark)     0x0000000000000005 (biasable; age: 0)
//    8   4                    (object header: class)    0x000d52d0
//   12   4                int T.m                       0
//   16   4   java.lang.String T.s                       null
//   20   4                    (object alignment gap)
//    Instance size: 24 bytes
}
// 移除压缩 -XX:-UseCompressedClassPointers -XX:-UseCompressedOops
//    java.lang.Object object internals:
//       OFF  SZ   TYPE DESCRIPTION               VALUE
//        0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
//        8   8        (object header: class)    0x000002027aa01ee0
//        Instance size: 16 bytes

// 开启压缩 -XX:+UseCompressedClassPointers +XX:-UseCompressedOops
//    java.lang.Object object internals:
//        OFF  SZ   TYPE DESCRIPTION               VALUE
//        0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
//        8   4        (object header: class)    0x00001000
//        12   4        (object alignment gap)
//        Instance size: 16 bytes