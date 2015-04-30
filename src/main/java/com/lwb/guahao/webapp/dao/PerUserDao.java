package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.PerUser;
import org.springframework.stereotype.Repository;

/**
 * Created by Lu Weibiao on 2015/2/14 21:27.
 */
@Repository
public class PerUserDao extends BaseHibernateDao<PerUser> {
    public PerUser getByEmailAndPwd(String email, String password) {
        String hql = "from PerUser p where p.email = ? and password = ?";
        Object[] params = new Object[]{
                email,password
        };
        return (PerUser) unique(hql, params);
    }

    public PerUser getByPhoneAndPwd(String phone, String password) {
        String hql = "from PerUser p where p.phone = ? and p.password = ?";
        Object[] params = new Object[]{
                phone,password
        };
        return (PerUser) unique(hql,params);
    }

    public boolean existsByEmail(String email) {
        String hql = "select id from PerUser p where p.email = ?";
        Object[] params = new Object[]{
                email
        };
        Integer id = (Integer)unique(hql,params);
        return id != null;
    }

    public boolean existsByMobilePhone(String mobilePhone) {
        String hql = "select id from PerUser p where p.mobilePhone = ?";
        Object[] params = new Object[]{
                mobilePhone
        };
        Integer id = (Integer) unique(hql, params);
        return id != null;
    }

    public boolean existsByIdCard(String idCard) {
        String hql = "select id from PerUser p where p.idCard = ?";
        Object[] params = new Object[]{
                idCard
        };
        Integer id = (Integer) unique(hql, params);
        return id != null;
    }
}
