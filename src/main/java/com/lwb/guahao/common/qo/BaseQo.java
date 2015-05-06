package com.lwb.guahao.common.qo;

import com.lwb.guahao.common.Constants;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/25 19:02
 */
public abstract class BaseQo {
    Integer pn;
    Integer pageSize = Constants.DEFAULT_PAGE_SIZE;

    public Integer getFirstIndex(){
        return (pn - 1) * pageSize;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
