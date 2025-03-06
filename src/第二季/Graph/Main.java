package 第二季.Graph;


import java.util.List;
import 第二季.Graph.Graph.PathInfo;

import java.util.Map;
import java.util.Set;

public class Main {
    static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {

        @Override
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }

        @Override
        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }

        @Override
        public Double zero() {
            return 0.0;
        }
    };

    public static void main(String[] args) {
        testMultiSP();
    }
    private static void testMultiSP() {
        Graph<Object,Double> graph = directedGraph(Data.NEGATIVE_WEIGHT1);
        Map<Object, Map<Object, PathInfo<Object,Double>>> sp = graph.shortPathDetil();
        if(sp==null) return;
        sp.forEach((Object from, Map<Object,PathInfo<Object,Double>> paths)->{
            System.out.println(from +"--------------------------");
            paths.forEach((Object v, PathInfo<Object,Double> path)->{
                System.out.println(v+"---"+ path);
            });
        });
    }

    private static void testSPDetil() {
        Graph<Object,Double> graph = directedGraph(Data.SP);
        Map<Object, PathInfo<Object,Double>> sp = graph.shortPathDetil("A");
        if(sp==null) return;
        sp.forEach((Object v, PathInfo<Object,Double> path)->{
            System.out.println(v+"---"+ path);
        });
    }
    private static void testSP() {
        Graph<Object,Double> graph = undirectedGraph(Data.SP);
        Map<Object,Double> sp = graph.shortPath("A");
        sp.forEach((Object v, Double path)->{
            System.out.println(v+"---"+ path);
        });
    }

    static  void testDfs(){
        Graph<Object,Double> graph = directedGraph(Data.DFS_02);
        graph.bfs("a",(Object v)->{
            System.out.println(v);
            return false;
        });
    }

    static  void testBfs(){
        Graph<Object,Double> graph = directedGraph(Data.BFS_02);
        graph.bfs(0,(Object v)->{
            System.out.println(v);
            return false;
        });
    }
    static  void testMst(){
        Graph<Object,Double> graph = undirectedGraph(Data.MST_01);
        Set<Graph.EdgeInfo<Object,Double>> infos = graph.mst();
        for (Graph.EdgeInfo<Object, Double> info : infos) {
            System.out.println(info);
        }
    }
    static  void testTopo(){
        Graph<Object,Double> graph = undirectedGraph(Data.TOPO);
        List<Object> list = graph.topLogicalSort();
        System.out.println(list);
    }



    static void test() {
//        ListGraph<String, Integer> graph = new ListGraph<>();
//        graph.addEdge("V1", "V0", 9);
//        graph.addEdge("V1", "V2", 3);
//        graph.addEdge("V2", "V0", 2);
//        graph.addEdge("V2", "V3", 5);
//        graph.addEdge("V3", "V4", 1);
//        graph.addEdge("V0", "V4", 6);
//        graph.removeVertex("V0");
//       graph.print();
        // graph.bfs("V1");
    }

    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length==2){
                graph.addEdge(edge[0],edge[1]);
            }else if(edge.length==3)
            {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0],edge[1],weight);
            }
        }
        return graph;
    }

    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length==2){
                graph.addEdge(edge[0],edge[1]);
                graph.addEdge(edge[1],edge[0]);
            }else if(edge.length==3)
            {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0],edge[1],weight);
                graph.addEdge(edge[1],edge[0],weight);
            }
        }
        return graph;
    }
}
