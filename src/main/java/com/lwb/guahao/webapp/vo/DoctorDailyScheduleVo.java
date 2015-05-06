package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.model.Doctor;

import java.util.List;

/**
 * Created by Lu Weibiao on 2015/2/16 22:26.
 */
public class DoctorDailyScheduleVo {
    private Integer id; //伪id标识
    private Integer totalSource; //当日总号源数
    private Integer oddSource; //剩余号源数
    private Double price; //挂号费
    private String takeEffectiveDate; //生效日期
    private List<DoctorPerTimeScheduleVo> doctorPerTimeScheduleList; //当日各时间段号源分配计划。Json <--> List<DoctorDailyScheduleVo.SourceArrangement>。
    private Integer doctorId;
    private Doctor doctor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTakeEffectiveDate() {
        return takeEffectiveDate;
    }

    public void setTakeEffectiveDate(String takeEffectiveDate) {
        this.takeEffectiveDate = takeEffectiveDate;
    }

    public List<DoctorPerTimeScheduleVo> getDoctorPerTimeScheduleList() {
        return doctorPerTimeScheduleList;
    }

    public void setDoctorPerTimeScheduleList(List<DoctorPerTimeScheduleVo> doctorPerTimeScheduleList) {
        this.doctorPerTimeScheduleList = doctorPerTimeScheduleList;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
