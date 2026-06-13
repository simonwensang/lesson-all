package com.simon.algorithm.slidingWindow;

/**
 * Created by sang on 2020/8/6.
 */
public class SlidingWindow {

    /**
     * 1 链表存储 1小时的订单
     * @param k
     * @return
     */


    /**
     * 2 时间片轮转 1小时的订单 分成60分钟
     * @param k
     * @return
     */

    /**
     * 3 相邻的两个数字之和 最大的值
     * @param k
     * @return
     */
    public static  int getMaxSum(int k){
        int[] arr = new int[]{100,200,300,400,100,500};

        int maxsum = 0;
        for(int i =0 ;i < k;i++){
            maxsum += arr[i];
        }
        int sum = maxsum;
        for(int j=k;j<arr.length ; j++){
            sum += arr[j]-arr[j-k];
            maxsum = Math.max(maxsum,sum);
        }

        return maxsum;
    }

    public static void main(String[] args) {
        System.out.println(SlidingWindow.getMaxSum(2));
    }
}
