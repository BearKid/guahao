package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.PerUser;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Lu Weibiao on 2015/2/14 21:27.
 */
@Repository
public class PerUserDao {
    @Resource
    private HibernateTemplate hibernateTemplate;

    public PerUser get(Integer id){
        return hibernateTemplate.get(PerUser.class,id);
    }
    public void saveOrUpdate(PerUser perUser){
        hibernateTemplate.saveOrUpdate(perUser);
    }
}
