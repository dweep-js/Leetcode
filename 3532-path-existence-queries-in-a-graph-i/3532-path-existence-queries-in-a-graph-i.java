class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        final int[] comp = new int[n];
        int id = 0;

        for (int i = 1; i < n; ++i) {
            if (nums[i] - nums[i - 1] > maxDiff)
                ++id;
            comp[i] = id;
        }

        final int m = queries.length;
        final boolean[] ans = new boolean[m];

        for (int i = 0; i < m; ++i) {
            ans[i] = comp[queries[i][0]] == comp[queries[i][1]];
        }

        return ans;
    }
}