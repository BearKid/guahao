package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.model.Hospital;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Lu Weibiao on 2015/2/14 21:27.
 */
@Repository
public class HospitalDao extends BaseHibernateDao<Hospital>{
    private final Logger logger = Logger.getLogger(HospitalDao.class);

    /**
     * 按email查唯一记录
     * @param email
     * @return
     */
    public Hospital uniqueByEmail(String email){
        String hql = "from Hospital where email = ?";
        Object[] params = new Object[]{
                email
        };
        return (Hospital)unique(hql,params);
    }
    /**
     * 按email和password查唯一记录
     * @param email
     * @param password
     * @return
     */
    public Hospital uniqueByEmailAndPwd(String email, String password){
        String hql = "from Hospital where email = ? and password = ?";
        Object[] params = new Object[]{
                email,password
        };
        return (Hospital)unique(hql, params);
    }

}
