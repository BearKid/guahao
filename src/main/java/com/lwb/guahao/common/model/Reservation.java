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

    @Column(nullable = false)
    private Integer orderStatusCode; //订单状态 参见：Constants.OrderStatus

    @Column(nullable = false)
    private Date createDateTime; //订单创建日期时间

    @Column
    private Date modifyDateTime; //订单修改日期时间

    @Column(nullable = false)
    private Integer doctorId; //医生id

    @Column(nullable = false,length = 10)
    private String doctorName;//医生姓名

    @Column(nullable = false)
    private Integer hospitalId; //医院id

    @Column(nullable = false, length = 50)
    private String hospitalName; //医院名称

    @Column(nullable = false)
    private Integer doctorPerTimeScheduleId;

    @Column(nullable = false)
    private Integer perUserId; //个人用户id

    @Column(nullable = false, length = 10)
    private String perUserName; //个人用户姓名

    @Column(nullable = false, length = 18)
    private String perUserIdCard; //个人用户身份证号

    /********* 关联表 *************/
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "doctorPerTimeScheduleId",insertable = false, updatable = false)
    private DoctorPerTimeSchedule doctorPerTimeSchedule;

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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModifyDateTime() {
        return modifyDateTime;
    }

    public void setModifyDateTime(Date modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }

    public Integer getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(Integer orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
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

    public String getPerUserIdCard() {
        return perUserIdCard;
    }

    public void setPerUserIdCard(String perUserIdCard) {
        this.perUserIdCard = perUserIdCard;
    }

    public String getPerUserName() {
        return perUserName;
    }

    public void setPerUserName(String perUserName) {
        this.perUserName = perUserName;
    }
}
