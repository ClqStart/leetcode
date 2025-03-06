package com.红黑树.Tree;

import java.util.Comparator;

public class AVLTree<E> extends BBST<E> {
    private Comparator<E> comparator;

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    public boolean isBalabced(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_P(" + parentString + ")_h(" + height + ")";
        }
    }

    private void reBalance2(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();

        if (parent.isLeftChild()) {//L
            if (node.isLeftChild()) {//ll
                rotateRight(grand);
            } else {//LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {//R
            if (node.isLeftChild()) {//RL
                rotateRight(parent);
                rotateLeft(grand);
            } else {//RR
                rotateLeft(grand);
            }
        }
    }

    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);
        updateHeight(grand);
        updateHeight(parent);
    }

    @Override
    protected void ratate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        super.ratate(r, a, b, c, d, e, f, g);
        updateHeight(b);
        updateHeight(f);
        updateHeight(d);
    }

    private void reBalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();

        if (parent.isLeftChild()) {//L
           if(node.isLeftChild()){//LL
               ratate(grand,node.left,node,node.right,parent,parent.right,grand,grand.right);
            } else {//LR
               ratate(grand,parent.left,parent,node.left,node,node.right,grand,grand.right);
           }
        } else {//R
            if (node.isLeftChild()) {//RL
                ratate(grand,grand.left,grand,node.left,node,node.right,parent,parent.right);
            } else {//RR
                ratate(grand,grand.left,grand,parent.left,parent,node.left,node,node.right);
            }
        }
    }


    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalabced(node)) {
                updateHeight(node);
            } else {
                reBalance(node);
                break;
            }
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }


    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalabced(node)) {
                updateHeight(node);
            } else {
                reBalance(node);
            }
        }
    }
}
