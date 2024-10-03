public class UnionFind {
    private int[] items;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        this.items = new int[N];
        for (int i = 0; i < this.items.length; i++) {
            this.items[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return parent(find(v)) * -1;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return this.items[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= this.items.length) {
            throw new IllegalArgumentException("An invalid item was passed in.");
        }
        if (parent(v) >= 0) {
            int root = find(parent(v));
            this.items[v] = root;
            return root;
        }
        return v;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (v1 != v2 && !connected(v1, v2)) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (sizeOf(root2) < sizeOf(root1)) {
                this.items[root1] += this.items[root2];
                this.items[root2] = root1;
            } else {
                this.items[root2] += this.items[root1];
                this.items[root1] = root2;
            }
        }
    }

}
