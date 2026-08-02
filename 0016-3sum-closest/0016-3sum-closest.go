func threeSumClosest(nums []int, target int) int {
	sort.Ints(nums)

	ans := nums[0] + nums[1] + nums[2]

	for i := 0; i < len(nums)-2; i++ {
		left, right := i+1, len(nums)-1

		for left < right {
			sum := nums[i] + nums[left] + nums[right]

			if abs(sum-target) < abs(ans-target) {
				ans = sum
			}

			if sum < target {
				left++
			} else if sum > target {
				right--
			} else {
				return target
			}
		}
	}

	return ans
}

func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}