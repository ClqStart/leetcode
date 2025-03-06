package 第二季.Graph;


import java.util.Collection;
import java.util.Comparator;

@SuppressWarnings("unchecked")
public class MinHeap<E> {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private Comparator<E> comparator;

    private int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable<E>) e1).compareTo(e2);
    }

    public MinHeap(Collection<E> elements, Comparator<E> comparator) {
        this.comparator = comparator;

        size = elements == null ? 0 : elements.size();
        if (size == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            int capacity = Math.max(size, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            int i = 0;
            for (E element : elements) {
                this.elements[i++] = element;
            }
            heapify();
        }

    }

    public MinHeap(E[] elements, Comparator<E> comparator) {
        this.comparator=comparator;
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }

    }

    public MinHeap(Collection<E> elements) {
        this(elements, null);
    }

    public MinHeap(E[] elements) {
        this(elements, null);
    }

    public MinHeap() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addAll(Collection<E> elements) {
        if (elements == null) return;
        for (E element : elements) {
            add(element);
        }
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public void add(E element) {
        elementNotNullCheck(element);
        enSureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    public E get() {
        emptyCheck();
        return elements[0];
    }

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

    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0) {
            elements[0] = element;
            size++;
        } else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
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

    private void siftUp(int index) {
        E e = elements[index];
        while (index > 0) {
            int pindex = (index - 1) >> 1;
            E p = elements[pindex];
            if (compare(e, p) >= 0) break;
            elements[index] = p;
            index = pindex;
        }
        elements[index] = e;
    }

    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;
        while (index < half) {
            int ChildIndex = (index << 1) + 1;
            E child = elements[ChildIndex];
            int rightChild = ChildIndex + 1;
            if (rightChild < size && compare(elements[rightChild], child) < 0) {
                ChildIndex = rightChild;
                child = elements[rightChild];
            }
            if (compare(element, child) <= 0) break;
            elements[index] = child;
            index = ChildIndex;
        }
        elements[index] = element;
    }


    private void heapify() {
        for (int i = (size >> 1)-1; i >= 0; i--) {
            siftDown(i);
        }
    }

}
