package com.lwb.guahao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/16 22:11.
 */

/**
 * 医生每日的号源计划
 */
@Deprecated
@Entity
public class DoctorDailySchedule implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double price; //挂号费

    @Column
    @Temporal(TemporalType.DATE)
    private Date takeEffectDate; //生效日期

    @Column
    private Date editDateTime; //修改日期

    @Column(nullable = false)
    private Integer doctorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId", insertable = false, updatable = false)
    private Doctor doctor; //医生

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getTakeEffectDate() {
        return takeEffectDate;
    }

    public void setTakeEffectDate(Date takeEffectDate) {
        this.takeEffectDate = takeEffectDate;
    }

    public Date getEditDateTime() {
        return editDateTime;
    }

    public void setEditDateTime(Date editDateTime) {
        this.editDateTime = editDateTime;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
