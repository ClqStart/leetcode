package com.哈希表.map;

import com.哈希表.printer.BinaryTreeInfo;
import com.哈希表.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class HashMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        resize();
        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            return null;
        }
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int cmp = 0;
        K k1 = key;
        int h1 =hash(k1);
        Node<K, V> result = null;
        boolean seached = false;
        //添加节点
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) { //排除comparable等于0的情况，等于零进行扫描进行equals比较；
            } else if (seached) {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else {  //扫描后通过内存地址决定左右 searched为false进行扫描
                if ((node.left != null && (result = node(node.left, k1)) != null)
                        || (node.right != null && (result = node(node.right, k1)) != null)) {
                    node = result;
                    cmp = 0;
                } else {
                    seached = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                //node.hash=h1;
                return oldValue;
            }
        } while (node != null);
        Node<K, V> newNode = createNode(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        fixAfterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
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
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> root : table) {
            if (root == null) continue;
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(node.value, value)) return true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> root : table) {
            if (root == null) continue;
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) return;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    private void resize() {
        if (size / table.length <= DEFAULT_LOAD_FACTOR) return;
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null) continue;
            queue.offer(oldTable[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                //挪动代码带后面，不影响以前的左右节点遍历
                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        //重置
        newNode.parent = null;
        newNode.right = null;
        newNode.left = null;
        newNode.color=RED;
        int index = index(newNode);
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        //添加节点
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) { //排除comparable等于0的情况，等于零进行扫描进行equals比较；
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);
        newNode.parent=parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        newNode.parent=parent;
        fixAfterPut(newNode);
    }

    private void fixAfterRemove(Node<K, V> node) {
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
                    fixAfterRemove(parent);
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
                    fixAfterRemove(parent);
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

    private void fixAfterPut(Node<K, V> node) {
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
            fixAfterPut(grand);
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
            table[index(grand)] = parent;
        }
        if (child != null) {
            child.parent = grand;
        }
        grand.parent = parent;

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

    private int compare(K k1, K k2, int h1, int h2) {
        //比较hash值
        int result = h1 - h2;
        if (result != 0) return result;
        //比较equals
        if (Objects.equals(k1, k2)) return 0;
        //hash值相等，但不equals;
        //比较类名
        if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof Comparable) {
            //同一类型具备可比较性
            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }
        //同一类型，不具备可比较性
        //k1不为null,k2为null;
        //k1为null,k2不为null;
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    private int index(K key) {
        return hash(key) & (table.length - 1);
    }
    private  int hash(K key){
        if (key == null) return 0;
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16));
    }

    private int index(Node<K, V> node) {
        return  node.hash & (table.length - 1);
    }

    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 =hash(k1);
        Node<K, V> result = null;
        int cmp = 0;
        while (node != null) {
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null && (result = node(node.right, k1)) != null) {
                return result;
            } else {
                node = node.left;
            }
//            } else if (node.left != null && (result = node(node.left, k1)) != null) {
//                return result;
//            } else {
//                return null;
//            }
        }
        return null;
    }


    protected V remove(Node<K, V> node) {
        if (node == null) return null;
        Node<K,V> willNode = node;
        size--;
        V oldValue = node.value;
        if (node.hasTwoChildren()) {
            //找到后继节点
            Node<K, V> S = successor(node);
            node.key = S.key;
            node.value = S.value;
            node.hash = S.hash;
            //删除后继节点
            node = S;
        }
        //统一删除node(node度为1或者0)
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);
        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) { //node是度为1并且是根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            fixAfterRemove(replacement);

        } else if (node.parent == null) { //为叶子节点且是根节点
            table[index] = null;
            fixAfterRemove(node);
        } else { //为叶子结点不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            fixAfterRemove(node);
        }
        afterRemove(willNode,node);
        return oldValue;
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

    protected void afterRemove(Node<K,V> willNode,Node<K,V> removedNode){

    }
    protected  Node<K,V> createNode(K key, V value, Node<K, V> parent){
        return new Node<>(key, value, parent);
    }

    protected static class Node<K, V> {
        K key;
        V value;
        int hash;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
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

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }

    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【" + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                                    @Override
                                    public Object root() {
                                        return root;
                                    }

                                    @Override
                                    public Object left(Object node) {
                                        return ((Node<K, V>) node).left;
                                    }

                                    @Override
                                    public Object right(Object node) {
                                        return ((Node<K, V>) node).right;
                                    }

                                    @Override
                                    public Object string(Object node) {
                                        return node;
                                    }
                                }
            );
            System.out.println("---------------------------------------------------------------------");
        }
    }

}
