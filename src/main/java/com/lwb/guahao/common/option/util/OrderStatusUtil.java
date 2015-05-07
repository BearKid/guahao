package com.lwb.guahao.common.option.util;

import com.lwb.guahao.common.option.OptionMap;

/**
 * User: Lu Weibiao
 * Date: 2015/5/7 18:01
 */
public class OrderStatusUtil {
    public static String getOrderStatusName(Integer orderStatus) {
        String orderStatusName = OptionMap.orderStatusMap.get(orderStatus);
        return orderStatusName == null ? "未知" : orderStatusName;
    }
}
