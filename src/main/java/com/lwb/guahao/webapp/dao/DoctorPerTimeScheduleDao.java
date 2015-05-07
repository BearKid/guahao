package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.model.DoctorPerTimeSchedule;
import com.lwb.guahao.common.qo.DoctorDailyScheduleQo;
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
}
