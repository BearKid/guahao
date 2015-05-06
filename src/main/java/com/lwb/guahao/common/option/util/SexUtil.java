package com.lwb.guahao.common.option.util;

import com.lwb.guahao.common.option.OptionMap;

/**
 * User: Lu Weibiao
 * Date: 2015/5/4 21:42
 */
public class SexUtil {
    public static String getSexName(Integer sexCode){
        return OptionMap.sexMap.get(sexCode);
    }
}
