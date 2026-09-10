package com.simon.algorithm.slidingWindow;

/**
 * 标题
 * n个数字组成n位整数，去除其中k位，剩余n-k位最小
 *
 * 题目描述
 * 示例 1： 输入：num = "1432219", k = 3 输出："1219"
 * 示例 2： 输入：num = "13211", k = 1 输出："1211"
 * 示例 3： 输入：num = "11", k = 2 输出："0"
 */
public class RemoveKthMinimum {

    public static String removeKDigits(String num,int k){
        if(k>num.length()){
            return "0";
        }
        StringBuilder stack = new StringBuilder();

        for(char digit : num.toCharArray()){
            if(k>0 && stack.length()>0 && stack.charAt(stack.length()-1)>digit){
                stack.deleteCharAt(stack.length()-1);
                k--;
            }
            stack.append(digit);
        }
        if(k>0){
            stack.delete(stack.length()-k,stack.length());
        }
        String result = stack.toString().replaceAll("^0+","");
        return result.isEmpty()?"0":result;
    }

    public static void main(String[] args) {
        System.out.println(removeKDigits2("1432219",3));
        System.out.println(removeKDigits2("13211",1));
        System.out.println(removeKDigits2("11",2));
        System.out.println(removeKDigits2("1432319",3));
    }

    public static String removeKDigits2(String num,int k){
        //1 边界条件case k > num的长度
        if(k > num.length()){
            return "0";
        }
        //2 使用StringBuilder做stack，把num的字母都压到栈里，如果栈顶的字母>要压入栈的字母，先弹出栈顶的字母 并且k--
        StringBuilder stack = new StringBuilder();
        for(char digit:num.toCharArray()){
            if(k>0 && stack.length()>0 && stack.charAt(stack.length()-1)>digit){
                stack.deleteCharAt(stack.length()-1);
                k--;
            }
            stack.append(digit);
        }
        // 3 如果k依然>0，删除最后k位
        if(k>0){
            stack.delete(stack.length()-k,stack.length());
        }
        //4 空 替换掉 0
        String result = stack.toString().replaceAll("^0+","");
        //5 如果结果为空返回“0”
        return result.isEmpty()?"0":result;
    }

}
