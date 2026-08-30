package com.simon.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序(Bubble Sort)
 * 最好时间复杂度 O(n)
 * 平均时间复杂度 O(n²)
 * 最坏时间复杂度 O(n²)
 * 空间复杂度 O(1)
 * 稳定性  稳定
 * Created by sang on 2019/6/19.
 */
public class BubbleSort implements Sort<int[]> {

    public void sort(int[] array) {
        //第一层for循环i不使用，只用来标记已经排序的位置
        for (int i = 0; i < array.length - 1; i++) {
            // 每次循环都将最大的数交换到数组末尾，末尾就是最大的已经排序的数字
            for (int j = 0; j < array.length - 1 - i; j++) {
                // 如果前一个元素大于后一个元素，则交换
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {2, 8, 7, 4, 3, 6, 5, 1};
        Sort sort = new BubbleSort();
        sort.sort(array);
        System.out.println(JSON.toJSONString(array));
    }

}
