package com.jimas.class14;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 加强堆
 * T 要非基础类型
 *
 * @author liuqj
 */
public class HeapGreater<T> {
    /**
     * 用来存放数据
     */
    private List<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    /**
     * 通过 比较器比较，最小的在堆顶
     */
    private final Comparator<? super T> comparator;

    public HeapGreater(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.heapSize = 0;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int getHeapSize() {
        return heapSize;
    }

    /**
     * 进堆
     *
     * @param t
     */
    public void push(T t) {
        heap.add(t);
        indexMap.put(t, heapSize);
        heapInsert(heapSize++);
    }

    /**
     * 弹出堆
     *
     * @return
     */
    public T pop() {
        T t = heap.get(0);
        swapElements(0, heapSize - 1);
        indexMap.remove(t);
        heap.remove(--heapSize);
        heapify(0);
        return t;
    }

    /**
     * 查看堆顶元素
     *
     * @return
     */
    public T peek() {

        return heap.get(0);
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T t : heap) {
            ans.add(t);
        }
        return ans;
    }

    /**
     * 移除堆元素
     *
     * @param t
     * @return
     */
    public boolean remove(T t) {
        //得到替换元素
        T replace = heap.get(heapSize - 1);
        //获取t index
        Integer index = indexMap.get(t);
        indexMap.remove(t);
        heap.remove(--heapSize);
        if (replace != t) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
        return false;
    }

    /**
     * 是否存在该元素
     *
     * @param t
     * @return
     */
    public boolean contains(T t) {
        return indexMap.containsKey(t);
    }

    /**
     * 重新校准堆结构
     *
     * @param t
     */
    public void resign(T t) {
        final Integer index = indexMap.get(t);
        heapInsert(index);
        heapify(index);

    }

    /**
     * 类似 新节点插入（默认 插入集合heap 最后位置，然后再与父节点比较大小。最终确认真实得位置）
     *
     * @param index
     */
    private void heapInsert(Integer index) {
        // 父节点是否比自己大,比自己大 就交互位置
        while (this.comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swapElements((index - 1) / 2, index);
            index = (index - 1) / 2;
        }
    }

    /**
     * 类似 堆顶元素移除，重新调整堆结构
     *
     * @param index
     */
    private void heapify(Integer index) {
        int leftChildIndex = index * 2 + 1;
        //说明有左孩子
        while (leftChildIndex < heapSize) {
            //比较左右孩子大小，有右孩子，并且右孩子比左孩子小，best 即为右，反之为左
            int best = (leftChildIndex + 1) < heapSize && this.comparator.compare(heap.get(leftChildIndex + 1), heap.get(leftChildIndex)) < 0 ? leftChildIndex + 1 : leftChildIndex;
            best = this.comparator.compare(heap.get(index), heap.get(best)) < 0 ? index : best;
            //best
            if (best == index) {
                break;
            }
            swapElements(best, index);
            //重新以maxIndex 为顶 进行调整堆结构
            index = best;
            leftChildIndex = index * 2 + 1;
        }
    }


    private void swapElements(int i, Integer j) {
        T targetElement = heap.get(i);
        T sourceElement = heap.get(j);
        heap.set(i, sourceElement);
        heap.set(j, targetElement);
        indexMap.put(targetElement, j);
        indexMap.put(sourceElement, i);
    }
}
