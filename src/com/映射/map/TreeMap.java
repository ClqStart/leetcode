package com.映射.map;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings({"uncheck","unused"})
public class TreeMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V> root;
    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
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

    @Override
    public V put(K key, V value) {
        KeyNotNullCheck(key);
        if (this.root == null) {
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(value, node.value)) return true;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) return;
        traversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorof(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorof(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorof(node) == RED;
    }


    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        //添加是根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色,直接返回
        if (isBlack(parent)) return;
        //叔父节点是红色
        Node<K, V> uncle = parent.sibling();
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { //[B树上溢]
            black(parent);
            black(uncle);
            afterPut(grand);
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

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);

    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
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

    }

    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private V remove(Node<K, V> node) {
        if (node == null) return null;

        size--;

        V oldValue = node.value;
        if (node.hasTwoChildren()) {
            //找到后继节点
            Node<K, V> S = successor(node);
            node.key = S.key;
            node.value = S.value;
            //删除后继节点
            node = S;
        }
        //统一删除node(node度为1或者0)
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) { //node是度为1并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            afterRemove(replacement);

        } else if (node.parent == null) { //为叶子节点且是根节点
            root = null;
            afterRemove(node);
        } else { //为叶子结点不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            afterRemove(node);
        }
        return oldValue;
    }

    private Node<K, V> predesessor(Node<K, V> node) {
        if (node.left != null) {
            Node<K, V> p = node.left;
            while (p != null) {
                p = p.right;
            }
            return p;
        }
        //node.left==null
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        //node.parent ==null 或 node==node.parent.right  没有前驱
        return node.parent;
    }

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        if (node.right != null) {
            Node<K, V> S = node.right;
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

    private void afterRemove(Node<K, V> node) {
        //如果是红色节点
        // if (isRed(node)) return;
        //用于取代黑色 node的节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }
        //删除得是黑色叶子节点

        //删除的黑色节点是根节点
        Node<K, V> parent = node.parent;
        if (parent == null) return;
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
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

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    private void KeyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }


    private int compare(K e1, K e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<K>) e1).compareTo(e2);
    }

    private static class Node<K, V> {
        K key;
        V value;
        boolean color= RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }

    }
}
