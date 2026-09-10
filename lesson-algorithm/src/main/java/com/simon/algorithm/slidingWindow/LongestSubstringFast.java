package com.simon.algorithm.slidingWindow;

import java.util.HashMap;

/**
 * 标题 求一个字符串的最长无重复字符子字符串长度 题目描述 例子
 * 源字符串：abcabcabc
 * 子字符串：abca, abc, ab, a
 * 无重复字符的子字符串：abc, ab, a
 * 无重复字符的最长子字符串：abc
 * 返回结果：3
 */

public class LongestSubstringFast {

    public static int lengthOfLongestSubstring(String s) {
        // 如果字符串为空，直接返回 0
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 创建哈希表，用于记录字符及其最后一次出现的位置
        HashMap<Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        int left = 0; // 滑动窗口的左边界

        // right 为滑动窗口的右边界
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // 如果当前字符在当前窗口中已经存在，将左边界跳跃到重复字符上一次出现位置的下一个位置
            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }

            // 无论是否重复，都更新或插入当前字符的最新位置
            map.put(c, right);

            // 计算并更新最大窗口长度
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }


    public static int lengthOfLongestSubstringFast(String s) {
        if (s == null || s.length() == 0) return 0;

        int[] lastPos = new int[128];
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // 如果字符出现过，且在当前窗口内
            if (lastPos[c] >= left) {
                left = lastPos[c] + 1;
            }

            lastPos[c] = right;
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        // 测试题目中的例子
        String testStr = "abcabcabc";
        int result = lengthOfLongestSubstringFast(testStr);
        System.out.println("源字符串: " + testStr);
        System.out.println("最长无重复字符子串长度: " + result); // 输出: 3
    }

}
