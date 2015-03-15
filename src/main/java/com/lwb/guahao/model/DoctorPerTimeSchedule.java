package com.lwb.guahao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @autor:Lu Weibiao Date: 2015/3/13 22:13
 */

/**
 * 某天的某一时间段的号源安排
 */
@Entity
public class DoctorPerTimeSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date startDateTime; //开始时间

    @Column(nullable = false)
    private Date endDateTime; //结束时间

    @Column
    private Double price; //挂号费

    @Column(nullable = false)
    private Integer totalSource; //号源总数

    @Column(nullable = false)
    Integer oddSource; //剩余号源数

    @Column(nullable = false)
    private Integer doctorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId", insertable = false, updatable = false)
    private Doctor doctor; //医生

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Integer getTotalSource() {
        return totalSource;
    }

    public void setTotalSource(Integer totalSource) {
        this.totalSource = totalSource;
    }
}
