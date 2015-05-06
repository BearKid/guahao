package com.lwb.guahao.common.model;

/**
 * Created by Lu Weibiao on 2015/2/16 17:47.
 */

import org.apache.taglibs.standard.lang.jstl.IntegerDivideOperator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 医院科室
 */
public class Department implements Serializable{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

    //    @Column(nullable = false)
    private Integer deptClassCode; //科室类目编号 参见：OptionMap.department

//    @Column(nullable = false)
    private Integer doctorNum; //医生人数

//    @Column(nullable = false)
    private Integer hospitalId; //医院id

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "hospitalId",insertable = false, updatable = false)
//    private Hospital hospital; //医院

    public Integer getDeptClassCode() {
        return deptClassCode;
    }

    public void setDeptClassCode(Integer deptClassCode) {
        this.deptClassCode = deptClassCode;
    }

    public Integer getDoctorNum() {
        return doctorNum;
    }

    public void setDoctorNum(Integer doctorNum) {
        this.doctorNum = doctorNum;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
}