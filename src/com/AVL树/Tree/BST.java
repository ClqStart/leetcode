package com.AVL树.Tree;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class BST<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    public BST() {
        this(null);
    }

    public BST(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    public void add(E element) {
        if (this.root == null) {
            root =createNode(element, null);
            size++;
            afterAdd(root);
            return;
        }
        Node<E> node = root;
        Node<E> parent = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        afterAdd(newNode);
    }

    /**
     *
     * 添加node后的调整
     */

    protected  void  afterAdd(Node<E> node) {}


    /**
     *
     * 删除node后的调整
     */
    protected  void  afterRemove(Node<E> node) {}

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }


    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) return;

        size--;
        if (node.hasTwoChildren()) {
            //找到后继节点
            Node<E> S = successor(node);
            node.element = S.element;
            //删除后继节点
            node = S;
        }
        //统一删除node(node度为1或者0)
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) { //node是度为1并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            afterRemove(node);

        } else if (node.parent == null) { //为叶子节点且是根节点
            root = null;
        } else { //为叶子结点不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            afterRemove(node);
        }

    }

    public boolean contains(E element) {
        return node(element) != null;
    }


    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }


}


