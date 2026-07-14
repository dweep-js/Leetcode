class Solution {
    static final int MOD = 1_000_000_007;
    static final int MAX = 200;

    public int subsequencePairCount(int[] nums) {
        int[][] gcd = new int[MAX + 1][MAX + 1];
        for (int i = 0; i <= MAX; i++) {
            for (int j = 0; j <= MAX; j++) {
                gcd[i][j] = (i == 0) ? j : calcGcd(i, j);
            }
        }

        int[][] dp = new int[MAX + 1][MAX + 1];
        dp[0][0] = 1;

        for (int x : nums) {
            int[][] ndp = new int[MAX + 1][MAX + 1];

            for (int g1 = 0; g1 <= MAX; g1++) {
                for (int g2 = 0; g2 <= MAX; g2++) {
                    int cur = dp[g1][g2];
                    if (cur == 0) continue;

                    ndp[g1][g2] = (ndp[g1][g2] + cur) % MOD;

                    int ng1 = gcd[g1][x];
                    ndp[ng1][g2] = (ndp[ng1][g2] + cur) % MOD;

                    int ng2 = gcd[g2][x];
                    ndp[g1][ng2] = (ndp[g1][ng2] + cur) % MOD;
                }
            }

            dp = ndp;
        }

        int ans = 0;
        for (int g = 1; g <= MAX; g++) {
            ans += dp[g][g];
            if (ans >= MOD) ans -= MOD;
        }
        return ans;
    }

    private static int calcGcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}