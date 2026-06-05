package com.simon.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序
 * Created by sang on 2019/6/19.
 */
public class BubbleSort implements Sort<int[]> {

    public void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            // 每次循环都将最大的数交换到数组末尾
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
