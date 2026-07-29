class Solution {
    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int half = n / 2;

        int[] freq = new int[26];

        for (int i = 0; i < half; i++) {
            freq[s.charAt(i) - 'a']++;
        }

        long rank = k;
        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < half; pos++) {
            boolean chosen = false;

            for (int c = 0; c < 26; c++) {
                if (freq[c] == 0) {
                    continue;
                }

                freq[c]--;

                int remaining = half - pos - 1;
                long ways = countPermutations(freq, remaining, rank);

                if (rank <= ways) {
                    left.append((char) ('a' + c));
                    chosen = true;
                    break;
                }

                rank -= ways;
                freq[c]++;
            }

            if (!chosen) {
                return "";
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(left);

        if ((n & 1) == 1) {
            ans.append(s.charAt(half));
        }

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private long countPermutations(int[] freq, int remaining, long limit) {
        long ways = 1;

        for (int c = 0; c < 26; c++) {
            int cnt = freq[c];

            if (cnt == 0) {
                continue;
            }

            long comb = combination(remaining, cnt, limit);

            if (ways > limit / comb) {
                return limit + 1;
            }

            ways *= comb;

            if (ways > limit) {
                return limit + 1;
            }

            remaining -= cnt;
        }

        return ways;
    }

    private long combination(int n, int r, long limit) {
        r = Math.min(r, n - r);

        long result = 1;

        for (int i = 1; i <= r; i++) {
            long numerator = n - i + 1;
            long denominator = i;

            long g = gcd(numerator, denominator);
            numerator /= g;
            denominator /= g;

            g = gcd(result, denominator);
            result /= g;
            denominator /= g;

            if (denominator != 1) {
                result /= denominator;
            }

            if (result > limit / numerator) {
                return limit + 1;
            }

            result *= numerator;
        }

        return result;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}