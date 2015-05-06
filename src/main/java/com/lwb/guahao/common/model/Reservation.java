package com.lwb.guahao.common.model;

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
    private Integer orderStatus; //订单状态 参见：Constants.OrderStatus

    @Column
    private Date createDateTime; //订单创建日期时间

    @Column
    private Date editDateTime; //订单修改日期时间

    @Column(nullable = false)
    private Integer doctorId; //医生id

    @Column(nullable = false)
    private Integer doctorPerTimeScheduleId;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "doctorPerTimeSchedule",insertable = false, updatable = false)
    private DoctorPerTimeSchedule doctorPerTimeSchedule;

    @Column(nullable = false)
    private Integer perUserId; //个人用户id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perUserId",insertable = false, updatable = false)
    private PerUser perUser; //病人


    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public DoctorPerTimeSchedule getDoctorPerTimeSchedule() {
        return doctorPerTimeSchedule;
    }

    public void setDoctorPerTimeSchedule(DoctorPerTimeSchedule doctorPerTimeSchedule) {
        this.doctorPerTimeSchedule = doctorPerTimeSchedule;
    }

    public Integer getDoctorPerTimeScheduleId() {
        return doctorPerTimeScheduleId;
    }

    public void setDoctorPerTimeScheduleId(Integer doctorPerTimeScheduleId) {
        this.doctorPerTimeScheduleId = doctorPerTimeScheduleId;
    }

    public Date getEditDateTime() {
        return editDateTime;
    }

    public void setEditDateTime(Date editDateTime) {
        this.editDateTime = editDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PerUser getPerUser() {
        return perUser;
    }

    public void setPerUser(PerUser perUser) {
        this.perUser = perUser;
    }

    public Integer getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(Integer perUserId) {
        this.perUserId = perUserId;
    }
}
