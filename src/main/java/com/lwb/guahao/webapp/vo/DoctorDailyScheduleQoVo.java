package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.util.lang.DateUtils;
import com.lwb.guahao.common.qo.DoctorDailyScheduleQo;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/25 22:22
 * 医生每天排班查询对象VO
 */
public class DoctorDailyScheduleQoVo {
    String doctorId;
    String startDay;
    String endDay;
    String ignoreNoScheduleDay;
    String pn;
    String pageSize;

    public static DoctorDailyScheduleQoVo parse(DoctorDailyScheduleQo qo) {
        DoctorDailyScheduleQoVo vo = new DoctorDailyScheduleQoVo();
        String  doctorId = null;
        String startDay = null;
        String endDay = null;
        String ignoreNoScheduleDate = null;

        doctorId = (qo.getDoctorId() == null) ? null : qo.getDoctorId().toString();
        startDay = DateUtils.ISODateFormatter.format(qo.getStartDay());
        endDay = DateUtils.ISODateFormatter.format(qo.getEndDay());
        ignoreNoScheduleDate = (qo.getIgnoreNoScheduleDay() == null) ? Boolean.FALSE.toString() : qo.getIgnoreNoScheduleDay().toString();

        vo.setDoctorId(doctorId);
        vo.setStartDay(startDay);
        vo.setEndDay(endDay);
        vo.setIgnoreNoScheduleDay(ignoreNoScheduleDate);
        return vo;
    }

    public String  getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
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

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }
}
