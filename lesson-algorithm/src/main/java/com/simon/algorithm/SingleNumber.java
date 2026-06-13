package com.simon.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 数组中只出现一次的数字
 * 
 * 题目：给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。
 * 找出那个只出现了一次的元素。
 * 
 * 要求：
 * - 时间复杂度：O(n)
 * - 空间复杂度：O(1)
 * 
 * 举例：
 * 输入: [2, 2, 1]
 * 输出: 1
 * 
 * 输入: [4, 1, 2, 1, 2]
 * 输出: 4
 * 
 * Created by sang on 2019/6/19.
 */
public class SingleNumber {

    /**
     * 方法1：异或法（推荐）
     * 
     * 算法思想：
     * 利用异或运算的性质：
     * 1. a ^ a = 0（相同数字异或为0）
     * 2. a ^ 0 = a（任何数与0异或等于自身）
     * 3. 异或满足交换律和结合律：a ^ b ^ a = a ^ a ^ b = 0 ^ b = b
     * 
     * 因此，将所有数字异或，出现两次的数字会相互抵消为0，
     * 最终结果就是只出现一次的数字。
     * 
     * 举例：[4, 1, 2, 1, 2]
     * 
     * 步骤演示：
     * 4 ^ 1 ^ 2 ^ 1 ^ 2
     * = 4 ^ (1 ^ 1) ^ (2 ^ 2)  // 交换律
     * = 4 ^ 0 ^ 0              // 相同数字异或为0
     * = 4                      // 任何数与0异或等于自身
     * 
     * 时间复杂度：O(n) - 遍历一次数组
     * 空间复杂度：O(1) - 只使用一个变量
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        
        // 将所有数字异或
        for (int num : nums) {
            result ^= num;
        }
        
        return result;
    }

    /**
     * 方法2：哈希表法
     * 
     * 算法思想：
     * 使用哈希表统计每个数字出现的次数，返回出现次数为1的数字。
     * 
     * 举例：[4, 1, 2, 1, 2]
     * 
     * 步骤演示：
     * 1. 遍历数组，统计每个数字的出现次数
     *    map = {4: 1, 1: 2, 2: 2}
     * 2. 遍历哈希表，找到出现次数为1的数字
     *    返回 4
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(n) - 需要存储哈希表
     */
    public int singleNumberHashMap(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        
        // 统计每个数字出现的次数
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        
        // 找到出现次数为1的数字
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        
        return -1; // 理论上不会执行到这里
    }

    /**
     * 方法3：排序法
     * 
     * 算法思想：
     * 先对数组排序，然后遍历数组，相邻两个元素比较。
     * 如果相邻元素不相等，则前一个元素就是只出现一次的数字。
     * 
     * 举例：[4, 1, 2, 1, 2]
     * 
     * 步骤演示：
     * 1. 排序后: [1, 1, 2, 2, 4]
     * 2. 遍历比较:
     *    - nums[0]=1, nums[1]=1, 相等，继续
     *    - nums[2]=2, nums[3]=2, 相等，继续
     *    - nums[4]=4, 是最后一个元素，返回 4
     * 
     * 时间复杂度：O(n log n) - 排序的时间复杂度
     * 空间复杂度：O(1) 或 O(log n) - 取决于排序算法
     */
    public int singleNumberSort(int[] nums) {
        Arrays.sort(nums);
        
        // 遍历数组，步长为2
        for (int i = 0; i < nums.length; i += 2) {
            // 如果是最后一个元素，或者当前元素与下一个元素不相等
            if (i == nums.length - 1 || nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        
        return -1; // 理论上不会执行到这里
    }

    /**
     * 方法4：集合法
     * 
     * 算法思想：
     * 使用 Set 存储遍历过的数字。
     * 如果数字已经在 Set 中，说明它出现了两次，从 Set 中移除。
     * 最终 Set 中剩下的就是只出现一次的数字。
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int singleNumberSet(int[] nums) {
        java.util.Set<Integer> set = new java.util.HashSet<>();
        
        for (int num : nums) {
            if (!set.add(num)) {
                // add 返回 false 说明元素已存在，移除它
                set.remove(num);
            }
        }
        
        // Set 中剩下的就是只出现一次的数字
        return set.iterator().next();
    }

    /**
     * 扩展：数组中两个只出现一次的数字
     * 
     * 题目：给定一个整数数组，其中恰好有两个元素只出现一次，其余每个元素均出现两次。
     * 找出那两个只出现了一次的元素。
     * 
     * 算法思想：
     * 1. 将所有数字异或，得到两个只出现一次的数字的异或结果
     * 2. 找到异或结果中任意一个为1的位（说明这两个数字在该位不同）
     * 3. 根据该位将数组分为两组，每组分别异或，得到两个数字
     * 
     * 举例：[1, 2, 1, 3, 2, 5]
     * 
     * 步骤演示：
     * 1. 所有数字异或: 1^2^1^3^2^5 = 3^5 = 6 (二进制: 110)
     * 2. 找到最低位的1: 6 & (-6) = 2 (二进制: 010)
     * 3. 根据第1位分组:
     *    - 第1位为0: [1, 1, 5] -> 异或结果: 5
     *    - 第1位为1: [2, 3, 2] -> 异或结果: 3
     * 4. 返回 [3, 5]
     */
    public int[] singleNumberTwo(int[] nums) {
        // 1. 所有数字异或，得到两个只出现一次的数字的异或结果
        int xorAll = 0;
        for (int num : nums) {
            xorAll ^= num;
        }
        
        // 2. 找到异或结果中最低位的1
        // 技巧：x & (-x) 可以得到 x 的最低位的1
        int diffBit = xorAll & (-xorAll);
        
        // 3. 根据该位将数组分为两组，分别异或
        int num1 = 0;
        int num2 = 0;
        
        for (int num : nums) {
            if ((num & diffBit) == 0) {
                // 该位为0的数字
                num1 ^= num;
            } else {
                // 该位为1的数字
                num2 ^= num;
            }
        }
        
        return new int[]{num1, num2};
    }

    public static void main(String[] args) {
        SingleNumber solution = new SingleNumber();

        // 测试用例1：基本测试
        int[] nums1 = {2, 2, 1};
        System.out.println("输入: " + JSON.toJSONString(nums1));
        System.out.println("异或法结果: " + solution.singleNumber(nums1));
        System.out.println("期望: 1");
        System.out.println();

        // 测试用例2：多个重复
        int[] nums2 = {4, 1, 2, 1, 2};
        System.out.println("输入: " + JSON.toJSONString(nums2));
        System.out.println("异或法结果: " + solution.singleNumber(nums2));
        System.out.println("哈希表法结果: " + solution.singleNumberHashMap(nums2));
        System.out.println("排序法结果: " + solution.singleNumberSort(nums2.clone()));
        System.out.println("集合法结果: " + solution.singleNumberSet(nums2));
        System.out.println("期望: 4");
        System.out.println();

        // 测试用例3：单个元素
        int[] nums3 = {1};
        System.out.println("输入: " + JSON.toJSONString(nums3));
        System.out.println("异或法结果: " + solution.singleNumber(nums3));
        System.out.println("期望: 1");
        System.out.println();

        // 测试用例4：负数
        int[] nums4 = {-1, -1, 3};
        System.out.println("输入: " + JSON.toJSONString(nums4));
        System.out.println("异或法结果: " + solution.singleNumber(nums4));
        System.out.println("期望: 3");
        System.out.println();

        // 测试用例5：两个只出现一次的数字
        int[] nums5 = {1, 2, 1, 3, 2, 5};
        System.out.println("输入: " + JSON.toJSONString(nums5));
        int[] result5 = solution.singleNumberTwo(nums5);
        System.out.println("两个只出现一次的数字: " + JSON.toJSONString(result5));
        System.out.println("期望: [3, 5] 或 [5, 3]");
        System.out.println();

        // 测试用例6：大数组
        int[] nums6 = {1, 1, 2, 2, 3, 3, 4, 4, 5};
        System.out.println("输入: " + JSON.toJSONString(nums6));
        System.out.println("异或法结果: " + solution.singleNumber(nums6));
        System.out.println("期望: 5");
    }

}
