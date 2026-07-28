class Solution {
    public String smallestPalindrome(String s) {
        int n = s.length();
        char[] a = s.toCharArray();

        Arrays.sort(a, 0, n / 2);

        for (int i = 0; i < n / 2; i++) {
            a[n - 1 - i] = a[i];
        }

        return new String(a);
    }
}