package com.Trie;

import com.哈希表.map.HashMap;

public class trie<V> {
    private int size;
    private Node<V> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return node != null && node.word;
    }

    public V add(String key, V value) {
        keyCheck(key);
        //创建根节点
        if (root == null) {
            root = new Node<>(null);
        }
        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char c = key.charAt(i);
            boolean emptyChildren = node.Children == null;
            Node<V> childNode = emptyChildren ? null : node.Children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.Children = emptyChildren ? new HashMap<>() : node.Children;
                node.Children.put(c, childNode);
            }
            node = childNode;
        }
        if (node.word) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String key) {
        Node<V> node = node(key);
        if (node == null || !node.word) return null;
        size--;
        V oldValue = node.value;
        //如果还有子节点
        if (node.Children != null && !node.Children.isEmpty()) {
            node.word = false;
            node.value = null;
            return oldValue;
        }
        //如果没有子节点
        Node<V> parent = null;
        while ((parent=node.parent)!=null){
            parent.Children.remove(node.character);
            if(parent.word || !parent.Children.isEmpty()) break;
            node=parent;
        }
        return oldValue;
    }

    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            if (node == null || node.Children == null || node.Children.isEmpty()) return null;
            char c = key.charAt(i);
            node = node.Children.get(c);
        }

        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    private static class Node<V> {
        Node<V> parent;
        HashMap<Character, Node<V>> Children;
        V value;
        Character character;
        Boolean word=false;

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }


}
