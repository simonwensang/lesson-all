package com.simon.algorithm;

/**
 * @author sangyiwen
 * @version 1.0
 * @date 2020/9/11 0:55
 */
public class GreatestCommonDiviser {
    //欧几里得算法
    public int gcd(int a ,int b){
        if(b==0){
            return a;
        }else{
            return gcd(b,a%b);
        }
    }

    public static void main(String[] args) {
        GreatestCommonDiviser greatestCommonDiviser = new GreatestCommonDiviser();
        System.out.println( greatestCommonDiviser.gcd(320,50));
    }

}
