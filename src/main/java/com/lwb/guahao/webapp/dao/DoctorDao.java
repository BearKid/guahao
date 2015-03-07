package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.Doctor;
import org.springframework.stereotype.Repository;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 22:47
 */
@Repository
public class DoctorDao extends BaseHibernateDao{
    public void update(Doctor doctor){
        hibernateTemplate.update(doctor);
    }

    public Doctor uniqueByAccountAndPwd(String accountName, String pwd) {
        String hql = "from Doctor as d where d.accountName = ? and d.password = ?";
        return (Doctor)unique(hql,accountName,pwd);
    }

    public Integer save(Doctor doctor) {
        return (Integer)hibernateTemplate.save(doctor);
    }
}
