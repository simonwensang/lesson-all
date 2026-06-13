package com.simon.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 电话号码字母组合
 * 
 * 题目：给定一个仅包含数字 2-9 的字符串，返回所有可能的字母组合
 * 
 * 电话按键映射：
 * 2 -> abc
 * 3 -> def
 * 4 -> ghi
 * 5 -> jkl
 * 6 -> mno
 * 7 -> pqrs
 * 8 -> tuv
 * 9 -> wxyz
 * 
 * 举例：输入 "23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 * 
 * Created by sang on 2019/6/19.
 */
public class LetterCombinations {

    // 电话按键映射
    private static final String[] KEYPAD = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
    };

    /**
     * 方法1：回溯法（推荐）
     * 
     * 算法思想：
     * 使用回溯算法，逐个处理每个数字，尝试该数字对应的所有字母。
     * 当处理完所有数字后，将当前组合加入结果集。
     * 
     * 举例：输入 "23"
     * 
     * 递归树：
     * 
     *                    ""
     *                  /  |  \
     *                 a   b   c
     *                /|\ /|\ /|\
     *               d e f d e f d e f
     * 
     * 结果：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
     * 
     * 时间复杂度：O(3^m × 4^n)，其中 m 是对应 3 个字母的数字个数，n 是对应 4 个字母的数字个数
     * 空间复杂度：O(m + n)，递归栈深度
     */
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        
        // 边界条件处理
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        
        // 开始回溯
        backtrack(digits, 0, new StringBuilder(), result);
        
        return result;
    }

    /**
     * 回溯函数
     * @param digits 输入的数字字符串
     * @param index 当前处理到第几个数字
     * @param current 当前已构建的字母组合
     * @param result 结果集
     */
    private void backtrack(String digits, int index, StringBuilder current, List<String> result) {
        // 递归终止条件：已处理完所有数字
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // 获取当前数字对应的字母
        int digit = digits.charAt(index) - '0';
        String letters = KEYPAD[digit];

        // 尝试当前数字对应的每个字母
        for (char letter : letters.toCharArray()) {
            // 选择：将当前字母加入组合
            current.append(letter);
            
            // 递归：处理下一个数字
            backtrack(digits, index + 1, current, result);
            
            // 撤销选择：回溯，移除刚才加入的字母
            current.deleteCharAt(current.length() - 1);
        }
    }

    /**
     * 方法2：迭代法
     * 
     * 算法思想：
     * 从空字符串开始，逐个处理每个数字。
     * 对于每个数字，将当前结果集中的每个字符串与该数字对应的每个字母组合。
     * 
     * 举例：输入 "23"
     * 
     * 步骤演示：
     * 1. 初始：result = [""]
     * 2. 处理数字 2（对应 "abc"）：
     *    result = ["a", "b", "c"]
     * 3. 处理数字 3（对应 "def"）：
     *    result = ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
     * 
     * 时间复杂度：O(3^m × 4^n)
     * 空间复杂度：O(3^m × 4^n)，存储所有结果
     */
    public List<String> letterCombinationsIterative(String digits) {
        List<String> result = new ArrayList<>();
        
        // 边界条件处理
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        
        // 初始化为空字符串
        result.add("");

        // 逐个处理每个数字
        for (int i = 0; i < digits.length(); i++) {
            int digit = digits.charAt(i) - '0';
            String letters = KEYPAD[digit];
            
            // 保存当前结果集的大小
            int size = result.size();
            
            // 清空结果集，准备存储新的组合
            result.clear();
            
            // 将之前的每个字符串与当前数字的每个字母组合
            for (int j = 0; j < size; j++) {
                String prev = result.isEmpty() ? "" : result.get(j);
                for (char letter : letters.toCharArray()) {
                    result.add(prev + letter);
                }
            }
        }
        
        return result;
    }

    /**
     * 方法3：迭代法（优化版，使用临时列表）
     * 
     * 算法思想：
     * 使用临时列表存储新组合，避免清空原列表导致的问题。
     * 
     * 举例：输入 "23"
     * 
     * 步骤演示：
     * 1. 初始：result = [""]
     * 2. 处理数字 2（对应 "abc"）：
     *    temp = ["a", "b", "c"]
     *    result = temp
     * 3. 处理数字 3（对应 "def"）：
     *    temp = ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
     *    result = temp
     * 
     * 时间复杂度：O(3^m × 4^n)
     * 空间复杂度：O(3^m × 4^n)
     */
    public List<String> letterCombinationsIterativeOptimized(String digits) {
        List<String> result = new ArrayList<>();
        
        // 边界条件处理
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        
        // 初始化为空字符串
        result.add("");

        // 逐个处理每个数字
        for (int i = 0; i < digits.length(); i++) {
            int digit = digits.charAt(i) - '0';
            String letters = KEYPAD[digit];
            
            // 使用临时列表存储新组合
            List<String> temp = new ArrayList<>();
            
            // 将之前的每个字符串与当前数字的每个字母组合
            for (String prev : result) {
                for (char letter : letters.toCharArray()) {
                    temp.add(prev + letter);
                }
            }
            
            // 更新结果集
            result = temp;
        }
        
        return result;
    }

    public static void main(String[] args) {
        LetterCombinations solution = new LetterCombinations();

        // 测试用例1：输入 "23"
        String digits1 = "23";
        System.out.println("输入: " + digits1);
        List<String> result1 = solution.letterCombinations(digits1);
        System.out.println("回溯法结果: " + JSON.toJSONString(result1));
        System.out.println("期望结果: [\"ad\",\"ae\",\"af\",\"bd\",\"be\",\"bf\",\"cd\",\"ce\",\"cf\"]");
        System.out.println();

        // 测试用例2：输入 "2"
        String digits2 = "2";
        System.out.println("输入: " + digits2);
        List<String> result2 = solution.letterCombinations(digits2);
        System.out.println("回溯法结果: " + JSON.toJSONString(result2));
        System.out.println("期望结果: [\"a\",\"b\",\"c\"]");
        System.out.println();

        // 测试用例3：输入 "234"
        String digits3 = "234";
        System.out.println("输入: " + digits3);
        List<String> result3 = solution.letterCombinations(digits3);
        System.out.println("回溯法结果: " + JSON.toJSONString(result3));
        System.out.println("结果数量: " + result3.size() + "（3×3×3=27）");
        System.out.println();

        // 测试用例4：输入 "7"（对应4个字母）
        String digits4 = "7";
        System.out.println("输入: " + digits4);
        List<String> result4 = solution.letterCombinations(digits4);
        System.out.println("回溯法结果: " + JSON.toJSONString(result4));
        System.out.println("期望结果: [\"p\",\"q\",\"r\",\"s\"]");
        System.out.println();

        // 测试用例5：迭代法验证
        String digits5 = "23";
        System.out.println("输入: " + digits5);
        List<String> result5 = solution.letterCombinationsIterative(digits5);
        System.out.println("迭代法结果: " + JSON.toJSONString(result5));
        System.out.println();

        // 测试用例6：空字符串
        String digits6 = "";
        System.out.println("输入: \"\"");
        List<String> result6 = solution.letterCombinations(digits6);
        System.out.println("结果: " + JSON.toJSONString(result6));
        System.out.println("期望结果: []");
    }

}
