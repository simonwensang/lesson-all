package com.simon.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 两字符串相加
 * 
 * 题目：给定两个非负整数字符串 num1 和 num2，返回它们的和（字符串形式）
 * 
 * 注意：
 * - 不能使用 BigInteger 或直接将字符串转换为整数
 * - 字符串长度可能很大，需要模拟手工加法
 * 
 * 举例：
 * 输入: num1 = "123", num2 = "45"
 * 输出: "168"
 * 
 * 输入: num1 = "999", num2 = "1"
 * 输出: "1000"
 * 
 * Created by sang on 2019/6/19.
 */
public class AddStrings {

    /**
     * 方法1：从右到左逐位相加（推荐）
     * 
     * 算法思想：
     * 模拟手工加法，从右到左逐位相加，处理进位。
     * 
     * 举例：num1 = "123", num2 = "45"
     * 
     * 步骤演示：
     * 
     *     1 2 3
     *   +   4 5
     *   -------
     *     1 6 8
     * 
     * 详细过程：
     * 1. i=2, j=1: 3+5=8, carry=0, result="8"
     * 2. i=1, j=0: 2+4=6, carry=0, result="68"
     * 3. i=0, j=-1: 1+0=1, carry=0, result="168"
     * 4. 反转结果: "168"
     * 
     * 举例2：num1 = "999", num2 = "1"
     * 
     *     9 9 9
     *   +     1
     *   -------
     *   1 0 0 0
     * 
     * 详细过程：
     * 1. i=2, j=0: 9+1=10, carry=1, result="0"
     * 2. i=1, j=-1: 9+0+1=10, carry=1, result="00"
     * 3. i=0, j=-1: 9+0+1=10, carry=1, result="000"
     * 4. 循环结束，carry=1, result="0001"
     * 5. 反转结果: "1000"
     * 
     * 时间复杂度：O(max(m, n))，其中 m 和 n 是两个字符串的长度
     * 空间复杂度：O(max(m, n))，存储结果
     */
    public String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        
        int i = num1.length() - 1;  // num1 的指针，从右到左
        int j = num2.length() - 1;  // num2 的指针，从右到左
        int carry = 0;              // 进位
        
        // 从右到左逐位相加
        while (i >= 0 || j >= 0 || carry > 0) {
            // 获取当前位的数字（如果指针越界则为0）
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
            
            // 计算当前位的和
            int sum = digit1 + digit2 + carry;
            
            // 当前位的结果是 sum % 10
            result.append(sum % 10);
            
            // 更新进位
            carry = sum / 10;
            
            // 移动指针
            i--;
            j--;
        }
        
        // 结果是倒序的，需要反转
        return result.reverse().toString();
    }

    /**
     * 方法2：使用数组存储结果
     * 
     * 算法思想：
     * 先计算结果的最大长度，使用数组存储每一位，最后转换为字符串。
     * 
     * 时间复杂度：O(max(m, n))
     * 空间复杂度：O(max(m, n))
     */
    public String addStringsArray(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        
        // 结果的最大长度是 max(len1, len2) + 1（考虑进位）
        int maxLength = Math.max(num1.length(), num2.length()) + 1;
        int[] result = new int[maxLength];
        int index = maxLength - 1;
        
        while (i >= 0 || j >= 0 || carry > 0) {
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
            
            int sum = digit1 + digit2 + carry;
            result[index] = sum % 10;
            carry = sum / 10;
            
            i--;
            j--;
            index--;
        }
        
        // 转换为字符串，跳过前导0
        StringBuilder sb = new StringBuilder();
        int start = (result[0] == 0) ? 1 : 0;
        for (int k = start; k < result.length; k++) {
            sb.append(result[k]);
        }
        
        return sb.toString();
    }

    public static void main(String[] args) {
        AddStrings solution = new AddStrings();

        // 测试用例1：基本加法
        String num1_1 = "123";
        String num2_1 = "45";
        System.out.println("输入: " + num1_1 + " + " + num2_1);
        String result1 = solution.addStrings(num1_1, num2_1);
        System.out.println("结果: " + result1);
        System.out.println("期望: 168");
        System.out.println();

        // 测试用例2：进位
        String num1_2 = "999";
        String num2_2 = "1";
        System.out.println("输入: " + num1_2 + " + " + num2_2);
        String result2 = solution.addStrings(num1_2, num2_2);
        System.out.println("结果: " + result2);
        System.out.println("期望: 1000");
        System.out.println();

        // 测试用例3：长度不同
        String num1_3 = "9999";
        String num2_3 = "9999";
        System.out.println("输入: " + num1_3 + " + " + num2_3);
        String result3 = solution.addStrings(num1_3, num2_3);
        System.out.println("结果: " + result3);
        System.out.println("期望: 19998");
        System.out.println();

        // 测试用例4：大数相加
        String num1_4 = "9999999999";
        String num2_4 = "1";
        System.out.println("输入: " + num1_4 + " + " + num2_4);
        String result4 = solution.addStrings(num1_4, num2_4);
        System.out.println("结果: " + result4);
        System.out.println("期望: 10000000000");
        System.out.println();

        // 测试用例5：数组法验证
        String num1_5 = "123";
        String num2_5 = "45";
        System.out.println("输入: " + num1_5 + " + " + num2_5);
        String result5 = solution.addStringsArray(num1_5, num2_5);
        System.out.println("数组法结果: " + result5);
        System.out.println("期望: 168");
        System.out.println();

        // 测试用例6：多位进位
        String num1_6 = "599";
        String num2_6 = "1";
        System.out.println("输入: " + num1_6 + " + " + num2_6);
        String result6 = solution.addStrings(num1_6, num2_6);
        System.out.println("结果: " + result6);
        System.out.println("期望: 600");
    }

}
