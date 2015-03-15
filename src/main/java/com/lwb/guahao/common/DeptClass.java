package com.lwb.guahao.common;

/**
 * User: Lu Weibiao
 * Date: 2015/3/15 19:42
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 科室类目
 */
public class DeptClass {
    private int code;
    private String name;
    private List<DeptClass> subDeptClassList;

    public DeptClass(Integer code, String name, boolean hasSub) {
        this.code = code;
        this.name = name;
        this.subDeptClassList = hasSub ? new ArrayList<DeptClass>() : null;
    }

    public DeptClass(Integer code, String name) {
        this.code = code;
        this.name = name;
        this.subDeptClassList = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeptClass> getSubDeptClassList() {
        return subDeptClassList;
    }

    public void setSubDeptClassList(List<DeptClass> subDeptClassList) {
        this.subDeptClassList = subDeptClassList;
    }
}
