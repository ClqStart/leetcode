package com.stack.list;

public interface List<E> {
    static final int ELEMENT_NOT_FOUND = -1;

    void clear();


    int getSize();


    boolean isEmpty();


    boolean contains(E element);


    void add(E element);


    void add(int index, E element);


    E remove(int index);


    int indexOf(E element);


    E get(int index);


    E set(int index, E element);

}
