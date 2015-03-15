package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.Doctor;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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

    public List<Doctor> getDoctorsBy(Integer hospitalId, String name, Integer deptClasCode, String accountName,
                                     final int pn, final int pageSize) {
        final StringBuilder hqlBuilder = new StringBuilder("from Doctor d where 1=1");
        List params = new ArrayList();
        if (hospitalId != null) {
            hqlBuilder.append(" and d.hospital.id = ?");
            params.add(hospitalId);
        }
        if (!StringUtils.isEmpty(name)) {
            hqlBuilder.append(" and d.name = ?");
            params.add(name);
        }
        if (deptClasCode != null) {
            hqlBuilder.append(" and d.deptClassCode = ?");
            params.add(deptClasCode);
        }
        if (!StringUtils.isEmpty(accountName)) {
            hqlBuilder.append(" and d.accountName = ?");
            params.add(accountName);
        }
        hqlBuilder.append(" order by d.createDateTime");
        return hibernateTemplate.execute(new HibernateCallback<List<Doctor>>() {
            @Override
            public List<Doctor> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hqlBuilder.toString());
                query.setFirstResult((pn - 1) * pageSize);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });

    }
}
