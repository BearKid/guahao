package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.util.DateUtils;
import com.lwb.guahao.qo.DoctorDailyScheduleQo;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/25 22:22
 * 医生每天排班查询对象VO
 */
public class DoctorDailyScheduleQoVo {
    Integer doctorId;
    String startDay;
    String endDay;
    String ignoreNoScheduleDay;

    public static DoctorDailyScheduleQoVo parse(DoctorDailyScheduleQo qo) {
        DoctorDailyScheduleQoVo vo = new DoctorDailyScheduleQoVo();
        Integer doctorId = null;
        String startDay = null;
        String endDay = null;
        String ignoreNoScheduleDate = null;
        try{
            doctorId = qo.getDoctorId();
        } catch (Exception e){
            System.out.println(DoctorDailyScheduleQo.class + " - doctorId");
        }
        try{
            startDay = DateUtils.ISODateFormatter.format(qo.getStartDay());
        } catch (Exception e){
            System.out.println(DoctorDailyScheduleQo.class + " - startDay");
        }
        try{
            endDay = DateUtils.ISODateFormatter.format(qo.getEndDay());
        } catch (Exception e){
            System.out.println(DoctorDailyScheduleQo.class + " - endDay");
        }

        ignoreNoScheduleDate = qo.getIgnoreNoScheduleDay() ? "true" :"false";
        vo.setDoctorId(doctorId);
        vo.setStartDay(startDay);
        vo.setEndDay(endDay);
        vo.setIgnoreNoScheduleDay(ignoreNoScheduleDate);
        return vo;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getIgnoreNoScheduleDay() {
        return ignoreNoScheduleDay;
    }

    public void setIgnoreNoScheduleDay(String ignoreNoScheduleDay) {
        this.ignoreNoScheduleDay = ignoreNoScheduleDay;
    }
}
