package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private int v;
    private HashMap<Integer, List<Integer>> al;

    public Graph() {
        this.v = 0;
        this.al = new HashMap<>();
    }

    public int V() {
        return this.v;
    }

    public void addEdge(int v, int w) {
        if (!hasVertex(v)) {
            addVertex(v);
        }
        if (!hasVertex(w)) {
            addVertex(w);
        }
        if (!hasEdge(v, w)) {
            al.get(v).add(w);
        }
    }

    public void addVertex(int v) {
        if (!al.containsKey(v)) {
            al.put(v, new ArrayList<>());
            this.v += 1;
        }
    }

    public boolean hasVertex(int v) {
        return al.containsKey(v);
    }

    public boolean hasEdge(int v, int w) {
        return al.get(v).contains(w);
    }

    public Iterable<Integer> adj(int v) {
        return al.get(v);
    }
}
