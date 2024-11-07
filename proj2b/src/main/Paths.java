package main;

import java.util.ArrayList;
import java.util.List;

public class Paths {
    private List<Integer> visited;
    private int[] edgeTo;

    public Paths(Graph G, int s) {
        this.visited = new ArrayList<>();
        this.edgeTo = new int[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        visited.add(v);
        for (int w : G.adj(v)) {
            if (!visited.contains(w)) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public List<Integer> descendants() {
        return this.visited;
    }

}
