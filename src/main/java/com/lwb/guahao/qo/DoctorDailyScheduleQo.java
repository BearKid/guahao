package com.lwb.guahao.qo;

import com.lwb.guahao.common.util.DateUtils;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleQoVo;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/25 18:56
 * 医生每日排班查询对象
 */
public class DoctorDailyScheduleQo extends BaseQo{
    Integer doctorId;
    Date startDay;
    Date endDay;
    Boolean ignoreNoScheduleDay;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public Boolean getIgnoreNoScheduleDay() {
        return ignoreNoScheduleDay;
    }

    public void setIgnoreNoScheduleDay(Boolean ignoreNoScheduleDay) {
        this.ignoreNoScheduleDay = ignoreNoScheduleDay;
    }

    public static DoctorDailyScheduleQo parse(DoctorDailyScheduleQoVo vo) {
        DoctorDailyScheduleQo qo = new DoctorDailyScheduleQo();
        Integer doctorId = null;
        Date startDay = null;
        Date endDay = null;
        Boolean ignoreNoScheduleDate = null;
        try{
            doctorId = Integer.valueOf(vo.getDoctorId());
        } catch (Exception e){
            System.out.println(DoctorDailyScheduleQo.class + " - doctorId");
        }
        try{
            startDay = DateUtils.ISODateFormatter.parse(vo.getStartDay());
        } catch (Exception e){
            startDay = DateTime.now().minusDays(3).toDate();
            System.out.println(DoctorDailyScheduleQo.class + " - startDay");
        }
        try{
            endDay = DateUtils.ISODateFormatter.parse(vo.getEndDay());
        } catch (Exception e){
            endDay = DateTime.now().plusDays(3).toDate();
            System.out.println(DoctorDailyScheduleQo.class + " - endDay");
        }

        ignoreNoScheduleDate = "true".equals(vo.getIgnoreNoScheduleDay()) ? true : false;
        qo.setDoctorId(doctorId);
        qo.setStartDay(startDay);
        qo.setEndDay(endDay);
        qo.setIgnoreNoScheduleDay(ignoreNoScheduleDate);
        return qo;
    }
}
