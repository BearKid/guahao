package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.model.Doctor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 18:53
 */

/**
 * 登录态的医生账号相关信息
 */
public class LoginedDoctor {
    private Integer id;

    private String accountName; //用于登陆的账户名

    private String password; //账户密码

    private Integer accountStatusCode; //账户状态 参见：Constants.AccountStatus

    private Date createDate; //账户创建日期时间

    private String name; //医生名称

    private Integer age; //年龄

    private String sex; //性别

    private String title; //医生头衔/级别：医师、主治医生、教授等等。

    private Double price; //默认挂号费

    private String goodAtTags; //擅长标签列表

    private String brief; //医生简介

    private String avatarPath; //头像物理存储路径

    private Integer deptClassCode; //科室类目-编号 参见：ConstantsMap.deptClassMap

    private Date latestLoginDate; //最近一次登录的日期时间

    private Date modifiedDate; //被修改的日期时间

    private Integer hospitalId; //医院id

    /*-------相较于Doctor 新增的字段----------*/
    private String deptClassName; //科室类目-名称 参见：ConstantsMap.deptClassMap
    private String accountStatusName; //账户状态-名称 参见：Constants.AccountStatus

    public static LoginedDoctor parse(Doctor doctor){
        LoginedDoctor loginedDoctor = new LoginedDoctor();
        BeanUtils.copyProperties(doctor,loginedDoctor);

        loginedDoctor.setDeptClassName(ConstantsMap.deptClassMap.get(loginedDoctor.getDeptClassCode()));
        loginedDoctor.setAccountStatusName(ConstantsMap.accountStatusMap.get(loginedDoctor.getAccountStatusCode()));
        return loginedDoctor;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(Date latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDeptClassName() {
        return deptClassName;
    }

    public void setDeptClassName(String deptClassName) {
        this.deptClassName = deptClassName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Integer getDeptClassCode() {
        return deptClassCode;
    }

    public void setDeptClassCode(Integer deptClassCode) {
        this.deptClassCode = deptClassCode;
    }

    public String getGoodAtTags() {
        return goodAtTags;
    }

    public void setGoodAtTags(String goodAtTags) {
        this.goodAtTags = goodAtTags;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
