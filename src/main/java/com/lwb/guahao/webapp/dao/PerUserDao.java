package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.PerUser;
import org.springframework.stereotype.Repository;

/**
 * Created by Lu Weibiao on 2015/2/14 21:27.
 */
@Repository
public class PerUserDao extends BaseHibernateDao {
    public PerUser get(Integer id) {
        return hibernateTemplate.get(PerUser.class, id);
    }

    public void saveOrUpdate(PerUser perUser) {
        hibernateTemplate.saveOrUpdate(perUser);
    }

    public Integer save(PerUser user) {
        return (Integer) hibernateTemplate.save(user);
    }

    public PerUser getByEmailAndPwd(String email, String password) {
        String hql = "from PerUser p where p.email = ? and password = ?";
//        List<PerUser> rs = (List<PerUser>)hibernateTemplate.find(hql,account,password);
//        if(rs != null && !rs.isEmpty()) rs.get(0);
//        else return null;
        return (PerUser) unique(hql, email, password);
    }

    public PerUser getByPhoneAndPwd(String phone, String password) {
        String hql = "from PerUser p where p.phone = ? and p.password = ?";
        return (PerUser) unique(hql, phone, password);
    }

    public void update(PerUser perUser) {
        hibernateTemplate.update(perUser);
    }
}
