package com.lwb.guahao.model;

/**
 * Created by Lu Weibiao on 2015/2/16 23:02.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 挂号预约订单
 */
@Entity
public class Reservation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double price; //挂号费

    @Column
    private Date startTime; //号源生效日期时间

    @Column
    private Date endTime; //号源失效日期时间

    @Column
    private Integer orderStatus; //订单状态 参见：Constants.OrderStatus

    @Column
    private Date createDatetime; //订单创建日期时间

    @Column
    private Date modifyDatetime; //订单修改日期时间

    @Column(nullable = false)
    private Integer perUserId; //个人用户id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perUserId",insertable = false, updatable = false)
    private PerUser perUser; //病人

    @Column(nullable = false)
    private Integer doctorId; //医生id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId",insertable = false, updatable = false)
    private Doctor doctor; //医生

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(Integer perUserId) {
        this.perUserId = perUserId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public PerUser getPerUser() {
        return perUser;
    }

    public void setPerUser(PerUser perUser) {
        this.perUser = perUser;
    }
}
