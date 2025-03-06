package com.lk;


@SuppressWarnings("unchecked")

public class LinkedList2<E> extends AbstractList<E> {
    private Node<E> first;


    public LinkedList2() {
        this.first = new Node<>(null, null);
    }

    @Override
    public void clear() {
        this.first = null;
        this.size = 0;
    }

    @Override
    public void add(int index, E element) {

        Node<E> prenode = index == 0 ? first : Node(index - 1);
        prenode.next = new Node<>(element, prenode.next);
        size++;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Node<E> preNode = index == 0? first: Node(index - 1);
        Node<E> node = preNode.next;
        preNode.next = preNode.next.next;
        size--;
        return node.element;
    }



    @Override
    public int indexOf(Object element) {
        Node<E> node = first.next;
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
                node=node.next;
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
        Node<E> node = first.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size=").append(size).append(", [");
        Node<E> node = first.next;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(node.element);
            node = node.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

