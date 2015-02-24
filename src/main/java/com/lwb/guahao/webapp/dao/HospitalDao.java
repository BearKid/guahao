package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.model.PerUser;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Lu Weibiao on 2015/2/14 21:27.
 */
@Repository
public class HospitalDao {
    @Resource
    private HibernateTemplate hibernateTemplate;

    public Hospital get(Integer id){
        return hibernateTemplate.get(Hospital.class,id);
    }
    public Integer save(Hospital hospital){
        return (Integer)hibernateTemplate.save(hospital);
    }
}
