package com.simon.lesson6.utils;


/**
 * Created by sang on 2018/7/24.
 */
public class CommandSignUtil {
    public static final  String PATTERN_TEN="0000000000";
    public static final  String PATTERN_EIGHT="0000000000";
    public static final  String PATTERN_SIX="000000";
    public static final  String PATTERN_FOUR="0000";
    public static final  String PATTERN_TWO="00";

    public static String getPatternBit(String pattern,String param){
        if(pattern.length()<param.length()){
            return param;
        }
        return pattern.substring(0, pattern.length()-param.length())+param;
    }

 /*   public static String getSixBit(String param){
        return PATTERN_SIX.substring(0, PATTERN_SIX.length()-param.length())+param;
    }

    public static String getTwoBit(String param){
        return PATTERN_TWO.substring(0, PATTERN_TWO.length()-param.length())+param;
    }*/

}
