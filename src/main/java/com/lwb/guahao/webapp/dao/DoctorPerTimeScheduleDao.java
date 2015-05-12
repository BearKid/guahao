package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.model.DoctorPerTimeSchedule;
import com.lwb.guahao.common.qo.DoctorDailyScheduleQo;
import com.lwb.guahao.common.qo.DoctorPerTimeScheduleQo;
import com.lwb.guahao.common.qo.util.QoUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/23 21:06
 */
@Repository
public class DoctorPerTimeScheduleDao extends BaseHibernateDao<DoctorPerTimeSchedule>{
    /**
     * 获取分页
     * @param qo
     * @return
     */
    public Paging<DoctorPerTimeSchedule> getPagingBy(final DoctorDailyScheduleQo qo) {
        final List params = new ArrayList();
        final String selectHql = "select s";
        final String countHql = "select count(*)";
        StringBuilder fromHqlBuilder = new StringBuilder(" from DoctorPerTimeSchedule s where s.doctorId = ? and s.startDateTime >= ? and s.startDateTime < ?");
        Date startDateTime = new DateTime(qo.getStartDay()).withTimeAtStartOfDay().toDate();
        Date endDateTime = new DateTime(qo.getEndDay()).plusDays(1).withTimeAtStartOfDay().toDate();
        params.add(qo.getDoctorId());
        params.add(startDateTime);
        params.add(endDateTime);

        final String fromHql = fromHqlBuilder.toString();

        Integer firstIndex = QoUtil.getFirstIndex(qo);
        Integer pageSize = QoUtil.getPageSize(qo);
        Integer pn = QoUtil.getPn(qo);

        List<DoctorPerTimeSchedule> doctorPerTimeScheduleList = pagingQuery(selectHql + fromHql,params, firstIndex, pageSize);

        Long totalSize = (Long)unique(countHql + fromHql,params);

        Paging<DoctorPerTimeSchedule> paging = new Paging<DoctorPerTimeSchedule>(doctorPerTimeScheduleList, pn, pageSize,totalSize.intValue());
        return paging;
    }

    /**
     * 获取符合查询条件的记录集分页
     * @param doctorPerTimeScheduleQo
     * @return
     */
    public Paging<DoctorPerTimeSchedule> getPagingBy(DoctorPerTimeScheduleQo doctorPerTimeScheduleQo) {
        Object[] fromHqlHolder = constructFromHqlBy(doctorPerTimeScheduleQo);
        final String fromHql = (String)fromHqlHolder[0];
        final List params = (List)fromHqlHolder[1];

        final String selectHql = "select s " + fromHql + " order by s.startDateTime asc";
        final String countHql = "select count(*) " + fromHql;

        Integer pn = QoUtil.getPn(doctorPerTimeScheduleQo);
        Integer pageSize = QoUtil.getPageSize(doctorPerTimeScheduleQo);
        Integer firstIndex = QoUtil.getFirstIndex(doctorPerTimeScheduleQo);

        List<DoctorPerTimeSchedule> doctorPerTimeScheduleList = pagingQuery(selectHql, params, firstIndex, pageSize);
        Long totalSize = (Long)unique(countHql,params);

        return new Paging<DoctorPerTimeSchedule>(doctorPerTimeScheduleList,pn,pageSize,totalSize.intValue());
    }

    private Object[] constructFromHqlBy(DoctorPerTimeScheduleQo doctorPerTimeScheduleQo) {
        StringBuilder fromHqlBuilder = new StringBuilder(" from DoctorPerTimeSchedule s where 1=1");
        List params = new ArrayList();

       Integer id = doctorPerTimeScheduleQo.getId();
        if(id != null){
            fromHqlBuilder.append(" and id = ?");
            params.add(id);
        }

        Date startDateTime = doctorPerTimeScheduleQo.getStartDateTime(); //开始时间
        if(startDateTime != null){
            fromHqlBuilder.append(" and startDateTime >= ?");
            params.add(startDateTime);
        }
       Date endDateTime = doctorPerTimeScheduleQo.getEndDateTime(); //结束时间
        if(endDateTime != null){
            fromHqlBuilder.append(" and endDateTime <= ?");
            params.add(endDateTime);
        }
        //TODO
//        Double price; //挂号费
//
//       Integer totalSource; //号源总数
//
//        Integer oddSource; //剩余号源数

        Integer doctorId = doctorPerTimeScheduleQo.getDoctorId();
        if(doctorId != null){
            fromHqlBuilder.append(" and doctorId = ?");
            params.add(doctorId);
        }

        return new Object[]{
                fromHqlBuilder.toString(),
                params
        };
    }
}
