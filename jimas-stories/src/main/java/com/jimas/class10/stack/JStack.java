package com.jimas.class10.stack;

/**
 * 栈
 * 先进后出
 *
 * @author liuqj
 */
public interface JStack<E> {
    /**
     * 压入栈
     *
     * @param e
     * @return
     */
    boolean push(E e);

    /**
     * 弹出栈
     *
     * @return
     */
    E pop();

    /**
     * 获取栈顶数据
     *
     * @return
     */
    E peek();


}
