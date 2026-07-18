class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";

        char[] t = new char[s.length() * 2 + 3];
        t[0] = '^';
        t[t.length - 1] = '$';

        for (int i = 0; i < s.length(); i++) {
            t[2 * i + 1] = '#';
            t[2 * i + 2] = s.charAt(i);
        }
        t[t.length - 2] = '#';

        int[] p = new int[t.length];
        int center = 0, right = 0;
        int maxLen = 0, centerIndex = 0;

        for (int i = 1; i < t.length - 1; i++) {
            int mirror = 2 * center - i;

            if (i < right) {
                p[i] = Math.min(right - i, p[mirror]);
            }

            while (t[i + p[i] + 1] == t[i - p[i] - 1]) {
                p[i]++;
            }

            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }

            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }
}