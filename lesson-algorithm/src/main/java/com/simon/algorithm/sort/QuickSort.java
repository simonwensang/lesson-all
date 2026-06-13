package com.simon.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 快速排序
 * Created by sang on 2019/6/19.
 */
public class QuickSort implements Sort<int[]> {

    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        // 分区操作，返回 pivot 的位置
        int pivotIndex = partition(array, left, right);

        // 递归排序左右两部分
        quickSort(array, left, pivotIndex - 1);
        quickSort(array, pivotIndex + 1, right);
    }

    /**
     * 分区操作
     * 选择最右边的元素作为 pivot，将数组分为两部分：
     * 左边 <= pivot，右边 > pivot
     */
    private int partition(int[] array, int left, int right) {
        int pivot = array[right];  // 选择最右边的元素作为 pivot
        int i = left;              // i 指向 <= pivot 区域的右边界

        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                swap(array, i, j);
                i++;
            }
        }

        // 将 pivot 放到正确位置
        swap(array, i, right);
        return i;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {2, 8, 7, 4, 3, 6, 5, 1};
        Sort sort = new QuickSort();
        sort.sort(array);
        System.out.println(JSON.toJSONString(array));
    }

}
