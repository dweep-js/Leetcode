class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        int[] size = new int[n];
        int[] edgeCount = new int[n]; // edges within each component's root

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        for (int[] edge : edges) {
            union(parent, size, edgeCount, edge[0], edge[1]);
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            if (find(parent, i) == i) {
                int v = size[i];
                int e = edgeCount[i];
                // complete graph on v vertices has v*(v-1)/2 edges
                if (e == v * (v - 1) / 2) {
                    result++;
                }
            }
        }
        return result;
    }

    private int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]; // path compression
            x = parent[x];
        }
        return x;
    }

    private void union(int[] parent, int[] size, int[] edgeCount, int a, int b) {
        int ra = find(parent, a);
        int rb = find(parent, b);
        if (ra == rb) {
            edgeCount[ra]++; // edge within the same component
            return;
        }
        if (size[ra] < size[rb]) {
            int tmp = ra; ra = rb; rb = tmp;
        }
        parent[rb] = ra;
        size[ra] += size[rb];
        edgeCount[ra] += edgeCount[rb] + 1;
    }
}