package com.lk;


@SuppressWarnings("unchecked")

public class ArrayList<E> extends AbstractList<E> {

    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    private ArrayList(int capatity) {

        capatity = Math.max(capatity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capatity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        if (elements != null && elements.length > DEFAULT_CAPACITY) {
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        }

    }

    public void add(int index, E element) {
        rangeCheckADD(index);

        ensureCapacity(size + 1);
        if (size - index >= 0) System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        if (size - index >= 0) System.arraycopy(elements, index + 1, elements, index, size - index);
        elements[--size] = null;
        trim();
        return old;
    }

    private void trim() {
        int capacity = elements.length;
        int newcapacity = capacity >> 1;
        if (size >= newcapacity || capacity <= DEFAULT_CAPACITY) return;
        E[] newElement = (E[]) new Object[newcapacity];
        System.arraycopy(elements, 0, newElement, 0, size);
        elements = newElement;

        System.out.println(capacity + "新容量" + newcapacity);

    }

    public int indexOf(E element) {

        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    private int indexOf1(E element) {
        for (int i = 0; i < size; i++) {
            if (valEquals(element, elements[i])) return i;
        }
        return ELEMENT_NOT_FOUND;
    }

    private boolean valEquals(E v1, E v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }


    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        if (size >= 0) System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
        System.out.println(oldCapacity + "新容量" + newCapacity);

    }

    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    public E set(int index, E element) {

        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(",[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(",");
            }
            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Person- finalize");
    }
}
