package 第二季.Union;

public class UnionFind_QU_R_PC extends UnionFind_QU_R {
    public UnionFind_QU_R_PC(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        if(parents[v]!=v){
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
