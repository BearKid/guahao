package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.util.AreaUtil;
import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.common.util.DateUtils;
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

    private String accountStatusCode; //账户状态 参见：Constants.AccountStatus
    private String accountStatusName; //账户状态 参见：Constants.AccountStatus

    private String createDateTime; //账户创建日期时间

    private String latestLoginDateTime; //最近一次登录的日期时间

    private String email; //联系邮箱

    private String telPhone; //联系电话

    private String name; //医院名称

    private String areaCode; //医院地区-地区代码 参见ConstantsMap.areaMap
    private String areaName; //医院地区

    private String address; //医院详细地址

    private String linkman; //联系人名称

    private String brief; //医院简介

    /**
     * 根据Hospital转换出HosptialVo
     * @param source
     * @return
     */
    public final static HospitalVo parse(final Hospital source){
        if(source == null) return null;
        HospitalVo target = new HospitalVo();
        BeanUtils.copyProperties(source, target);

        target.setId(source.getId() == null ? null : source.getId().toString());

        target.setAccountStatusCode(source.getAccountStatusCode() == null ? "未知" : source.getAccountStatusCode().toString());
        target.setAccountStatusName(ConstantsMap.accountStatusMap.get(source.getAccountStatusCode()));

        target.setAreaCode(source.getAreaCode() == null ? "未知" : source.getAreaCode().toString());
        target.setAreaName(AreaUtil.getAreaName(source.getAreaCode()));

        //日期时间
        target.setLatestLoginDateTime(source.getLatestLoginDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(source.getLatestLoginDateTime()));
        target.setCreateDateTime(source.getCreateDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(source.getCreateDateTime()));

        return target;
    }

    public String getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(String accountStatusCode) {
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
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

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
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

    public String getLatestLoginDateTime() {
        return latestLoginDateTime;
    }

    public void setLatestLoginDateTime(String latestLoginDateTime) {
        this.latestLoginDateTime = latestLoginDateTime;
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
