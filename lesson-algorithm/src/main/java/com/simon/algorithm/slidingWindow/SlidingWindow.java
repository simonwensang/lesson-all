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
     * 时间片轮转（Round Robin, RR）算法是一种专为分时系统设计的核心进程调度算法。
     * [1] 它的核心思想是让所有就绪进程轮流使用CPU，以确保系统的响应速度和公平性。以下是该算法的核心机制、
     * 工作原理及优缺点：
     * 核心机制时间片（Time Quantum / Slot）：系统分配给每个进程单次连续执行的微小时间段（通常为 10ms - 100ms）。
     * 循环队列：所有就绪进程按照到达先后顺序排成一个先进先出（FIFO）的队列。
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
