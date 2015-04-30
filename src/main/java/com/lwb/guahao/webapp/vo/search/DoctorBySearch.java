package com.lwb.guahao.webapp.vo.search;

import com.lwb.guahao.common.util.AreaUtil;
import com.lwb.guahao.common.util.DeptClassUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import org.springframework.util.StringUtils;

/**
 * <p></p>
 * Date: 2015/4/27 19:33
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
public class DoctorBySearch {
    private String id;

    private String name; //医生名称

    private String age; //年龄

    private String sex; //性别

    private String title; //医生头衔/级别：医师、主治医生、教授等等。

    private String brief; //医生简介

    private String avatarPath; //头像物理存储路径

    private String deptClassName; //科室类目编号 参见：ConstantsMap.deptClassMap

    private String hospitalId; //医院id

    /*** 相较于 Docotor 增加、变更的字段 ***/
    private String[] goodAtTags; //擅长标签列表

    private String hospitalName; //医院名称

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String[] getGoodAtTags() {
        return goodAtTags;
    }

    public void setGoodAtTags(String[] goodAtTags) {
        this.goodAtTags = goodAtTags;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getDeptClassName() {
        return deptClassName;
    }

    public void setDeptClassName(String deptClassName) {
        this.deptClassName = deptClassName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * 解析转换为DoctorBySearch
     * @param doctor
     * @param hospital
     * @return
     */
    public static DoctorBySearch parse(Doctor doctor, Hospital hospital) {
        DoctorBySearch doctorBySearch = new DoctorBySearch();

        Integer age = doctor.getAge();
        doctorBySearch.setAge((age == null) ? null : age.toString());

        doctorBySearch.setAvatarPath(doctor.getAvatarPath());
        doctorBySearch.setBrief(doctor.getBrief());

        Integer deptClassCode = doctor.getDeptClassCode();
        doctorBySearch.setDeptClassName((deptClassCode == null) ? null : DeptClassUtil.getDeptClassName(deptClassCode));

        String[] goodAtTags = StringUtils.split(doctor.getGoodAtTags(),Doctor.GOOD_AT_TAGS_DELIMITER);
        doctorBySearch.setGoodAtTags(goodAtTags);

        Integer hospitalId = doctor.getHospitalId();
        doctorBySearch.setHospitalId((hospitalId == null) ? null : hospitalId.toString());

        doctorBySearch.setHospitalName(hospital.getName());

        Integer id = doctor.getId();
        doctorBySearch.setId((id == null) ? null : id.toString());

        doctorBySearch.setName(doctor.getName());
        doctorBySearch.setSex(doctor.getSex());
        doctorBySearch.setTitle(doctor.getTitle());

        return doctorBySearch;
    }
}
