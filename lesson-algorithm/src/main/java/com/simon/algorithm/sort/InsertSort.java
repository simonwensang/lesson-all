package com.simon.algorithm.sort;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * 插入排序 (Insertion Sort) ，插入次数少
 *  * 最好时间复杂度 O(n)
 *  * 平均时间复杂度 O(n²)
 *  * 最坏时间复杂度 O(n²)
 *  * 空间复杂度 O(1)
 *  * 稳定性  稳定
 *  为什么插入排序要比冒泡排序更受欢迎呢？
 * 我们前面分析冒泡排序和插入排序的时候讲到，冒泡排序不管怎么优化，元素交换的次数是一个固定值，是原始数据的逆序度。插入排序是同样的，不管怎么优化，元素移动的次数也等于原始数据的逆序度。虽然插入排序和冒泡排序元素交换的最终次数都是一样的，但：
 * 1 冒泡排序元素交换比插入排序要复杂。插入排序只需要一次操作。
 * 2 插入排序的时间复杂度更稳定。冒泡排序只有当数据完全有序时，时间复杂度才是 O(1)，只要有一个逆序度则时间复杂度就变成 O(n2)。而插入排序在数据比较有序时更有优势。
 * Created by sang on 2019/6/19.
 */
public class InsertSort implements  Sort<int[]>{


    public void sort(int[] array) {

        for(int i = 1 ; i< array.length ; i++){
            //param
            int param = array[i];
            int j = i-1;
            while(j>=0 && array[j]>param){
                // 前后换位
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = param;
        }
    }

    public static void main(String[] args) {
        int[] array = {2,1,7,4,3,6,5,8};
        Sort sort = new InsertSort();
        sort.sort(array);
        System.out.println(JSON.toJSONString(array));
        /*int[] sub = Arrays.copyOf(array,2);
        int[] system= new int[6];
        System.arraycopy(array,1,system,1,5);
        System.out.println(JSON.toJSONString(system));*/
    }

}
