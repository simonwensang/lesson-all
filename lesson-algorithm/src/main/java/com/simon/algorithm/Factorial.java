package com.simon.algorithm;
/**
 * @author sangyiwen
 * @version 1.0
 * @date 2020/9/11 0:55
 */
public class Factorial {

    public int fact(int x){
        if(x==1){
            return 1;
        }else{
            return x*fact(x-1);
        }
    }

    public static void main(String[] args) {
        Factorial factorial = new Factorial();
        System.out.println(factorial.fact(5));
    }

}
