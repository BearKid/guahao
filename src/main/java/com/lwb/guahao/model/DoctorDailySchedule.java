package com.lwb.guahao.model;

import javax.persistence.*;
import javax.print.Doc;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/16 22:11.
 */

/**
 * 医生每日的号源计划
 */
@Entity
public class DoctorDailySchedule implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer totalSource; //当日总号源数

    @Column
    private Integer oddSource; //剩余号源数

    @Column
    private Double price; //挂号费

    @Column
    @Temporal(TemporalType.DATE)
    private Date effectiveDate; //生效日期

    @Column
    @Temporal(TemporalType.DATE)
    private Date modifyDate; //修改日期

    @Column
    private String sourceArrangement; //当日各时间段号源分配计划。Json <--> List<DoctorDailyScheduleVo.SourceArrangement>。

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor; //医生

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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

    public String getSourceArrangement() {
        return sourceArrangement;
    }

    public void setSourceArrangement(String sourceArrangement) {
        this.sourceArrangement = sourceArrangement;
    }

    public Integer getTotalSource() {
        return totalSource;
    }

    public void setTotalSource(Integer totalSource) {
        this.totalSource = totalSource;
    }
}
