package com.堆;

import com.堆.printer.BinaryTreeInfo;

import java.util.Comparator;
//大鼎堆

@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            this.size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }

    }


    public BinaryHeap(Comparator<E> comparator) {
        this(null, comparator);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }


    public BinaryHeap() {
        this(null, null);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;

    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        enSureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = size - 1;
        E root = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        size--;
        siftDown(0);
        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0) {
            elements[size++] = element;
        } else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;

    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must be not empty");
        }
    }

    private void enSureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;
        while (index < half) {
            int ChildIndex = (index << 1) + 1;
            E child = elements[ChildIndex];
            int rightChild = ChildIndex + 1;
            if (rightChild < size && compare(elements[rightChild], child) > 0) {
                ChildIndex = rightChild;
                child = elements[rightChild];
            }
            if (compare(element, child) >= 0) break;
            elements[index] = child;
            index = ChildIndex;
        }
        elements[index] = element;

    }

    private void siftUp(int index) {
//        E e = elements[index];
//        while (index > 0) {
//            int pindex = (index - 1) >> 1;
//            E p = elements[pindex];
//            if(compare(e,p)<=0) return;
//            E tmp = elements[index];
//            elements[index] = elements[pindex];
//            elements[pindex] = tmp;
//            index = pindex;
//        }

        E e = elements[index];
        while (index > 0) {
            int pindex = (index - 1) >> 1;
            E p = elements[pindex];
            if (compare(e, p) <= 0) break;
            elements[index] = p;
            index = pindex;
        }
        elements[index] = e;
    }

    private void heapify() {
        //自上而下的上虑
//        for (int i = 1; i <size ; i++) {
//            siftUp(i);
//        }


        //自下而上的下虑
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }


    }


    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int) node];
    }
}

