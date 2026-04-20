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
            return  income + sum( income*(1+rate),  rate, --count);
        }
    }

    public double sum2(double rate,int count){
        if(count ==0){
            return  1;
        }else{
            return    (1+rate)*sum2(rate, --count);
        }
    }

    public static void main(String[] args) {
        CompoundInterest compoundInterest = new CompoundInterest();
        /*for(int i=1 ; i <=30 ; i++){
            System.out.println(compoundInterest.sum(100 ,0.2,i));
        }*/
        System.out.println(compoundInterest.sum(50 ,0.2,6));
        //System.out.println(50*compoundInterest.sum2( 0.2,10));
    }

}
