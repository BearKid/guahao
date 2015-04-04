package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.model.DoctorPerTimeSchedule;
import com.lwb.guahao.qo.DoctorDailyScheduleQo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @autor: Lu Weibiao
 * Date: 2015/3/23 21:06
 */
@Repository
public class DoctorPerTimeScheduleDao extends BaseHibernateDao{
    /**
     * 获取分页
     * @param qo
     * @return
     */
    public Paging<DoctorPerTimeSchedule> getPagingByDoctorId(final DoctorDailyScheduleQo qo) {
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

        List<DoctorPerTimeSchedule> doctorPerTimeScheduleList = hibernateTemplate.execute(new HibernateCallback<List<DoctorPerTimeSchedule>>() {
            @Override
            public List<DoctorPerTimeSchedule> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(selectHql + fromHql);
                setParameters(query,params);
                query.setFirstResult(qo.getFirstIndex());
                query.setMaxResults(qo.getPageSize());
                return query.list();
            }
        });

        Long totalSize = hibernateTemplate.execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(countHql + fromHql);
                setParameters(query,params);
                return (Long)query.uniqueResult();
            }
        });
        Paging<DoctorPerTimeSchedule> paging = new Paging<DoctorPerTimeSchedule>(doctorPerTimeScheduleList,qo.getPn(),qo.getPageSize(),totalSize.intValue());
        return paging;
    }
}
