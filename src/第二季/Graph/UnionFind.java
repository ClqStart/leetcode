package 第二季.Graph;

import com.哈希表.map.HashMap;
import com.哈希表.map.Map;

import java.util.Objects;

public class UnionFind<V> {
    private Map<V, Node<V>> nodes = new HashMap<>();

    public void makeSet(V v) {
        if (nodes.containsKey(v)) return;
        nodes.put(v, new Node<>(v));
    }

    public V find(V v) {
        Node<V> node = findNode(v);
        return node == null ? null : node.value;
    }

    private Node<V> findNode(V v) {
        Node<V> node = nodes.get(v);
        if (node == null) return null;
        while (!Objects.equals(node.value, node.parend.value)) {
            node.parend = node.parend.parend;
            node = node.parend;
        }
        return node;
    }

    public void union(V v1, V v2) {
        Node<V> p1 = findNode(v1);
        Node<V> p2 = findNode(v2);
        if (p1 == null || p2 == null) return;
        if (Objects.equals(p1.value, p2.value)) return;
        if (p1.rank < p2.rank) {
            p1.parend = p2;
        } else if (p1.rank > p2.rank) {
            p2.parend = p1;
        } else {
            p1.parend = p2;
            p2.rank += 1;
        }
    }

    public boolean isSame(V v1, V v2) {
        return Objects.equals(find(v1), find(v2));
    }


    private static class Node<V> {
        V value;
        Node<V> parend = this;
        int rank = 1;

        public Node(V value) {
            this.value = value;
        }
    }
}
