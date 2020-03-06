package com.simon.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * Created by sang on 2019/6/19.
 */
public class InsertSort implements  Sort<int[]>{


    public void sort(int[] array) {

        for(int i = 1 ; i< array.length ; i++){
            //param
            int param = array[i];
            int j = i-1;
            while(  j>=0&& array[j]>param){
                // 前后换位
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = param;
        }
    }

    public static void main(String[] args) {
        int[] array = {2,1,3,4,7,6,5,8};
        InsertSort insertSort = new InsertSort();
        insertSort.sort(array);
        System.out.println(JSON.toJSONString(array));
    }

}
