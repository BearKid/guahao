package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.common.util.DateUtils;
import com.lwb.guahao.model.DoctorPerTimeSchedule;
import org.springframework.beans.BeanUtils;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/23 21:34
 */
public class DoctorPerTimeScheduleVo {
    private Integer id;

    private Double price; //挂号费

    private Integer totalSource; //号源总数

    Integer oddSource; //剩余号源数

    private Integer doctorId;
    /*****相较于DoctorPerTimeSchedule 有差异的字段*****/
    private String startTime; //开始时间*
    private String endTime; //结束时间*

    public static DoctorPerTimeScheduleVo parse(DoctorPerTimeSchedule doctorPerTimeSchedule){
        DoctorPerTimeScheduleVo vo = new DoctorPerTimeScheduleVo();
        BeanUtils.copyProperties(doctorPerTimeSchedule, vo);

        /*时间日期格式化*/
        vo.setStartTime(DateUtils.timeFormatter.format(doctorPerTimeSchedule.getStartDateTime()));
        vo.setEndTime(DateUtils.timeFormatter.format(doctorPerTimeSchedule.getEndDateTime()));
        return vo;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTotalSource() {
        return totalSource;
    }

    public void setTotalSource(Integer totalSource) {
        this.totalSource = totalSource;
    }

    public Integer getOddSource() {
        return oddSource;
    }

    public void setOddSource(Integer oddSource) {
        this.oddSource = oddSource;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
