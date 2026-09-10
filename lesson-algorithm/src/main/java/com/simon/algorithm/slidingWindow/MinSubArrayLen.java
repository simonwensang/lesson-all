package com.simon.algorithm.slidingWindow;

/**
 * 标题：长度最小的子数组
 *
 * 题目描述：
 * 给定一个含有 n 个正整数的数组和一个正整数 target。
 * 找出该数组中满足其和 >= target 的长度最小的连续子数组，并返回其长度。
 * 如果不存在符合条件的子数组，返回 0。
 */
public class MinSubArrayLen {

    public int minSubArrayLen(int target, int[] nums) {
        // left 表示当前滑动窗口的左边界。
        int left = 0;

        // sum 记录当前窗口 nums[left] 到 nums[right] 的元素和。
        long sum = 0;

        // minLength 记录已经找到的最短连续子数组长度。
        int minLength = Integer.MAX_VALUE;

        // right 表示当前滑动窗口的右边界，逐步向右扩大窗口。
        for (int right = 0; right < nums.length; right++) {
            // 将右边界元素加入窗口。
            sum += nums[right];

            // 当前窗口和已经满足条件时，尝试不断缩小左边界。
            while (sum >= target) {
                // 更新满足条件的最短长度。
                minLength = Math.min(minLength, right - left + 1);

                // 移除左边界元素，让窗口变短，继续寻找更短答案。
                sum -= nums[left];
                left++;
            }
        }

        // 如果没有找到满足条件的窗口，返回 0。
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    public static void main(String[] args) {
        MinSubArrayLen solution = new MinSubArrayLen();
        System.out.println(solution.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
        System.out.println(solution.minSubArrayLen(4, new int[]{1, 4, 4}));
        System.out.println(solution.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
    }
}
