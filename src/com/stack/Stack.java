package com.stack;

import com.stack.list.ArrayList;
import com.stack.list.List;

public class Stack<E> {
    private List<E> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.getSize();
    }

    public void push(E element) {
        list.add(element);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public E pop() {
        return list.remove(list.getSize() - 1);
    }

    public E top() {
        return list.get(list.getSize() - 1);
    }
}
