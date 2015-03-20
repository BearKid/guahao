package com.lwb.guahao.webapp.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

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

    /**
     * 设置参数绑定。
     * 在使用hibernateTemplate中使用回调操作原生Query时使用。
     * 因为hibernate Query原本的setParamters需要同时传入参数数组和参数类型数组，不够方便。
     * @param query
     * @param params //数组类型
     * @return
     */
    protected Query setParameters(Query query, Object[] params){
        int i = 0;
        for(Object param : params){
            query.setParameter(i, param);
            ++i;
        }
        return query;
    }
    /**
     * 设置参数绑定。
     * 在使用hibernateTemplate中使用回调操作原生Query时使用。
     * 因为hibernate Query原本的setParamters需要同时传入参数数组和参数类型数组，不够方便。
     * @param query
     * @param params  //List类型
     * @return
     */
    protected Query setParameters(Query query, List params){
        int i = 0;
        for(Object param : params){
            query.setParameter(i, param);
            ++i;
        }
        return query;
    }
}
