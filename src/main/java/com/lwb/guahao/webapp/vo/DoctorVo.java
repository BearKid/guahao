package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.model.Doctor;
import com.lwb.guahao.common.option.OptionMap;
import com.lwb.guahao.common.option.util.SexUtil;
import com.lwb.guahao.common.util.lang.DateUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Function:
 *
 * @autor:Lu Weibiao Date: 2015/3/17 21:46
 */
public class DoctorVo {
    private String id;

    private String accountName; //用于登陆的账户名

    private String password; //账户密码

    private String accountStatusCode; //账户状态 参见：Constants.AccountStatus

    private String createDateTime; //账户创建日期时间

    private String name; //医生名称

    private String age; //年龄

    private String sex; //性别

    private String title; //医生头衔/级别：医师、主治医生、教授等等。

    private String price; //默认挂号费

    private String goodAtTags; //擅长标签列表

    private String brief; //医生简介

    private String avatarPath; //头像物理存储路径

    private String deptClassCode; //科室类目-编号 参见：OptionMap.deptClassMap

    private String latestLoginDateTime; //最近一次登录的日期时间

    private String modifyDateTime; //被修改的日期时间

    private String hospitalId; //医院id

    /*-------相较于Doctor 新增的字段----------*/
    private String deptClassName; //科室类目-名称 参见：OptionMap.deptClassMap
    private String accountStatusName; //账户状态-名称 参见：Constants.AccountStatus
    private HospitalVo hospital; //医院

    public static DoctorVo parse(Doctor doctor){
        DoctorVo doctorVo = new DoctorVo();
        BeanUtils.copyProperties(doctor,doctorVo);

        doctorVo.setId(doctor.getId() == null ? null : doctor.getId().toString());
        doctorVo.setAccountStatusCode(doctor.getAccountStatusCode() == null ? null : doctor.getAccountStatusCode().toString());
        doctorVo.setPrice(doctor.getPrice() == null ? null : doctor.getPrice().toString());
        doctorVo.setSex(doctor.getSex() == null ? "未知" : SexUtil.getSexName(doctor.getSex()));
        doctorVo.setAge(doctor.getAge() == null ? null : doctor.getAge().toString());
        doctorVo.setDeptClassCode(doctor.getDeptClassCode() == null ? null : doctor.getDeptClassCode().toString());
        doctorVo.setHospitalId(doctor.getHospitalId() == null ? null : doctor.getHospitalId().toString());
        doctorVo.setDeptClassName(OptionMap.deptClassMap.get(doctor.getDeptClassCode()));
        doctorVo.setAccountStatusName(OptionMap.accountStatusMap.get(doctor.getAccountStatusCode()));
        //日期时间
        doctorVo.setCreateDateTime(doctor.getCreateDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(doctor.getCreateDateTime()));
        doctorVo.setModifyDateTime(doctor.getModifyDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(doctor.getModifyDateTime()));
        doctorVo.setLatestLoginDateTime(doctor.getLatestLoginDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(doctor.getLatestLoginDateTime()));
        return doctorVo;
    }

    public static List<DoctorVo> parse(List<Doctor> doctors) {
        List<DoctorVo> doctorVos = new ArrayList<DoctorVo>(doctors.size());
        for(Doctor doctor : doctors){
            doctorVos.add(parse(doctor));
        }
        return doctorVos;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getDeptClassCode() {
        return deptClassCode;
    }

    public void setDeptClassCode(String deptClassCode) {
        this.deptClassCode = deptClassCode;
    }

    public String getDeptClassName() {
        return deptClassName;
    }

    public void setDeptClassName(String deptClassName) {
        this.deptClassName = deptClassName;
    }

    public String getGoodAtTags() {
        return goodAtTags;
    }

    public void setGoodAtTags(String goodAtTags) {
        this.goodAtTags = goodAtTags;
    }

    public HospitalVo getHospital() {
        return hospital;
    }

    public void setHospital(HospitalVo hospital) {
        this.hospital = hospital;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
