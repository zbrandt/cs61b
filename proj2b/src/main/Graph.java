package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private HashMap<Integer, List<Integer>> al;

    public Graph() {
        this.al = new HashMap<>();
    }

    public void addEdge(int v, int w) {
        addVertex(v);
        addVertex(w);
        if (!al.get(v).contains(w)) { // in case of duplicates in csv
            al.get(v).add(w);
        }
    }

    private void addVertex(int v) {
        if (!al.containsKey(v)) {
            al.put(v, new ArrayList<>());
        }
    }

    public List<Integer> adjacent(int v) {
        return al.get(v);
    }
}
