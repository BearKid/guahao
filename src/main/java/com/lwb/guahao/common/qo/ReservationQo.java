package com.lwb.guahao.common.qo;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.util.lang.DateUtils;
import com.lwb.guahao.webapp.vo.ReservationQoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/5/6 23:12
 * 挂号预约记录查询条件
 */
public class ReservationQo extends BaseQo {
    private Integer id;

    private Integer orderStatusCode; //订单状态 参见：Constants.OrderStatus

    private Integer[] orderStatusCodeIn;//设置多个订单状态

    private Date createDateTimeStart; //订单创建日期时间-起始时间

    private Date createDateTimeEnd; //订单创建日期时间-结束时间

    private Date modifyDateTimeStart; //订单修改日期时间-起始时间

    private Date modifyDateTimeEnd; //订单修改日期时间-结束时间

    private Integer doctorId; //医生id

    private Integer doctorPerTimeScheduleId;

    private Integer perUserId; //个人用户id


    /**
     * 从vo转换为标准运算对象
     * @param reservationQoVo
     * @return
     */
    public static ReservationQo parse(ReservationQoVo reservationQoVo) throws ParseException {
        ReservationQo reservationQo = new ReservationQo();
        BeanUtils.copyProperties(reservationQoVo, reservationQo);

        String id = reservationQoVo.getId();
        reservationQo.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));

        String orderStatus = reservationQoVo.getOrderStatusCode();
        reservationQo.setOrderStatusCode(StringUtils.isEmpty(orderStatus) ? null : Integer.valueOf(orderStatus));

        String[] orderStatusStrIn = reservationQoVo.getOrderStatusCodeIn();
        if(orderStatusStrIn != null && orderStatusStrIn.length > 0){
            Integer[] orderStatusIn = new Integer[orderStatusStrIn.length];
            for(int i = 0; i < orderStatusStrIn.length; i++){
                orderStatusIn[i] = Integer.valueOf(orderStatusStrIn[i]);
            }
        }

        //日期时间转换
        String createDateTimeStart = reservationQoVo.getCreateDateTimeStart();
        reservationQo.setCreateDateTimeStart(StringUtils.isEmpty(createDateTimeStart) ? null : DateUtils.ISODateFormatter.parse(createDateTimeStart));

        String createDateTimeEnd = reservationQoVo.getCreateDateTimeEnd();
        reservationQo.setCreateDateTimeEnd(StringUtils.isEmpty(createDateTimeEnd) ? null : DateUtils.ISODateFormatter.parse(createDateTimeEnd));

        String modifyDateTimeStart = reservationQoVo.getModifyDateTimeStart();
        reservationQo.setModifyDateTimeStart(StringUtils.isEmpty(modifyDateTimeStart) ? null : DateUtils.ISODateFormatter.parse(modifyDateTimeStart));

        String modifyDateTimeEnd = reservationQoVo.getModifyDateTimeEnd();
        reservationQo.setModifyDateTimeEnd(StringUtils.isEmpty(modifyDateTimeEnd) ? null : DateUtils.ISODateFormatter.parse(modifyDateTimeEnd));

        String doctorId = reservationQoVo.getDoctorId();
        reservationQo.setDoctorId(StringUtils.isEmpty(doctorId) ? null : Integer.valueOf(doctorId));

        String doctorPerTimeScheduleId = reservationQoVo.getDoctorPerTimeScheduleId();
        reservationQo.setDoctorPerTimeScheduleId(StringUtils.isEmpty(doctorPerTimeScheduleId) ? null : Integer.valueOf(doctorPerTimeScheduleId));

        String perUserId = reservationQoVo.getPerUserId();
        reservationQo.setPerUserId(StringUtils.isEmpty(perUserId) ? null : Integer.valueOf(perUserId));

        //当前页码
        String pn = reservationQoVo.getPn();
        reservationQo.setPn(StringUtils.isEmpty(pn) ? 1 : Integer.valueOf(pn));
        reservationQo.setPageSize(Constants.DEFAULT_PAGE_SIZE);

        return reservationQo;
    }

    public Date getCreateDateTimeEnd() {
        return createDateTimeEnd;
    }

    public void setCreateDateTimeEnd(Date createDateTimeEnd) {
        this.createDateTimeEnd = createDateTimeEnd;
    }

    public Date getCreateDateTimeStart() {
        return createDateTimeStart;
    }

    public void setCreateDateTimeStart(Date createDateTimeStart) {
        this.createDateTimeStart = createDateTimeStart;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getDoctorPerTimeScheduleId() {
        return doctorPerTimeScheduleId;
    }

    public void setDoctorPerTimeScheduleId(Integer doctorPerTimeScheduleId) {
        this.doctorPerTimeScheduleId = doctorPerTimeScheduleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModifyDateTimeEnd() {
        return modifyDateTimeEnd;
    }

    public void setModifyDateTimeEnd(Date modifyDateTimeEnd) {
        this.modifyDateTimeEnd = modifyDateTimeEnd;
    }

    public Date getModifyDateTimeStart() {
        return modifyDateTimeStart;
    }

    public void setModifyDateTimeStart(Date modifyDateTimeStart) {
        this.modifyDateTimeStart = modifyDateTimeStart;
    }

    public Integer getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(Integer orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public Integer[] getOrderStatusCodeIn() {
        return orderStatusCodeIn;
    }

    public void setOrderStatusCodeIn(Integer[] orderStatusCodeIn) {
        this.orderStatusCodeIn = orderStatusCodeIn;
    }

    public Integer getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(Integer perUserId) {
        this.perUserId = perUserId;
    }
}
