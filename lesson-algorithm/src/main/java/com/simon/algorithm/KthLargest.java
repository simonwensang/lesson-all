package com.simon.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 输出数组第k大的数字
 * Created by sang on 2019/6/19.
 */
public class KthLargest {

    /**
     * 方法1：排序法
     * 时间复杂度：O(n log n)
     * 空间复杂度：O(1)
     */
    public int findKthLargestBySort(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 方法2：最小堆法
     * 
     * 算法思想：
     * 维护一个大小为 k 的最小堆，堆顶始终是当前堆中最小的元素。
     * 遍历数组时，只保留最大的 k 个元素在堆中。
     * 遍历结束后，堆顶元素就是第 k 大的数。
     * 
     * 为什么用最小堆？
     * - 最小堆的堆顶是堆中最小的元素
     * - 当堆满 k 个元素时，如果新元素 > 堆顶，说明新元素应该进入前 k 大
     * - 此时弹出堆顶（最小的），加入新元素，保持堆大小为 k
     * 
     * 举例：数组 [3, 2, 1, 5, 6, 4]，找第 2 大的数（k=2）
     * 
     * 步骤演示：
     * 1. 处理 3: 堆 [3]
     * 2. 处理 2: 堆 [2, 3]（堆满 k=2 个元素）
     * 3. 处理 1: 1 < 堆顶 2，不加入
     * 4. 处理 5: 5 > 堆顶 2，弹出 2，加入 5 → 堆 [3, 5]
     * 5. 处理 6: 6 > 堆顶 3，弹出 3，加入 6 → 堆 [5, 6]
     * 6. 处理 4: 4 < 堆顶 5，不加入
     * 
     * 最终堆顶是 5，即第 2 大的数
     * 
     * 时间复杂度：O(n log k) - 遍历 n 个元素，每次堆操作 O(log k)
     * 空间复杂度：O(k) - 堆最多存储 k 个元素
     */
    public int findKthLargestByHeap(int[] nums, int k) {
        // 创建最小堆，初始容量为 k
        // PriorityQueue 默认是最小堆，堆顶是最小元素
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        
        for (int num : nums) {
            if (minHeap.size() < k) {
                // 堆还没满，直接加入元素
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                // 堆已满，且当前元素 > 堆顶（堆中最小的）
                // 说明当前元素应该进入前 k 大，弹出堆顶，加入当前元素
                minHeap.poll();      // 弹出堆顶（最小的元素）
                minHeap.offer(num);  // 加入当前元素
            }
            // 如果 num <= 堆顶，说明 num 不在前 k 大，直接忽略
        }
        
        // 遍历结束后，堆中保存的是最大的 k 个元素
        // 堆顶是这 k 个元素中最小的，即第 k 大的数
        return minHeap.peek();
    }

    /**
     * 方法3：快速选择算法（QuickSelect）
     * 
     * 算法思想：
     * 快速选择算法是快速排序的变种。快速排序需要对左右两边都递归排序，
     * 而快速选择只需要递归处理包含目标元素的那一边，因此平均时间复杂度为 O(n)。
     * 
     * 核心思路：
     * 1. 选择一个基准值（pivot），将数组分为两部分：左边 <= pivot，右边 > pivot
     * 2. 如果 pivot 的位置正好是第 k 大的位置，返回 pivot
     * 3. 如果 k 在 pivot 左边，递归处理左边
     * 4. 如果 k 在 pivot 右边，递归处理右边
     * 
     * 举例：数组 [3, 2, 1, 5, 6, 4]，找第 2 大的数
     * - 第 2 大 = 排序后索引为 4 的元素（6-2=4）
     * - 排序后：[1, 2, 3, 4, 5, 6]，索引 4 的值是 5
     * 
     * 时间复杂度：
     * - 平均：O(n) - 每次递归只处理一半数据
     * - 最坏：O(n²) - 每次 pivot 都选到最小/最大值
     * - 空间：O(1) - 原地操作
     */
    public int findKthLargestByQuickSelect(int[] nums, int k) {
        // 第 k 大的数 = 排序后索引为 (length - k) 的数
        // 例如：第 2 大 = 索引 4（数组长度 6 - 2 = 4）
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    /**
     * 快速选择递归函数
     * @param nums 数组
     * @param left 当前搜索范围的左边界
     * @param right 当前搜索范围的右边界
     * @param k 目标元素的索引（排序后的位置）
     * @return 第 k 大的元素值
     */
    private int quickSelect(int[] nums, int left, int right, int k) {
        // 递归终止条件：搜索范围缩小到一个元素
        if (left == right) {
            return nums[left];
        }

        // 分区操作：将数组分为 <= pivot 和 > pivot 两部分
        // pivotIndex 是 pivot 元素分区后的最终位置
        int pivotIndex = partition(nums, left, right);
        
        // 情况1：pivot 的位置正好是目标位置，直接返回
        if (k == pivotIndex) {
            return nums[k];
        } 
        // 情况2：目标在 pivot 左边，递归处理左半部分
        else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } 
        // 情况3：目标在 pivot 右边，递归处理右半部分
        else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }

    /**
     * 分区函数（Partition）
     * 
     * 作用：选择一个基准值 pivot，将数组重新排列，使得：
     * - pivot 左边的所有元素 <= pivot
     * - pivot 右边的所有元素 > pivot
     * - 返回 pivot 的最终位置索引
     * 
     * 举例：[3, 2, 1, 5, 6, 4]，pivot = 4（最后一个元素）
     * 分区后：[3, 2, 1, 4, 6, 5]，pivotIndex = 3
     * 
     * @param nums 数组
     * @param left 左边界
     * @param right 右边界
     * @return pivot 元素的最终位置索引
     */
    private int partition(int[] nums, int left, int right) {
        // 选择最右边的元素作为基准值（pivot）
        int pivot = nums[right];
        
        // i 指向 <= pivot 区域的右边界（下一个要放 <= pivot 元素的位置）
        int i = left;
        
        // j 遍历整个区间，寻找 <= pivot 的元素
        for (int j = left; j < right; j++) {
            // 如果当前元素 <= pivot，将其放到 <= pivot 区域
            if (nums[j] <= pivot) {
                swap(nums, i, j);  // 交换 nums[i] 和 nums[j]
                i++;               // 扩大 <= pivot 区域
            }

        }
        
        // 最后将 pivot 放到正确位置（<= pivot 区域的右边）
        swap(nums, i, right);
        return i;  // 返回 pivot 的最终位置
    }

    /**
     * 交换数组中两个元素的位置
     */
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
        System.out.println("第 " + k + " 大的数字:");
        System.out.println("排序法: " + kthLargest.findKthLargestBySort(nums.clone(), k));
        System.out.println("最小堆法: " + kthLargest.findKthLargestByHeap(nums.clone(), k));
        System.out.println("快速选择法: " + kthLargest.findKthLargestByQuickSelect(nums.clone(), k));
    }

}
