
import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int cnt1 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') cnt1++;
        }

        List<int[]> blocks = new ArrayList<>();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == '0') {
                int left = i;
                while (i < n && s.charAt(i) == '0') i++;
                blocks.add(new int[]{left, i - 1, i - left});
            } else {
                i++;
            }
        }

        int m = blocks.size();
        List<Integer> ans = new ArrayList<>();

        if (m < 2) {
            for (int[] q : queries) {
                ans.add(cnt1);
            }
            return ans;
        }

        int[] blockLeft = new int[m];
        int[] blockRight = new int[m];
        int[] zeroBlocks = new int[m];
        for (int k = 0; k < m; k++) {
            blockLeft[k] = blocks.get(k)[0];
            blockRight[k] = blocks.get(k)[1];
            zeroBlocks[k] = blocks.get(k)[2];
        }

        int[] tmpSum = new int[m - 1];
        for (int k = 0; k < m - 1; k++) {
            tmpSum[k] = zeroBlocks[k] + zeroBlocks[k + 1];
        }

        int K = (int) (Math.log(m - 1) / Math.log(2)) + 2;
        int[][] st = new int[K][m - 1];
        for (int k = 0; k < m - 1; k++) st[0][k] = tmpSum[k];
        for (int k = 1; k < K; k++) {
            for (int j = 0; j + (1 << k) - 1 < m - 1; j++) {
                st[k][j] = Math.max(st[k - 1][j], st[k - 1][j + (1 << (k - 1))]);
            }
        }
        int[] logTable = new int[m];
        for (int k = 2; k < m; k++) logTable[k] = logTable[k / 2] + 1;

        for (int[] q : queries) {
            int l = q[0], r = q[1];
            int idx_i = lowerBound(blockRight, l);
            int idx_j = upperBound(blockLeft, r) - 1;

            if (idx_i >= m || idx_j < 0 || idx_i >= idx_j) {
                ans.add(cnt1);
                continue;
            }

            int z_i_prime = Math.min(blockRight[idx_i], r) - Math.max(blockLeft[idx_i], l) + 1;
            int z_j_prime = Math.min(blockRight[idx_j], r) - Math.max(blockLeft[idx_j], l) + 1;

            int bestGain = 0;
            if (idx_i + 1 == idx_j) {
                bestGain = z_i_prime + z_j_prime;
            } else {
                int val1 = z_i_prime + zeroBlocks[idx_i + 1];
                int val2 = zeroBlocks[idx_j - 1] + z_j_prime;
                int val3 = 0;
                if (idx_j - 2 >= idx_i + 1) {
                    int L = idx_i + 1;
                    int R = idx_j - 2;
                    int k = logTable[R - L + 1];
                    val3 = Math.max(st[k][L], st[k][R - (1 << k) + 1]);
                }
                bestGain = Math.max(val1, Math.max(val2, val3));
            }
            ans.add(cnt1 + bestGain);
        }
        return ans;
    }

    private int lowerBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private int upperBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
