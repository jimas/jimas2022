package com.jimas.class10;

import com.jimas.class10.stack.JStack;
import com.jimas.class10.stack.impl.*;
import org.junit.Test;

/**
 * @author liuqj
 */
public class JStackTest {
    private static int capacity = 10;

    @Test
    public void testArrayStack() {
        JStack<Integer> jStack = new ArrayStack(capacity);
        testPrint(jStack);
        System.out.println("====================");
    }
//
//    @Test
//    public void testDoubleNodeStack() {
//        JStack<Integer> jStack = new DoubleNodeStack(capacity);
//        testPrint(jStack);
//        System.out.println("====================");
//
//    }
//
//    @Test
//    public void testNodeStack() {
//        JStack<Integer> jStack = new NodeStack(capacity);
//        testPrint(jStack);
//        System.out.println("====================");
//
//    }

    @Test
    public void testDoubleQueueStack() {
        JStack<Integer> jStack = new DoubleQueueStack(capacity);
        testPrint(jStack);
        System.out.println("====================");

    }

    /*@Test
    public void testQueueStack() {
        JStack<Integer> jStack = new QueueStack(capacity);
        testPrint(jStack);
        System.out.println("====================");

    }*/

    private void testPrint(JStack<Integer> jStack) {
        for (int i = 0; i < 5; i++) {
            System.out.println(jStack.push(i));
        }
        //0 1 2 3 4
        for (int i = 0; i < 9; i++) {
            System.out.println(jStack.pop());
        }// 0 1
        for (int i = 0; i < 5; i++) {
            System.out.println(jStack.push(i));
        }// 0 1 0 1 2
        System.out.println(jStack.peek());
        System.out.println(jStack.peek());
        for (int i = 5; i < 15; i++) {
            System.out.println(jStack.push(i));
        }
        System.out.println(jStack.peek());
    }
}
