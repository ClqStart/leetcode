package com.二叉搜索树;

import com.二叉搜索树.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class BinarySearchTree<E> implements BinaryTreeInfo {
    private int size;
    private Node<E> root;
    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private static class Node<E> {
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

    public void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    public void add(E element) {
        if (this.root == null) {
            root = new Node<>(element, null);
            size++;
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
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
    }

    public void preorderTraversal() {
        preorderTraversal(root);
    }

    private void preorderTraversal(Node<E> node) {
        if (node == null) return;
        System.out.print(node.element);
        System.out.print(" ");
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node == null) return;
        inorderTraversal(node.left);
        System.out.print(node.element);
        System.out.print(" ");
        inorderTraversal(node.right);
    }


    public void PostorderTraversal() {
        PostorderTraversal(root);
    }

    private void PostorderTraversal(Node<E> node) {
        if (node == null) return;
        PostorderTraversal(node.left);
        PostorderTraversal(node.right);
        System.out.print(node.element);
        System.out.print(" ");
    }

    /*
     * 层次队列，队列
     * */
    public void levelOrderTranversal() {
        if (root == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            System.out.print(node.element);
            System.out.print(" ");
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

    }


    public static abstract class Visitor<E> {
        boolean stop;
        /**
         * @return 如果返回true，就代表停止遍历
         */
        public abstract boolean visit(E element);
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

        inorderTraversal(node.left, visitor);
        if (visitor.stop) return;
        inorderTraversal(node.right, visitor);
        visitor.stop = visitor.visit(node.element);
    }


    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    public void remove(E element) {
        remove(node(element));
    }

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

        } else if (node.parent == null) { //为叶子节点且是根节点
            root = null;
        } else { //为叶子结点不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }

    }

    public boolean contains(E element) {
        return node(element) != null;
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

    private Node<E> predesessor(Node<E> node) {
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

    private Node<E> successor(Node<E> node) {
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

    public boolean isComplete2() {
        if (root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                leaf = true;
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

    public int height1() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb, "");
        return sb.toString();

    }

    private void toString(Node<E> node, StringBuilder sb, String sign) {
        if (node == null) return;
        sb.append(sign).append(node.element).append("\n");
        toString(node.left, sb, sign + "【L】--");
        toString(node.right, sb, sign + "【R】--");
    }
}
