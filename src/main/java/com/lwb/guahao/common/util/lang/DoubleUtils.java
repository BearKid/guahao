package com.lwb.guahao.common.util.lang;

/**
 * User: Lu Weibiao
 * Date: 2015/4/19 18:58
 */
public class DoubleUtils {
    public static Double parseString(String src, Double defaultValue){
        Double val = defaultValue;
        try{
            val = Double.parseDouble(src);
        }catch (NumberFormatException e){}
        return val;
    }
}
