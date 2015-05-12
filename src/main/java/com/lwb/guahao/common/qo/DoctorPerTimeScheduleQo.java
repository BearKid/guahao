package com.lwb.guahao.common.qo;

import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/5/12 22:20
 */
public class DoctorPerTimeScheduleQo extends BaseQo {
    private Integer id;

    private Date startDateTime; //开始时间

    private Date endDateTime; //结束时间

    private Double price; //挂号费

    private Integer totalSource; //号源总数

    private Integer oddSource; //剩余号源数

    private Integer doctorId;

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
