package com.映射.Tree;


import java.util.Comparator;

public class RBTree<E> extends BBST<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean colorof(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorof(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorof(node) == RED;
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        //添加是根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色,直接返回
        if (isBlack(parent)) return;
        //叔父节点是红色
        Node<E> uncle = parent.sibling();
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)) { //[B树上溢]
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }
        //叔父 节点不是红色-
        if (parent.isLeftChild()) {//L
            if (node.isLeftChild()) {//LL
                black(parent);
            } else {//LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { //R
            if (node.isLeftChild()) { //RL
                black(node);
                rotateRight(parent);
            } else {//RR
                black(parent);
            }
            rotateLeft(grand);
        }

    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<E>(element, parent);
    }

    //    @Override
//    protected void afterRemove(Node<E> node, Node<E> replacement) {
//        //如果是红色节点
//        if (isRed(node)) return;
//        //用于取代黑色 node的节点是红色
//        if (isRed(replacement)) {
//            black(replacement);
//            return;
//        }
//        //删除得是黑色叶子节点
//
//        //删除的黑色节点是根节点
//        Node<E> parent = node.parent;
//        if (parent == null) return;
//        boolean left = parent.left == null || node.isLeftChild();
//        Node<E> sibling = left ? parent.right : parent.left;
//        if (left) {//被删除的节点在左边，兄弟节点在右边
//            if (isRed(sibling)) {
//                black(sibling);
//                red(parent);
//                rotateLeft(parent);
//                //跟换兄弟节点
//                sibling = parent.right;
//            }
//            //兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack) {
//                    afterRemove(parent, null);
//                }
//            } else {//兄弟节点至少有一个红色节点，向兄弟节点借元素
//
//                //兄弟节点左边是黑色，兄弟节点先左旋转
//                if (isBlack(sibling.right)) {
//                    rotateRight(sibling);
//                    sibling = parent.right;
//                }
//                //兄弟节点结点全部进行右旋转
//                color(sibling, colorof(parent));
//                black(sibling.right);
//                black(parent);
//                rotateLeft(parent);
//            }
//
//        } else {//删除的节点在右边，兄弟节点在左边
//            if (isRed(sibling)) {
//                black(sibling);
//                red(parent);
//                rotateRight(parent);
//                //跟换兄弟节点
//                sibling = parent.left;
//            }
//            //兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                //没有一个红节点，父节点向下合并
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//                if (parentBlack) {
//                    afterRemove(parent, null);
//                }
//            } else {//兄弟节点至少有一个红色节点，向兄弟节点借元素
//
//                //兄弟节点左边是黑色，兄弟节点先左旋转
//                if (isBlack(sibling.left)) {
//                    rotateLeft(sibling);
//                    sibling = parent.left;
//                }
//                //兄弟节点结点全部进行右旋转
//                color(sibling, colorof(parent));
//                black(sibling.left);
//                black(parent);
//                rotateRight(parent);
//            }
//        }
//
//    }
    @Override
    protected void afterRemove(Node<E> node) {
        //如果是红色节点
        // if (isRed(node)) return;
        //用于取代黑色 node的节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }
        //删除得是黑色叶子节点

        //删除的黑色节点是根节点
        Node<E> parent = node.parent;
        if (parent == null) return;
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) {//被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //跟换兄弟节点
                sibling = parent.right;
            }
            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {//兄弟节点至少有一个红色节点，向兄弟节点借元素

                //兄弟节点左边是黑色，兄弟节点先左旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                //兄弟节点结点全部进行右旋转
                color(sibling, colorof(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }

        } else {//删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateRight(parent);
                //跟换兄弟节点
                sibling = parent.left;
            }
            //兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //没有一个红节点，父节点向下合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {//兄弟节点至少有一个红色节点，向兄弟节点借元素

                //兄弟节点左边是黑色，兄弟节点先左旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                //兄弟节点结点全部进行右旋转
                color(sibling, colorof(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }

    }

    private static class RBNode<E> extends Node<E> {
        boolean color;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }
}
