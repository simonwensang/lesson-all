package com.simon.algorithm.array;

public class MySqrt {

    public static int sqrt(int num){
        if(num < 2){
            return num;
        }
        int left = 0;
        int right = num/2;

        while(left<=right){
            int mid = left + (right-left+1)/2;
            int sqrt=mid*mid;
            if(sqrt==num){
                return mid;
            }else if(sqrt < num){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
        return right;
    }

    public static void main(String[] args) {
        System.out.println(sqrt2(25));
        System.out.println(sqrt2(625));
    }

    public static int sqrt2(int num){
        //1 边界条件case<2
        if(num < 2){
            return num;
        }
        // 2 初始化left和right
        int left = 0;
        int right = num/2;
        //3循环left<=right
        while(left<=right){
            //4 找中点位置 和 中点的平方值
            int mid = left + (right-left+1)/2;
            int sqrt = mid * mid;
            //5 中点的平方值 和 num 比大小，决定返回中点，还是向左，向右查询
            if(sqrt==num){
                return mid;
            }else if(sqrt < num){
                left = mid+1;
            }else {
                right = mid-1;
            }
        }

        return right;
    }
}
