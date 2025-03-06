package com.AVL树.Tree;

import java.util.Comparator;

public class AVLTree<E> extends BST<E> {
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

    private void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);

    }

    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void ratate(
            Node<E> r,//子树的根节点
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e, Node<E> f, Node<E> g) {

        //d 成为子树根节点
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }
        //a-b-c
        b.left = a;
        if (a != null) {
            a.parent = b;
        }
        b.right = c;
        if (c != null) {
            c.parent = b;
        }
        updateHeight(b);

        //e-f-g

         f.left=e;
         if(e !=null){
             e.parent=f;
         }
         f.right=g;
         if(g!=null){
             g.parent =f;
         }
         updateHeight(f);

         //b-d-f
        d.left=b;
        d.right=f;
        b.parent=d;
        f.parent=d;
        updateHeight(d);

    }

    private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }
        if (child != null) {
            child.parent = grand;
        }
        grand.parent = parent;
        updateHeight(grand);
        updateHeight(parent);

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
