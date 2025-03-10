import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    WeightedQuickUnionUF topAndBottom;
    WeightedQuickUnionUF noBottom;
    int[][] sites;
    int topIndex;
    int bottomIndex;
    int items;
    int N;
    int opens;


    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.topIndex = N * N;
        this.bottomIndex = N * N + 1;
        this.topAndBottom = new WeightedQuickUnionUF(N * N + 2); // for virtual top and bottom
        this.noBottom = new WeightedQuickUnionUF(N * N + 1); // only virtual top
        this.sites = new int[N][N];

        // fill sites
        for (int i = 0; i < this.sites.length; i++) {
            for (int j = 0; j < this.sites[0].length; j++) {
                this.sites[i][j] = 0;
            }
        }

        // connect top row to virtual top
        for (int i = 0; i < N; i++) {
            this.topAndBottom.union(this.topIndex, i);
            this.noBottom.union(this.topIndex, i);
        }

        // connect bottom row to virtual bottom
        for (int i = N * N - N; i < N * N; i++) {
            this.topAndBottom.union(this.bottomIndex, i);
        }
    }

    public void open(int row, int col) {
        if (this.sites[row][col] == 0) {
            this.sites[row][col] = 1;
            this.opens += 1;

            // check adjacent FIX THIS CHECK ONLY 4
            int[] rowOffset = new int[]{-1, 0, 1, 0};
            int[] colOffset = new int[]{0, 1, 0, -1};
            for (int i = 0; i < 4; i++) {
                int nextRow = row + rowOffset[i];
                int nextCol = col + colOffset[i];

                if (isValid(nextRow, nextCol) && this.sites[nextRow][nextCol] == 1) {
                    this.topAndBottom.union(xyTo1D(row, col), xyTo1D(nextRow, nextCol));
                    this.noBottom.union(xyTo1D(row, col), xyTo1D(nextRow, nextCol));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return this.sites[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && this.noBottom.connected(this.N * this.N, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return this.opens;
    }

    public boolean percolates() {
        if (this.N != 1) {
            return this.topAndBottom.connected(topIndex, bottomIndex);
        }
        return isOpen(0, 0);
    }

    private int xyTo1D(int r, int c) {
        return this.N * r + c;
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < this.N && c >= 0 && c < this.N;
    }
}
