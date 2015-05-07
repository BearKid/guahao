package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.model.Reservation;
import com.lwb.guahao.common.option.util.OrderStatusUtil;
import com.lwb.guahao.common.util.lang.DateUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/5/7 16:09
 */
public class ReservationVo {
    private String id;

    private String orderStatusCode; //订单状态 参见：Constants.OrderStatus

    private String createDateTime; //订单创建日期时间

    private String modifyDateTime; //订单修改日期时间

    private String doctorId; //医生id

    private String doctorName; //医生姓名

    private String hospitalId; //医院id

    private String hospitalName; //医院名称

    private String doctorPerTimeScheduleId;//医生排班id

    private String perUserId; //个人用户id

    private String perUserName; //个人用户名

    private String perUserIdCard; //个人用户身份证号

    /** 相较于Reservation 新增字段**/
    private String orderStatusName; //订单状态名称

    private String visitDoctorDay; //就诊日期（yyyy年MM月dd日（E））

    /** Reservation的关联表的实体对象VO**/
    private DoctorPerTimeScheduleVo doctorPerTimeSchedule;
    private DoctorVo doctor;
    private HospitalVo hospitalVo;
    private PerUserVo perUser; //病人

    /**
     * 从标准Reservation转换为用户视图vo
     * Reservation关联表的实体对象不被转换
     * @param reservation
     * @return
     */
    public static ReservationVo parse(Reservation reservation) {
        if(reservation == null) return null;
        ReservationVo reservationVo = new ReservationVo();
        BeanUtils.copyProperties(reservation, reservationVo);

        Integer id = reservation.getId();
        reservationVo.setId(id == null ? null : id.toString());

        Integer orderStatusCode = reservation.getOrderStatusCode(); //订单状态 参见：Constants.OrderStatus
        reservationVo.setOrderStatusCode(orderStatusCode == null ? null : orderStatusCode.toString());
        reservationVo.setOrderStatusName(OrderStatusUtil.getOrderStatusName(orderStatusCode));

        Date createDateTime = reservation.getCreateDateTime(); //订单创建日期时间
        reservationVo.setCreateDateTime(createDateTime == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(createDateTime));

        Date modifyDateTime = reservation.getModifyDateTime(); //订单修改日期时间
        reservationVo.setModifyDateTime(modifyDateTime == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(modifyDateTime));

        Integer doctorId = reservation.getDoctorId(); //医生id
        reservationVo.setDoctorId(doctorId == null ? null : doctorId.toString());

        Integer hospitalId = reservation.getHospitalId();//医院id
        reservationVo.setHospitalId(hospitalId == null ? null : hospitalId.toString());

        Integer doctorPerTimeScheduleId = reservation.getDoctorPerTimeScheduleId();//医生排班id
        reservationVo.setDoctorPerTimeScheduleId(doctorPerTimeScheduleId == null ? null : doctorPerTimeScheduleId.toString());

        Integer perUserId = reservation.getPerUserId(); //个人用户id
        reservationVo.setPerUserId(perUserId == null ? null : perUserId.toString());

        return reservationVo;
    }


    public String getVisitDoctorDay() {
        return visitDoctorDay;
    }

    public void setVisitDoctorDay(String visitDoctorDay) {
        this.visitDoctorDay = visitDoctorDay;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public DoctorVo getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorVo doctor) {
        this.doctor = doctor;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public DoctorPerTimeScheduleVo getDoctorPerTimeSchedule() {
        return doctorPerTimeSchedule;
    }

    public void setDoctorPerTimeSchedule(DoctorPerTimeScheduleVo doctorPerTimeSchedule) {
        this.doctorPerTimeSchedule = doctorPerTimeSchedule;
    }

    public String getDoctorPerTimeScheduleId() {
        return doctorPerTimeScheduleId;
    }

    public void setDoctorPerTimeScheduleId(String doctorPerTimeScheduleId) {
        this.doctorPerTimeScheduleId = doctorPerTimeScheduleId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public HospitalVo getHospitalVo() {
        return hospitalVo;
    }

    public void setHospitalVo(HospitalVo hospitalVo) {
        this.hospitalVo = hospitalVo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModifyDateTime() {
        return modifyDateTime;
    }

    public void setModifyDateTime(String modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }

    public String getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public PerUserVo getPerUser() {
        return perUser;
    }

    public void setPerUser(PerUserVo perUser) {
        this.perUser = perUser;
    }

    public String getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(String perUserId) {
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
