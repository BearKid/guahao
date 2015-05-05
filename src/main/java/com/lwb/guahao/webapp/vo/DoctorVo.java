package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.common.util.SexUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lwb.guahao.common.util.DateUtils.*;

/**
 * Function:
 *
 * @autor:Lu Weibiao Date: 2015/3/17 21:46
 */
public class DoctorVo {
    private Integer id;

    private String accountName; //用于登陆的账户名

    //private String password; //账户密码

    private Integer accountStatusCode; //账户状态 参见：Constants.AccountStatus

    private String createDateTime; //账户创建日期时间

    private String name; //医生名称

    private Integer age; //年龄

    private String sex; //性别

    private String title; //医生头衔/级别：医师、主治医生、教授等等。

    private Double price; //默认挂号费

    private String goodAtTags; //擅长标签列表

    private String brief; //医生简介

    private String avatarPath; //头像物理存储路径

    private Integer deptClassCode; //科室类目编号 参见：ConstantsMap.deptClassMap

    private String latestLoginDate; //最近一次登录的日期时间

    private String ModifyDateTime; //被修改的日期时间

    private Integer hospitalId; //医院id

    /*** 相较于 Doctor 增加的字段 **/
    private String deptClassName; //科室类目编号 参见：ConstantsMap.deptClassMap

    private HospitalVo hospital; //医院

    /**
     * model转vo
     * @param doctor
     * @return
     */
    public static DoctorVo parse(Doctor doctor){
        if(doctor == null) return null;
        DoctorVo doctorVo = new DoctorVo();
        BeanUtils.copyProperties(doctor,doctorVo);
        doctorVo.setSex(SexUtil.getSexName(doctor.getSex()));
        doctorVo.setDeptClassName(ConstantsMap.deptClassMap.get(doctor.getDeptClassCode()));

        //日期时间格式化
        doctorVo.setCreateDateTime(StringUtils.isEmpty(doctor.getLatestLoginDate()) ? "未知" : yearMonthDayTimeFormatter.format(doctor.getCreateDateTime()));
        doctorVo.setModifyDateTime(StringUtils.isEmpty(doctor.getModifyDateTime()) ? "未知" : yearMonthDayTimeFormatter.format(doctor.getModifyDateTime()));
        doctorVo.setLatestLoginDate(StringUtils.isEmpty(doctor.getLatestLoginDate()) ? "未知" : yearMonthDayTimeFormatter.format(doctor.getLatestLoginDate()));

        return doctorVo;
    }

    public static DoctorVo parse(Doctor doctor, Hospital hospital){
        DoctorVo doctorVo = parse(doctor);
        doctorVo.setHospital(HospitalVo.parse(hospital));
        return doctorVo;
    }

    public static List<DoctorVo> parse(List<Doctor> doctors) {
        List<DoctorVo> doctorVos = new ArrayList<DoctorVo>(doctors.size());
        for(Doctor doctor : doctors){
            doctorVos.add(parse(doctor));
        }
        return doctorVos;
    }

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

    public Integer getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(Integer accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Integer getDeptClassCode() {
        return deptClassCode;
    }

    public void setDeptClassCode(Integer deptClassCode) {
        this.deptClassCode = deptClassCode;
    }

    public String getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(String latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }

    public String getModifyDateTime() {
        return ModifyDateTime;
    }

    public void setModifyDateTime(String modifyDateTime) {
        this.ModifyDateTime = modifyDateTime;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDeptClassName() {
        return deptClassName;
    }

    public void setDeptClassName(String deptClassName) {
        this.deptClassName = deptClassName;
    }

    public HospitalVo getHospital() {
        return hospital;
    }

    public void setHospital(HospitalVo hospital) {
        this.hospital = hospital;
    }

}
