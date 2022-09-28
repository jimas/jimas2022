package com.jimas.class10;

import com.jimas.class10.stack.JStack;
import com.jimas.class10.stack.impl.ArrayStack;
import com.jimas.class10.stack.impl.DoubleNodeStack;
import org.junit.Test;

/**
 * @author liuqj
 */
public class JStackTest {
    @Test
    public void testArrayStack() {
        JStack<Integer> jStack = new ArrayStack(5);
        testPrint(jStack);
    }

    @Test
    public void testDoubleNodeStack() {
        JStack<Integer> jStack = new DoubleNodeStack(5);
        testPrint(jStack);
    }

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
        for (int i = 0; i < 5; i++) {
            System.out.println(jStack.push(i));
        }
    }
}
