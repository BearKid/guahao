package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.DoctorPerTimeSchedule;

import java.util.Date;
import java.util.List;

/**
 * Created by Lu Weibiao on 2015/2/16 22:26.
 */
public class DoctorDailyScheduleVo {
    private Integer id;
    private Integer totalSource; //当日总号源数
    private Integer oddSource; //剩余号源数
    private Integer price; //挂号费
    private Date effectiveDate; //生效日期
    private List<DoctorPerTimeSchedule> doctorPerTimeScheduleList; //当日各时间段号源分配计划。Json <--> List<DoctorDailyScheduleVo.SourceArrangement>。
    private Doctor doctor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalSource() {
        return totalSource;
    }

    public void setTotalSource(Integer totalSource) {
        this.totalSource = totalSource;
    }

    public Integer getOddSource() {
        return oddSource;
    }

    public void setOddSource(Integer oddSource) {
        this.oddSource = oddSource;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List<DoctorPerTimeSchedule> getDoctorPerTimeScheduleList() {
        return doctorPerTimeScheduleList;
    }

    public void setDoctorPerTimeScheduleList(List<DoctorPerTimeSchedule> doctorPerTimeScheduleList) {
        this.doctorPerTimeScheduleList = doctorPerTimeScheduleList;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
