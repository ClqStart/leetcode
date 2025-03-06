package com.二叉搜索树2.Tree;


import com.二叉搜索树2.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size;
    protected Node<E> root;

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }



    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public static abstract class Visitor<E> {
        boolean stop;
        /**
         * @return 如果返回true，就代表停止遍历
         */
        public abstract boolean visit(E element);
    }

    public void preorderTraversal(Visitor<E> visitor) {
        preorderTraversal(root, visitor);
    }

    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        visitor.stop = visitor.visit(node.element);
        preorderTraversal(node.left, visitor);
        preorderTraversal(node.right, visitor);
    }

    public void inorderTraversal(Visitor<E> visitor) {
        inorderTraversal(root, visitor);
    }

    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        inorderTraversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inorderTraversal(node.right, visitor);
    }


    public void PostorderTraversal(Visitor<E> visitor) {
        PostorderTraversal(root, visitor);
    }

    private void PostorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        PostorderTraversal(node.left, visitor);
        PostorderTraversal(node.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }


    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            visitor.visit(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

    }
    public boolean isComplete() {
        if (root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.hasTwoChildren()) {
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) {
                return false;
            } else {
                leaf = true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
            }
        }
        return true;
    }

    public int height() {
        if (root == null) return 0;
        int height = 0;
        int levelSize = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }

        }


        return height;
    }

    protected Node<E> predesessor(Node<E> node) {
        if (node.left != null) {
            Node<E> p = node.left;
            while (p != null) {
                p = p.right;
            }
            return p;
        }
        //node.left==null
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        //node.parent ==null 或 node==node.parent.right
        return node.parent;
    }

    protected Node<E> successor(Node<E> node) {
        if (node.right != null) {
            Node<E> S = node.right;
            while (S.left != null) {
                S = S.left;
            }
            return S;
        }
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        return node.parent;
    }

    public void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }


    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>) node;
        String parentString = null;
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_P(" + parentString + ")";
    }

}
