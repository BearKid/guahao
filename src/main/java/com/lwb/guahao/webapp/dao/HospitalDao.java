package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.Hospital;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Lu Weibiao on 2015/2/14 21:27.
 */
@Repository
public class HospitalDao extends BaseHibernateDao{

    public Hospital get(Integer id){
        return hibernateTemplate.get(Hospital.class,id);
    }
    public Integer save(Hospital hospital){
        return (Integer)hibernateTemplate.save(hospital);
    }

    /**
     * 按email查唯一记录
     * @param email
     * @return
     */
    public Hospital uniqueByEmail(String email){
        String hql = "from Hospital where email = ?";
        List<Hospital> rs = (List<Hospital>)hibernateTemplate.find(hql,email);
        if(rs == null || rs.isEmpty()) return null;
        else return rs.get(0);
    }
    /**
     * 按email和password查唯一记录
     * @param email
     * @param password
     * @return
     */
    public Hospital uniqueByEmailAndPwd(String email, String password){
        String hql = "from Hospital where email = ? and password = ?";
        List<Hospital> rs = (List<Hospital>)hibernateTemplate.find(hql, email, password);
        if(rs == null || rs.isEmpty()){
            System.out.println("List<Hospital> is empty" + password);
            return null;
        }
        else return rs.get(0);
    }

    /**
     * 更新记录
     * @param hospital
     */
    public void update(Hospital hospital){
        hibernateTemplate.update(hospital);
    }
}
