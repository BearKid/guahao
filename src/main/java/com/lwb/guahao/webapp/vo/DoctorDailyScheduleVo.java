package com.lwb.guahao.webapp.vo;

import com.lwb.guahao.model.Doctor;

import java.util.Date;
import java.util.List;

/**
 * Created by Lu Weibiao on 2015/2/16 22:26.
 */
public class DoctorDailyScheduleVo {
    private Integer id;
    private Integer totalSource; //当日总号源数
    private Integer oddSource; //剩余号源数
    private Integer price; //挂号费
    private Date effectiveDate; //生效日期
    private List<SourceArrangement> sourceArrangementList; //当日各时间段号源分配计划。Json <--> List<DoctorDailyScheduleVo.SourceArrangement>。
    private Doctor doctor;

    /**
     * 某一时间段的号源安排
     */
    public static class SourceArrangement {
        Date startTime; //开始时间
        Date endTime; //结束时间
        Integer totalSource; //号源总数
        Integer oddSource; //剩余号源数
    }
}
