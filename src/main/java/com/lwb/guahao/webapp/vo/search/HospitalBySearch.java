package com.lwb.guahao.webapp.vo.search;

import com.lwb.guahao.common.option.util.AreaUtil;
import com.lwb.guahao.common.model.Hospital;

/**
 * <p></p>
 * Date: 2015/4/27 19:44
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
public class HospitalBySearch {
    private String id;

    private String name; //医院名称

    private String address; //医院详细地址

    private String linkman; //联系人名称

    private String brief; //医院简介

    /*** 相较于 Hospital 增加、变更的字段***/
    private String areaName; //医院地区

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    /**
     * 转换为HospitalBySearch
     * @param hospital
     * @return
     */
    public static HospitalBySearch parse(Hospital hospital) {
        HospitalBySearch hospitalBySearch = new HospitalBySearch();

        hospitalBySearch.setName(hospital.getName());

        Integer id = hospital.getId();
        hospitalBySearch.setId((id == null) ? null : id.toString());

        hospitalBySearch.setAddress(hospital.getAddress());

        Integer areaCode = hospital.getAreaCode();
        hospitalBySearch.setAreaName(AreaUtil.getAreaName(areaCode));

        hospitalBySearch.setBrief(hospital.getBrief());
        hospitalBySearch.setLinkman(hospital.getLinkman());

        return hospitalBySearch;
    }
}
