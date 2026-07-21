class Solution {
    public String convert(String s, int numRows) {
        int n = s.length();
        if (numRows == 1 || numRows >= n) return s;

        char[] chars = s.toCharArray();
        StringBuilder ans = new StringBuilder(n);
        int cycle = (numRows - 1) << 1;

        for (int row = 0; row < numRows; row++) {
            for (int i = row; i < n; i += cycle) {
                ans.append(chars[i]);
                int d = i + cycle - (row << 1);
                if (row != 0 && row != numRows - 1 && d < n)
                    ans.append(chars[d]);
            }
        }

        return ans.toString();
    }
}