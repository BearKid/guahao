package com.lwb.guahao.webapp.vo;

/**
 * User: Lu Weibiao
 * String: 2015/5/7 15:17
 */
public class ReservationQoVo{
    private String id;

    private String orderStatusCode; //订单状态 参见：Constants.OrderStatus

    private String[] orderStatusCodeIn;//设置多个订单状态

    private String createDateTimeStart; //订单创建日期时间-起始时间

    private String createDateTimeEnd; //订单创建日期时间-结束时间

    private String modifyDateTimeStart; //订单修改日期时间-起始时间

    private String modifyDateTimeEnd; //订单修改日期时间-结束时间

    private String doctorId; //医生id

    private String doctorPerTimeScheduleId;

    private String perUserId; //个人用户id

    private String pn;

    public String getCreateDateTimeEnd() {
        return createDateTimeEnd;
    }

    public void setCreateDateTimeEnd(String createDateTimeEnd) {
        this.createDateTimeEnd = createDateTimeEnd;
    }

    public String getCreateDateTimeStart() {
        return createDateTimeStart;
    }

    public void setCreateDateTimeStart(String createDateTimeStart) {
        this.createDateTimeStart = createDateTimeStart;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorPerTimeScheduleId() {
        return doctorPerTimeScheduleId;
    }

    public void setDoctorPerTimeScheduleId(String doctorPerTimeScheduleId) {
        this.doctorPerTimeScheduleId = doctorPerTimeScheduleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModifyDateTimeEnd() {
        return modifyDateTimeEnd;
    }

    public void setModifyDateTimeEnd(String modifyDateTimeEnd) {
        this.modifyDateTimeEnd = modifyDateTimeEnd;
    }

    public String getModifyDateTimeStart() {
        return modifyDateTimeStart;
    }

    public void setModifyDateTimeStart(String modifyDateTimeStart) {
        this.modifyDateTimeStart = modifyDateTimeStart;
    }

    public String getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public String[] getOrderStatusCodeIn() {
        return orderStatusCodeIn;
    }

    public void setOrderStatusCodeIn(String[] orderStatusCodeIn) {
        this.orderStatusCodeIn = orderStatusCodeIn;
    }

    public String getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(String perUserId) {
        this.perUserId = perUserId;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }
}
