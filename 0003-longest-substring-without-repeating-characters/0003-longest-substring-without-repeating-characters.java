class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] lastIndex = new int[128]; // ASCII covers letters, digits, symbols, spaces
        Arrays.fill(lastIndex, -1);

        int maxLen = 0;
        int windowStart = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (lastIndex[c] >= windowStart) {
                windowStart = lastIndex[c] + 1;
            }
            lastIndex[c] = i;
            maxLen = Math.max(maxLen, i - windowStart + 1);
        }

        return maxLen;
    }
}