package com.lwb.guahao.common.util;

/**
 * User: Lu Weibiao
 * Date: 2015/2/23 23:40
 */

/**
 * 常见字段的合法性验证工具
 */
public class FieldValidationUtil {
    public static boolean isMobilePhone(String mobile){
        if(mobile == null) return false;
        return mobile.matches("^1[3|5|8]\\d{9}$");
    }
    public static boolean isTelPhone(String phone){
        if(phone == null) return false;
        return phone.matches("^0\\d{2,3}-?\\d{7,8}$");
    }

    public static boolean isEmail(String email) {
        if (email == null) return false;
        return email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }
}
