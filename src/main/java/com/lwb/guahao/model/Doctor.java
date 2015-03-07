package com.lwb.guahao.model;

/**
 * Created by Lu Weibiao on 2015/2/16 15:47.
 */

import javax.persistence.*;
import javax.print.attribute.standard.NumberUp;
import java.io.Serializable;
import java.util.List;

/**
 * 医生
 */
@Entity
public class Doctor implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String accountName; //用于登陆的账户名

    @Column(nullable = false, length = 50)
    private String password; //账户密码

    @Column(nullable = false, length = 10)
    private String name; //医生名称

    @Column
    private Integer age; //年龄

    @Column(nullable = false, length = 1)
    private String sex; //性别

    @Column(length = 50)
    private String title; //医生头衔/级别：医师、主治医生、教授等等。

    @Column
    private Double price; //默认挂号费

    @Column(length = 30)
    private String goodAtTags; //擅长标签列表

    @Column(length = 1000)
    private String brief; //医生简介

    @Column(length = 1000)
    private String avatarPath; //头像物理存储路径

    @Column(nullable = false)
    private Integer deptClassCode; //科室类目编号 参见：ConstantsMap.deptClassMap

    @ManyToOne
    @JoinColumn(name = "hosptital_id",nullable = false)
    private Hospital hospital; //医院

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<DoctorDailySchedule> doctorDailyScheduleList; //每日号源安排

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGoodAtTags() {
        return goodAtTags;
    }

    public void setGoodAtTags(String goodAtTags) {
        this.goodAtTags = goodAtTags;
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

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<DoctorDailySchedule> getDoctorDailyScheduleList() {
        return doctorDailyScheduleList;
    }

    public void setDoctorDailyScheduleList(List<DoctorDailySchedule> doctorDailyScheduleList) {
        this.doctorDailyScheduleList = doctorDailyScheduleList;
    }
}
