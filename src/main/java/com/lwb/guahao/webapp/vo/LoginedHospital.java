package com.lwb.guahao.webapp.vo;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 18:25
 */

import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.model.Hospital;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 登录态的医生账号相关信息
 */
public class LoginedHospital {
    private Integer id;

    private String password; //账户密码-密文

    private Integer accountStatusCode; //账户状态-编码 参见：Constants.AccountStatus

    private Date createDate; //账户创建日期时间

    private Date latestLoginDate; //最近一次登录的日期时间

    private String email; //联系邮箱

    private String telPhone; //联系电话

    private String name; //医院名称

    private Integer areaCode; //医院地址-地区代码 参见ConstantsMap.areaMap

    private String address; //医院详细地址

    private String linkman; //联系人名称

    private String brief; //医院简介

    private String avatarPath; //头像物理存储路径

    private Date modifiedDate; //Hospital被修改的日期时间

    /*----------相较于Hospital 新增的字段-----------*/
    private String accountStatusName; //账户状态-名称 参见：Constants.AccountStatus

    private String areaName; //医院地址-名称 参见ConstantsMap.areaMap

    public static LoginedHospital parse(Hospital hospital){
        LoginedHospital loginedHospital = new LoginedHospital();
        BeanUtils.copyProperties(hospital,loginedHospital);

        loginedHospital.setAccountStatusName(ConstantsMap.accountStatusMap.get(loginedHospital.getAccountStatusCode()));
        loginedHospital.setAreaName(ConstantsMap.areaMap.get(loginedHospital.getAreaCode()));
        return loginedHospital;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(Date latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
}
