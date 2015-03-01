package com.lwb.guahao.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/16 14:07.
 */
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String password; //账户密码

    @Column(nullable = false)
    private Integer accountStatus; //账户状态 参见：Constants.AccountStatus

    @Column(nullable = false)
    private Date creDate; //账户创建日期时间

    @Column(nullable = false)
    private String email; //联系邮箱

    @Column(nullable = false, length = 13)
    private String telPhone; //联系电话

    @Column(nullable = false, length = 50)
    private String name; //医院名称

    @Column(nullable = false)
    private Integer areaCode; //医院地址-地区代码 参见ConstantsMap.areaMap

    @Column(nullable = false, length = 255)
    private String address; //医院详细地址

    @Column(nullable = false, length = 10)
    private String linkman; //联系人名称

    @Column(nullable = false, length = 2000)
    private String brief; //医院简介

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
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
