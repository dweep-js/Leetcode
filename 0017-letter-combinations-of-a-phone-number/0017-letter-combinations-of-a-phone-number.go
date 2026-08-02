func letterCombinations(digits string) []string {
	if len(digits) == 0 {
		return []string{}
	}

	mapping := map[byte]string{
		'2': "abc",
		'3': "def",
		'4': "ghi",
		'5': "jkl",
		'6': "mno",
		'7': "pqrs",
		'8': "tuv",
		'9': "wxyz",
	}

	var ans []string
	var path []byte

	var dfs func(int)
	dfs = func(idx int) {
		if idx == len(digits) {
			ans = append(ans, string(path))
			return
		}

		letters := mapping[digits[idx]]

		for i := 0; i < len(letters); i++ {
			path = append(path, letters[i])
			dfs(idx + 1)
			path = path[:len(path)-1]
		}
	}

	dfs(0)
	return ans
}