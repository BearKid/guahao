package com.lwb.guahao.webapp.vo;

import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/5/5 17:52
 */
public class DepartmentVo {
    private String deptClassCode;
    private String deptClassName;
    private String doctorNum;
    private List<DepartmentVo> subDepartmentList;

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

    public String getDoctorNum() {
        return doctorNum;
    }

    public void setDoctorNum(String doctorNum) {
        this.doctorNum = doctorNum;
    }

    public List<DepartmentVo> getSubDepartmentList() {
        return subDepartmentList;
    }

    public void setSubDepartmentList(List<DepartmentVo> subDepartmentList) {
        this.subDepartmentList = subDepartmentList;
    }
}
