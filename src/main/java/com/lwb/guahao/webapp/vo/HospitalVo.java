package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.option.OptionMap;
import com.lwb.guahao.common.util.lang.DateUtils;
import com.lwb.guahao.common.model.Hospital;
import org.springframework.beans.BeanUtils;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 20:31
 */

/**
 * 医院账号VO
 */
public class HospitalVo {
    private String id;

    private String password; //账户密码-密文

    private String accountStatusCode; //账户状态-编码 参见：Constants.AccountStatus

    private String createDateTime; //账户创建日期时间

    private String latestLoginDateTime; //最近一次登录的日期时间

    private String email; //联系邮箱

    private String telPhone; //联系电话

    private String name; //医院名称

    private String areaCode; //医院地址-地区代码 参见ConstantsMap.areaMap

    private String address; //医院详细地址

    private String linkman; //联系人名称

    private String brief; //医院简介

    private String avatarPath; //头像物理存储路径

    private String modifyDateTime; //Hospital被修改的日期时间

    private String bankCard;//银行卡卡号

    /*----------相较于Hospital 新增的字段-----------*/
    private String accountStatusName; //账户状态-名称 参见：Constants.AccountStatus

    private String areaName; //医院地址-名称 参见ConstantsMap.areaMap

    /**
     * 根据Hospital转换出HosptialVo
     * @param hospital
     * @return
     */
    public static HospitalVo parse(Hospital hospital){
        HospitalVo hospitalVo = new HospitalVo();
        BeanUtils.copyProperties(hospital,hospitalVo);

        hospitalVo.setId(hospital.getId() == null ? null : hospital.getId().toString());
        hospitalVo.setAccountStatusCode(hospital.getAccountStatusCode() == null ? null : hospital.getAccountStatusCode().toString());
        hospitalVo.setAccountStatusName(OptionMap.accountStatusMap.get(hospital.getAccountStatusCode()));
        hospitalVo.setAreaName(OptionMap.areaMap.get(hospital.getAreaCode()));
        hospitalVo.setAreaCode(hospital.getAreaCode() == null ? null : hospital.getAreaCode().toString());
        //日期时间
        hospitalVo.setCreateDateTime(hospital.getCreateDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(hospital.getCreateDateTime()));
        hospitalVo.setModifyDateTime(hospital.getModifyDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(hospital.getModifyDateTime()));
        hospitalVo.setLatestLoginDateTime(hospital.getLatestLoginDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(hospital.getLatestLoginDateTime()));
        return hospitalVo;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
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

    public String getModifyDateTime() {
        return modifyDateTime;
    }

    public void setModifyDateTime(String modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
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
