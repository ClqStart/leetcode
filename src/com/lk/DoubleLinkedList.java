package com.lk;



@SuppressWarnings("unchecked")

public class DoubleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;
    private Node<E> last;


    @Override
    public void clear() {
        this.first = null;
        this.size = 0;
        this.last = null;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckADD(index);
        if (index == size) {
            Node<E> oldLast = last;
            last = new Node<>(oldLast, element, null);
            if (oldLast == null) {
                first = last;
            } else {
                oldLast.next = last;
            }
        } else {
            Node<E> next = Node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node(prev, element, next);
            next.prev = node;
            if (prev == null) {
                first = node;
            } else {
                prev.next = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Node<E> node = Node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev == null) {
            first=next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last =prev;
        } else {
            next.prev = prev;
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(Object element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public E get(int index) {

        return Node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = Node(index);
        E oldelement = node.element;
        node.element = element;
        return oldelement;
    }

    private Node<E> Node(int index) {
        rangeCheck(index);

        if (index < (size >> 1)) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            StringBuilder sb=new StringBuilder();
            if(prev != null){
                sb.append(prev.element);
            }else{
                sb.append("null");
            }
            sb.append("_").append(element).append("_");
            if (next != null){
                sb.append(next.element);
            }else{
                sb.append("null");
            }
            return  sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(node);
            node = node.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

