import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        Integer[] orderObj = new Integer[n];
        for (int i = 0; i < n; i++) orderObj[i] = i;
        Arrays.sort(orderObj, (a, b) -> Integer.compare(nums[a], nums[b]));

        int[] vals = new int[n];
        int[] pos = new int[n];
        for (int p = 0; p < n; p++) {
            int idx = orderObj[p];
            vals[p] = nums[idx];
            pos[idx] = p;
        }

        int[] hi = new int[n];
        int r = 0;
        for (int p = 0; p < n; p++) {
            if (r < p) r = p;
            while (r + 1 < n && vals[r + 1] - vals[p] <= maxDiff) r++;
            hi[p] = r;
        }

        int LOG = Math.max(1, 32 - Integer.numberOfLeadingZeros(Math.max(n, 1)));
        int[][] up = new int[LOG][n];
        up[0] = hi.clone();
        for (int k = 1; k < LOG; k++) {
            int[] prev = up[k - 1];
            int[] cur = new int[n];
            for (int i = 0; i < n; i++) {
                cur[i] = prev[prev[i]];
            }
            up[k] = cur;
        }

        int q = queries.length;
        int[] ans = new int[q];
        for (int i = 0; i < q; i++) {
            int u = queries[i][0], v = queries[i][1];
            if (u == v) {
                ans[i] = 0;
                continue;
            }
            int pu = pos[u], pv = pos[v];
            if (pu > pv) {
                int tmp = pu; pu = pv; pv = tmp;
            }
            ans[i] = dist(up, LOG, pu, pv);
        }
        return ans;
    }

    private int dist(int[][] up, int LOG, int pu, int pv) {
        int cur = pu, steps = 0;
        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][cur] < pv) {
                cur = up[k][cur];
                steps += (1 << k);
            }
        }
        int nxt = up[0][cur];
        return (nxt >= pv) ? steps + 1 : -1;
    }
}