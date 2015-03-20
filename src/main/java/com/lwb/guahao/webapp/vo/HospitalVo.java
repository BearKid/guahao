package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.util.AreaUtil;
import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.model.Hospital;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 20:31
 */

/**
 * 医院账号VO
 */
public class HospitalVo {
    private String id;

    private Integer accountStatusCode; //账户状态 参见：Constants.AccountStatus
    private String accountStatusName; //账户状态 参见：Constants.AccountStatus

    private String createDate; //账户创建日期时间

    private String latestLoginDate; //最近一次登录的日期时间

    private String email; //联系邮箱

    private String telPhone; //联系电话

    private String name; //医院名称

    private Integer areaCode; //医院地区-地区代码 参见ConstantsMap.areaMap
    private String areaName; //医院地区

    private String address; //医院详细地址

    private String linkman; //联系人名称

    private String brief; //医院简介

    /**
     * 根据Hospital转换出HosptialVo
     * @param source
     * @return
     */
    public final static HospitalVo parse(Hospital source){
        if(source == null) return null;
        HospitalVo target = new HospitalVo();
        BeanUtils.copyProperties(source, target);

        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");

        target.setAccountStatusName(ConstantsMap.accountStatusMap.get(source.getAccountStatusCode()));
        target.setLatestLoginDate(datetimeFormat.format(source.getLatestLoginDateTime()));
        target.setCreateDate(datetimeFormat.format(source.getCreateDate()));
        target.setAreaName(AreaUtil.getAreaName(source.getAreaCode()));

        return target;
    }

    public Integer getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(Integer accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
    }

    public String getAccountStatusName() {
        return accountStatusName;
    }

    public void setAccountStatusName(String accountStatusName) {
        this.accountStatusName = accountStatusName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(String latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
}
