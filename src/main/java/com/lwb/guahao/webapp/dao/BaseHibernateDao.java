package com.lwb.guahao.webapp.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 16:12
 */
public abstract class BaseHibernateDao<T> {
    @Resource
    protected HibernateTemplate hibernateTemplate;

    /**
     * 获取唯一返回值/对象
     * @param hql
     * @param params
     * @return
     */
    protected Object unique(final String hql, final Object... params){
        return hibernateTemplate.execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                for(int i = 0; i < params.length; i++) {
                    query.setParameter(i,params[i]);
                }
                return query.uniqueResult();
            }
        });
    }
}
