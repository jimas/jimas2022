package com.jimas.class10.queue;

/**
 * 队列
 * 先进先出
 *
 * @author liuqj
 */
public interface JQueue<E> {
    /**
     * 添加元素
     *
     * @param e
     * @return
     */
    boolean add(E e);

    /**
     * 移除头部元素 并返回
     *
     * @return
     */
    E poll();

    /**
     * 获取 头部元素
     *
     * @return
     */
    E peek();

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 队列大小
     *
     * @return
     */
    int size();

}
