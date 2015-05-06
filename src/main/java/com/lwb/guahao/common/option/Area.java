package com.lwb.guahao.common.option;

/**
 * Created by Lu Weibiao on 2015/2/18 21:59.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 地区
 */
public class Area {
    private int code; //代码
    private String name; //名称
    private List<Area> subAreaList; //下级管辖地区

    public Area(int code, String name){
        this.code = code;
        this.name = name;
        this.subAreaList = null;
    }
    public Area(int code, String name, boolean hasSubList){
        this.code = code;
        this.name = name;
        if(hasSubList){
            this.subAreaList = new ArrayList<Area>();
        } else {
            this.subAreaList = null;
        }
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

    public List<Area> getSubAreaList() {
        return subAreaList;
    }

    public void setSubAreaList(List<Area> subAreaList) {
        this.subAreaList = subAreaList;
    }
}
