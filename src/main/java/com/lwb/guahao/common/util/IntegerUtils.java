package com.lwb.guahao.common.util;

/**
 * @autor: Lu Weibiao
 * Date: 2015/4/1 22:18
 */
public class IntegerUtils {
    public static Integer parseString(String str, Integer defaultValue){
        Integer val = defaultValue;
        try {
            val = Integer.valueOf(str);
        }catch (Exception e){}
        return val;
    }
}
