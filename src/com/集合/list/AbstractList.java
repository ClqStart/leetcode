package com.集合.list;

import com.lk.List;

public abstract class AbstractList<E> implements List<E>{

    protected int size;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)==ELEMENT_NOT_FOUND;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }



    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index" + index + ", Size:" + size);
    }

    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    protected void rangeCheckADD(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

}
