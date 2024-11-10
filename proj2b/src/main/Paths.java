package main;

import java.util.ArrayList;
import java.util.List;

public class Paths {
    private List<Integer> visited;

    public Paths(Graph g, int v) {
        this.visited = new ArrayList<>();
        dfs(g, v);
    }

    private void dfs(Graph g, int v) {
        visited.add(v);
        for (int w : g.adjacent(v)) {
            if (!visited.contains(w)) {
                dfs(g, w);
            }
        }
    }

    public List<Integer> descendants() {
        return this.visited;
    }

}
