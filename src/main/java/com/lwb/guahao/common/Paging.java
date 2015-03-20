package com.lwb.guahao.common;

import java.util.List;

/**
 * Function:
 *
 * @autor:Lu Weibiao Date: 2015/3/17 22:11
 */
public class Paging<T> {
    int pageSize; //分页大小
    int pn; //当前页码
    int totalSize; //所有分页的元素总数
    List<T> items; //当前分页元素集合

    public Paging(List<T> items, int pn, int pageSize, int totalSize){
        this.items = items;
        this.pn = pn;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
//        if(pn > getTotalPages()){
//            throw new IllegalArgumentException();
//        }
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotalPages(){
        if(totalSize < 1) return 0;
        return (totalSize -1) / pageSize + 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPn() {
        return pn;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
