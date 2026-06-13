package com.simon.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 查询数组第k大的数字
 * Created by sang on 2019/6/19.
 */
public class KthLargest {

    /**
     * 使用快速选择算法查找第k大的数字
     * 时间复杂度：O(n) 平均
     */
    public int findKthLargest(int[] nums, int k) {
        // 第k大 = 排序后索引为 (length - k) 的元素
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }

        int pivotIndex = partition(nums, left, right);

        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left;

        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }

        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest();
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;

        System.out.println("数组: " + JSON.toJSONString(nums));
        System.out.println("第 " + k + " 大的数字: " + kthLargest.findKthLargest(nums, k));
        System.out.println("期望结果: 5");
    }

}
