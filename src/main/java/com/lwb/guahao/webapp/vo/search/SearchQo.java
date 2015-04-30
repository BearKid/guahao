package com.lwb.guahao.webapp.vo.search;

/**
 * <p></p>
 * Date: 2015/4/27 19:17
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
public class SearchQo {
    private Integer areaCode;
    private Integer deptClassCode;
    private String keyWord;
    private Integer pn;
    private Integer pageSize;

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getDeptClassCode() {
        return deptClassCode;
    }

    public void setDeptClassCode(Integer deptClassCode) {
        this.deptClassCode = deptClassCode;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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
