package com.lwb.guahao.common.qo.util;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.qo.BaseQo;
import com.lwb.guahao.common.qo.ReservationQo;

/**
 * User: Lu Weibiao
 * Date: 2015/5/7 14:44
 */
public class QoUtil {
    /**
     * 获取查询条件对象的分页首行号在总记录集中的位置
     * @param qo
     * @return
     */
    public static int getFirstIndex(BaseQo qo) {
        Integer pn = getPn(qo);
        return (pn - 1) * getPageSize(qo);
    }

    /**
     * 获取查询条件对象调整后的分页大小
     * @param qo
     * @return
     */
    public static Integer getPageSize(BaseQo qo){
        Integer pageSize = qo.getPageSize();
        if(pageSize == null){
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    /**
     * 获取查询条件对象调整后的页码
     * @param qo
     * @return
     */
    public static Integer getPn(BaseQo qo) {
        return qo.getPn() == null ? 1 : qo.getPn();
    }
}
