package 第二季.Graph;

import java.util.*;
import java.util.Map.Entry;

public class ListGraph<V, E> extends Graph<V, E> {
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    private Set<Edge<V, E>> edges = new HashSet<>();
    private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> weightManager.compare(e1.weight, e2.weight);

    public ListGraph() {
    }

    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

    public void print() {
        System.out.println("----------【顶点】-----------------");
        vertices.forEach((V v, Vertex<V, E> veVertex) -> {
            System.out.println(v);
            System.out.println("out------------------------------------------");
            System.out.println(veVertex.outEdges);
            System.out.println("in--------------------------------------------");
            System.out.println(veVertex.inEdges);
        });
        System.out.println("------------【边】-----------------------");
        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });
    }

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v, new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from, fromVertex);
        }
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeVertex(V v) {
        Vertex<V, E> vertex = vertices.remove(v);
        if (vertex == null) return;
        for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, E> edge = iterator.next();
            edge.to.inEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }
        for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, E> edge = iterator.next();
            edge.from.outEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) return;
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);
        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            if (visitor.visit(vertex.value)) return;
            for (Edge<V, E> edge : vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue;
                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            }
        }
    }

    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;
        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        Stack<Vertex<V, E>> stack = new Stack<>();

        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        if (visitor.visit(begin)) return;
        while (!stack.isEmpty()) {
            Vertex<V, E> vertex = stack.pop();
            for (Edge<V, E> edge : vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue;
                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                if (visitor.visit(edge.to.value)) return;
                break;
            }
        }
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return Math.random() > 0.5 ? prim() : Kruskal();
    }

    @Override
    public Map<V, E> shortPath(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;
        Map<V, E> selectedPaths = new HashMap<>();
        Map<Vertex<V, E>, E> paths = new HashMap<>();
        //初始化path
        for (Edge<V, E> edge : beginVertex.outEdges) {
            paths.put(edge.to, edge.weight);
        }
        while (!paths.isEmpty()) {
            Entry<Vertex<V, E>, E> minEntry = getMinPath(paths);
            //minvertex离开桌面
            Vertex<V, E> minVertex = minEntry.getKey();
            E minValue = minEntry.getValue();
            selectedPaths.put(minVertex.value, minEntry.getValue());
            paths.remove(minVertex);
            for (Edge<V, E> edge : minVertex.outEdges) {
                //如果已经离开桌面和起点没必要进行松弛操作
                if (selectedPaths.containsKey(edge.to.value)) continue;
                //对minvertex的出边进行松弛操作；
                relax(edge, minValue, paths);
            }
        }
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    @Override
    public Map<V, PathInfo<V, E>> shortPathDetil(V begin) {
        return dijkstra(begin);
    }

    /**
     * floyd
     *
     * @return
     */
    @Override
    public Map<V, Map<V, PathInfo<V, E>>> shortPathDetil() {
        Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
        //初始化
        for (Edge<V, E> edge : edges) {
            Map<V, PathInfo<V, E>> map = paths.get(edge.from.value);
            if (map == null) {
                map = new HashMap<>();
                paths.put(edge.from.value, map);
            }
            PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.info());
            map.put(edge.to.value, pathInfo);
        }
        vertices.forEach((V v2, Vertex<V, E> vertex2) -> {
            vertices.forEach((V v1, Vertex<V, E> vertex1) -> {
                vertices.forEach((V v3, Vertex<V, E> vertex3) -> {
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;
                    PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
                    if (path12 == null) return;
                    PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
                    if (path23 == null) return;
                    PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);

                    E newWeight = weightManager.add(path12.weight, path23.weight);
                    if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;
                    if (path13 == null) {
                        path13 = new PathInfo<>();
                        paths.get(v1).put(v3, path13);
                    } else {
                        path13.edgeInfos.clear();
                    }
                    path13.weight = newWeight;
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);
                });
            });
        });
        return paths;
    }

    private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> map = paths.get(from);
        return map == null ? null : map.get(to);
    }

    private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        HashMap<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));
        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) {
            for (Edge<V, E> edge : edges) {
                PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
                if (fromPath == null) continue;
                relaxbellmanFord(edge, fromPath, selectedPaths);
            }
        }
        for (Edge<V, E> edge : edges) {
            PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
            if (relaxbellmanFord(edge, fromPath, selectedPaths)) {
                System.out.println("有负权环");
                return null;
            }
        }
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    private boolean relaxbellmanFord(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
        //进行新的路径和老的路径比较，注意不存在的路径直接覆盖
        E newWeigh = weightManager.add(fromPath.weight, edge.weight);
        PathInfo<V, E> oldPath = paths.get(edge.to.value);
        if (oldPath != null && weightManager.compare(newWeigh, oldPath.weight) >= 0) return false;
        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to.value, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeigh;
        //将离开桌面的顶点和当前连起来重新覆盖原来的
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
        return true;
    }


    private Map<V, PathInfo<V, E>> dijkstra(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        Map<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();
        //初始化path
//        for (Edge<V, E> edge : beginVertex.outEdges) {
//            PathInfo<V, E> path = new PathInfo<>();
//            path.weight = edge.weight;
//            path.edgeInfos.add(edge.info());
//            paths.put(edge.to, path);
//        }
        paths.put(beginVertex, new PathInfo<>(weightManager.zero()));
        while (!paths.isEmpty()) {
            Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPathDetil(paths);
            PathInfo<V, E> minPath = minEntry.getValue();
            //minvertex离开桌面
            Vertex<V, E> minVertex = minEntry.getKey();
            selectedPaths.put(minVertex.value, minEntry.getValue());
            paths.remove(minVertex);
            for (Edge<V, E> edge : minVertex.outEdges) {
                //如果已经离开桌面和起点没必要进行松弛操作
                if (selectedPaths.containsKey(edge.to.value)) continue;
                //对minvertex的出边进行松弛操作；
                relaxdijkstra(edge, minPath, paths);
            }
        }
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    /**
     * 计算最短权值并且记录顶点之间的路线
     *
     * @param edge     最短边的出边遍历边
     * @param fromPath 最短路径的顶点信息
     * @param paths    存放所有顶点和边的信息(没有离开桌面的点)
     */
    private void relaxdijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        //进行新的路径和老的路径比较，注意不存在的路径直接覆盖
        E newWeigh = weightManager.add(fromPath.weight, edge.weight);
        PathInfo<V, E> oldPath = paths.get(edge.to);
        if (oldPath != null && weightManager.compare(newWeigh, oldPath.weight) >= 0) return;
        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeigh;
        //将离开桌面的顶点和当前连起来重新覆盖原来的
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
    }

    /**
     * 简单计算路径的权值
     *
     * @param edge           ：对最短边的遍历
     * @param fromPathValue： 最短边的权值
     * @param paths          ： 所有路径存放的映射(没有离开桌面的点)
     */
    private void relax(Edge<V, E> edge, E fromPathValue, Map<Vertex<V, E>, E> paths) {
        //进行新的路径和老的路径比较，注意不存在的路径直接覆盖
        E newWeigh = weightManager.add(fromPathValue, edge.weight);
        E oldWeigh = paths.get(edge.to);
        if (oldWeigh == null || weightManager.compare(newWeigh, oldWeigh) < 0) {
            paths.put(edge.to, newWeigh);
        }
    }

    private Entry<Vertex<V, E>, PathInfo<V, E>> getMinPathDetil(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        Iterator<Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
        Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = it.next();
        while (it.hasNext()) {
            Entry<Vertex<V, E>, PathInfo<V, E>> entry = it.next();
            if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    /**
     * 从Paths中选出最短路径
     *
     * @param paths
     * @return
     */
    private Entry<Vertex<V, E>, E> getMinPath(Map<Vertex<V, E>, E> paths) {
        Iterator<Entry<Vertex<V, E>, E>> it = paths.entrySet().iterator();
        Entry<Vertex<V, E>, E> minEntry = it.next();
        while (it.hasNext()) {
            Entry<Vertex<V, E>, E> entry = it.next();
            if (weightManager.compare(entry.getValue(), minEntry.getValue()) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    private Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V, E>> it = vertices.values().iterator();
        if (!it.hasNext()) return null;
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        Set<Vertex<V, E>> addedVertices = new HashSet<>();

        Vertex<V, E> vertex = it.next();
        addedVertices.add(vertex);
        MinHeap<Edge<V, E>> heap = new MinHeap<>(vertex.outEdges, edgeComparator);
        int edgeSize = vertices.size() - 1;
        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V, E> edge = heap.remove();
            if (addedVertices.contains(edge.to)) continue;

            edgeInfos.add(edge.info());
            addedVertices.add(edge.to);
            heap.addAll(edge.to.outEdges);
        }
        return edgeInfos;

    }

    private Set<EdgeInfo<V, E>> Kruskal() {
        int edgeSize = vertices.size() - 1;
        if (edgeSize == -1) return null;
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);
        UnionFind<Vertex<V, E>> uf = new UnionFind<>();
        vertices.forEach((V v, Vertex<V, E> vertex) -> uf.makeSet(vertex));
        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V, E> edge = heap.remove();
            if (uf.isSame(edge.from, edge.to)) continue;
            edgeInfos.add(edge.info());
            uf.union(edge.from, edge.to);
        }
        return edgeInfos;
    }


    @Override
    public List<V> topLogicalSort() {
        List<V> list = new ArrayList<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        Map<Vertex<V, E>, Integer> ins = new HashMap<>();
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            int in = vertex.inEdges.size();
            if (in == 0) {
                queue.offer(vertex);
            } else {
                ins.put(vertex, in);
            }
        });

        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            list.add(vertex.value);
            for (Edge<V, E> edge : vertex.outEdges) {
                int toIn = ins.get(edge.to) - 1;
                if (toIn == 0) {
                    queue.offer(edge.to);
                } else {
                    ins.put(edge.to, toIn);
                }
            }
        }
        return list;

    }

//    @Override
//    public void bfs(V begin) {
//        Vertex<V, E> beginVertex = vertices.get(begin);
//        if (beginVertex == null) return;
//
//        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
//        Queue<Vertex<V, E>> queue = new LinkedList<>();
//        queue.offer(beginVertex);
//        visitedVertices.add(beginVertex);
//        while (!queue.isEmpty()) {
//            Vertex<V, E> vertex = queue.poll();
//            System.out.println(vertex);
//            for (Edge<V, E> edge : vertex.outEdges) {
//                if (visitedVertices.contains(edge.to)) continue;
//                queue.offer(edge.to);
//                visitedVertices.add(edge.to);
//            }
//        }
//    }
//
//    @Override
//    public void dfs(V begin) {
//        Vertex<V, E> beginVertex = vertices.get(begin);
//        if (beginVertex == null) return;
//        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
//        Stack<Vertex<V, E>> stack = new Stack<>();
//
//        stack.push(beginVertex);
//        visitedVertices.add(beginVertex);
//        System.out.println(beginVertex.value);
//
//        while (!stack.isEmpty()) {
//            Vertex<V, E> vertex = stack.pop();
//            for (Edge<V, E> edge : vertex.outEdges) {
//                if (visitedVertices.contains(edge.to)) continue;
//                stack.push(edge.from);
//                stack.push(edge.to);
//                visitedVertices.add(edge.to);
//                System.out.println(edge.to.value);
//                break;
//            }
//        }
//
//    }
//
//    @Override
//    public void dfs1(V begin) {
//        Vertex<V, E> beginVertex = vertices.get(begin);
//        if (beginVertex == null) return;
//        dfs(beginVertex, new HashSet<>());
//    }
//
//    private void dfs(Vertex<V, E> Vertex, Set<Vertex<V, E>> visitedVertices) {
//        System.out.println(Vertex.value);
//        visitedVertices.add(Vertex);
//        for (Edge<V, E> edge : Vertex.outEdges) {
//            if (visitedVertices.contains(edge.to)) continue;
//            dfs(edge.to, visitedVertices);
//        }
//
//    }


    private static class Vertex<V, E> {
        V value;
        Set<Edge<V, E>> inEdges = new HashSet<>();
        Set<Edge<V, E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex<V, E>) obj).value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    private static class Edge<V, E> {
        Vertex<V, E> from;
        Vertex<V, E> to;
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        public EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V, E> edge = (Edge<V, E>) obj;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

    }

}
