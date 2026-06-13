package com.simon.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 阶乘 f(x)=x*f(x-1); f(1)=1
 * @author sangyiwen
 * @version 1.0
 * @date 2020/9/11 0:55
 */
public class Factorial {

    public int fact(int x){
        if(x==1){
            return 1;
        }
        return x*fact(x-1);
    }

    public static void main(String[] args) {
        Factorial factorial = new Factorial();
        System.out.println(factorial.fact(5));
        Map map = new HashMap();
    }

}
