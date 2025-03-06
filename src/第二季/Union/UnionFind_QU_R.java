package 第二季.Union;


import java.util.Arrays;

@SuppressWarnings("uncheched")
public class UnionFind_QU_R extends UnionFind_QU {

    private int[] ranks;

    public UnionFind_QU_R(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        Arrays.fill(ranks, 1);
    }

    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        ;
        if (ranks[p1] < ranks[p2]) {
            parents[p1] = p2;
        } else if (ranks[p1] > ranks[p2]) {
            parents[p2] = p1;
        } else {
            parents[p1] = p2;
            ranks[p2]++;
        }

    }

}
