package com.jimas.class01;

import java.lang.instrument.Instrumentation;

/**
 * @author liuqj
 */
public class ObjectSizeAgent {
    private static Instrumentation inst;

    /**
     * 核心方法，固定方法名 premain
     * @param agentArgs
     * @param instrumentation
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        inst = instrumentation;
    }

    public static long sizeOf(Object o) {
        return inst.getObjectSize(o);
    }
}
