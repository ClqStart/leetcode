package com.lk;


@SuppressWarnings("unchecked")

public class singClirleLinkedList<E> extends AbstractList<E> {
    private Node<E> first;

    @Override
    public void clear() {
        this.first = null;
        this.size = 0;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0) {
            Node<E> Newfirst = new Node<>(element, first);
            Node<E> last = (size == 0) ? Newfirst : Node(size - 1);
            last.next = Newfirst;
            first = Newfirst;
        } else {
            Node<E> prenode = Node(index - 1);
            prenode.next = new Node<>(element, prenode.next);
        }
        size++;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Node<E> node = first;
        if (index == 0) {
            if (size == 1) {
                first = null;
            } else {
                Node<E> last = Node(size - 1);
                first = first.next;
                last.next = first;
            }
        } else {
            Node<E> preNode = Node(index - 1);
            node = preNode.next;
            preNode.next = preNode.next.next;
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {

        if (element == null) {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
                node = node.next;
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
        Node<E> node = first;
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

        @Override
        public String toString() {
            return element + "_" + next.element;
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

