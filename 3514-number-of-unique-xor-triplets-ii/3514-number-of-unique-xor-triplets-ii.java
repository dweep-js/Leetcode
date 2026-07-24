class Solution {
    public int uniqueXorTriplets(int[] nums) {
        boolean[] one = new boolean[2048];
        boolean[] two = new boolean[2048];
        boolean[] three = new boolean[2048];

        for (int v : nums) {
            one[v] = true;

            for (int x = 0; x < 2048; x++) {
                if (one[x]) {
                    two[x ^ v] = true;
                }
            }
        }

        for (int v : nums) {
            for (int x = 0; x < 2048; x++) {
                if (two[x]) {
                    three[x ^ v] = true;
                }
            }
        }

        int ans = 0;
        for (boolean b : three) {
            if (b) ans++;
        }

        return ans;
    }
}