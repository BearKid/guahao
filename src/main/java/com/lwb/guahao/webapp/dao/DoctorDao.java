package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.model.Doctor;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 22:47
 */
@Repository
public class DoctorDao extends BaseHibernateDao {
    public void update(Doctor doctor) {
        hibernateTemplate.update(doctor);
    }

    public Doctor uniqueByAccountAndPwd(String accountName, String pwd) {
        String hql = "from Doctor as d where d.accountName = ? and d.password = ?";
        return (Doctor) unique(hql, accountName, pwd);
    }

    public Integer save(Doctor doctor) {
        return (Integer) hibernateTemplate.save(doctor);
    }

    public Paging<Doctor> getDoctorsPagingBy(Integer hospitalId, String name, Integer deptClasCode, String accountName,
                                           final int pn, final int pageSize) {
        final String selectHql = "select d ";
        final String countHql = "select count(*) ";
        final StringBuilder fromHqlBuilder = new StringBuilder("from Doctor d where 1=1");
        final List params = new ArrayList();

        if (hospitalId != null) {
            fromHqlBuilder.append(" and d.hospital.id = ?");
            params.add(hospitalId);
        }
        if (!StringUtils.isEmpty(name)) {
            fromHqlBuilder.append(" and d.name = ?");
            params.add(name);
        }
        if (deptClasCode != null) {
            fromHqlBuilder.append(" and d.deptClassCode = ?");
            params.add(deptClasCode);
        }
        if (!StringUtils.isEmpty(accountName)) {
            fromHqlBuilder.append(" and d.accountName = ?");
            params.add(accountName);
        }
        fromHqlBuilder.append(" order by d.createDateTime");

        final String finalSelectHql = selectHql + fromHqlBuilder.toString();
        List<Doctor> doctors = hibernateTemplate.execute(new HibernateCallback<List<Doctor>>() {
            @Override
            public List<Doctor> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(finalSelectHql);
                query.setFirstResult((pn - 1) * pageSize);
                query.setMaxResults(pageSize);
                setParameters(query, params);
                return query.list();
            }
        });

        final String finalCountHql = countHql + fromHqlBuilder.toString();
        Long totalSize = hibernateTemplate.execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(finalCountHql);
                setParameters(query,params);
                return (Long)query.uniqueResult();
            }
        });
        return new Paging<Doctor>(doctors,pn,pageSize,totalSize.intValue());

    }
}
