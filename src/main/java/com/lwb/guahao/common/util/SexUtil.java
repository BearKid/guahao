package com.lwb.guahao.common.util;

import com.lwb.guahao.common.constants.ConstantsMap;

/**
 * User: Lu Weibiao
 * Date: 2015/5/4 21:42
 */
public class SexUtil {
    public static String getSexName(Integer sexCode){
        return ConstantsMap.sexMap.get(sexCode);
    }
}
