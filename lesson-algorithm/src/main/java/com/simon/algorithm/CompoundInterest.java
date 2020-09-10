package com.simon.algorithm;

import java.math.BigDecimal;

/**
 * @author sangyiwen
 * @version 1.0
 * @date 2020/9/11 1:01
 */
public class CompoundInterest {

    public double sum(double income, double rate,int count){
        if(count ==0){
            return  0;
        }else{
            double init = income;
            income +=  income*rate ;
            count--;
            return  init + sum( income,  rate, count);
        }
    }
    public static void main(String[] args) {
        CompoundInterest compoundInterest = new CompoundInterest();
        for(int i=1 ; i <=10 ; i++){
            System.out.println(compoundInterest.sum(50 ,0.5,i));
        }

    }

}
