package UnionFind;

public class UnionFind {

    private int[] parent; //parent array

    public UnionFind(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) {       // At the start, every item is its own parent
            parent[i] = i;
        }
    }

    public int find(int x) {
        while (parent[x] != x) {        // Keep going up until we find the root
            x = parent[x];
        }

        return x;
    }

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {       // If they are not already connected, connect them
            parent[rootB] = rootA;
        }
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }
}
