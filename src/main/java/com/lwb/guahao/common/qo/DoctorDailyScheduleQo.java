package com.lwb.guahao.common.qo;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.util.lang.DateUtils;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleQoVo;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/25 18:56
 * 医生每日排班查询对象
 */
public class DoctorDailyScheduleQo extends BaseQo{
    private final static Logger logger = Logger.getLogger(DoctorDailyScheduleQo.class);
    private Integer doctorId;
    private Date startDay;
    private Date endDay;
    private Boolean ignoreNoScheduleDay;

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

    /**
     * 从用户视图qo解析出标准qo
     * 注意：
     * 1.起始日期和结束日期都为空，查询今天起一周内的排班
     * 2.起始日期为空，结束日期非空，查询从开始日期起后一周内的排班
     * 3.起始日期非空，结束日期为空，查询结束日期前一周内的排班
     * @param vo
     * @return
     * @throws ParseException
     */
    public static DoctorDailyScheduleQo parse(DoctorDailyScheduleQoVo vo) throws ParseException {
        DoctorDailyScheduleQo qo = new DoctorDailyScheduleQo();
        Integer doctorId = null;
        Date startDay = null;
        Date endDay = null;
        Boolean ignoreNoScheduleDate = null;
        Integer pn = null;
        Integer pageSize = null;


        doctorId = StringUtils.isEmpty(vo.getDoctorId()) ? null : Integer.valueOf(vo.getDoctorId());

        //涉及起始日期、结束日期留空时的查询医生排班的策略
        String startDayStr = vo.getStartDay();
        String endDayStr = vo.getEndDay();
        if(StringUtils.isEmpty(startDayStr) && StringUtils.isEmpty(endDayStr)){//起始日期和结束日期都为空，查询今天起一周内的排班
            DateTime today = DateTime.now().withTimeAtStartOfDay();
            startDay = today.toDate();
            endDay = today.plusDays(6).toDate();

        } else if(StringUtils.isEmpty(startDayStr)){//起始日期为空，结束日期非空，查询从开始日期起后一周内的排班
            endDay = DateUtils.ISODateFormatter.parse(endDayStr);
            startDay = new DateTime(endDay).minusDays(6).toDate();

        } else if(StringUtils.isEmpty(endDayStr)){//起始日期非空，结束日期为空，查询结束日期前一周内的排班
            startDay = DateUtils.ISODateFormatter.parse(startDayStr);
            endDay = new DateTime(startDay).plusDays(6).toDate();
        } else {
            startDay = DateUtils.ISODateFormatter.parse(startDayStr);
            endDay = DateUtils.ISODateFormatter.parse(endDayStr);
        }

        if(!StringUtils.isEmpty(vo.getPn())){
            pn = Integer.valueOf(vo.getPn());
        } else {
            pn = 1;
        }

        if(!StringUtils.isEmpty(vo.getPageSize())){
            pageSize = Integer.valueOf(vo.getPageSize());
        } else{
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        ignoreNoScheduleDate = StringUtils.isEmpty(vo.getIgnoreNoScheduleDay()) ? Boolean.FALSE : Boolean.valueOf(vo.getIgnoreNoScheduleDay());

        qo.setDoctorId(doctorId);
        qo.setStartDay(startDay);
        qo.setEndDay(endDay);
        qo.setIgnoreNoScheduleDay(ignoreNoScheduleDate);
        qo.setPn(pn);
        qo.setPageSize(pageSize);
        return qo;
    }
}
