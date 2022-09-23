package com.jimas.class07;

import com.jimas.RandomLinkedNode;
import org.junit.Test;

/**
 * 有环链表，怎么找第一个入环节点
 *
 * @author liuqj
 */
public class RingFirstNodeTest {
    @Test
    public void firstNodeTest() {
        RandomLinkedNode.Node root = RandomLinkedNode.randomRingLinkedList(4, 5, 10);
        RandomLinkedNode.Node oneStepPoint = root.getNext();
        RandomLinkedNode.Node twoStepPoint = root.getNext().getNext();
        //1、两个指针，都从起点移动。指针①每次走一步，指针②每次走两步，直到两个相遇。
        while (oneStepPoint != twoStepPoint) {
            oneStepPoint = oneStepPoint.getNext();
            twoStepPoint = twoStepPoint.getNext().getNext();
        }
        //2、在第一步相遇后，指针①回到起点，指针②保持不变；之后在同时每个都走一步直至相遇，相遇节点便是入环的第一个节点。
        oneStepPoint = root;
        while (oneStepPoint != twoStepPoint){
            oneStepPoint = oneStepPoint.getNext();
            twoStepPoint = twoStepPoint.getNext();
        }
        System.out.println(oneStepPoint);
    }
}
