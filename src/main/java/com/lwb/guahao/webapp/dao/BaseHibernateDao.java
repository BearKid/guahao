package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Constants;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 16:12
 */
public abstract class BaseHibernateDao<T> {
    private static final Logger logger = Logger.getLogger(BaseHibernateDao.class);
    private final Class<T> entityClass;
    private final String EXISTS_HQL;
    private final String DELETE_HQL;

    @Resource
    protected HibernateTemplate hibernateTemplate;

    protected BaseHibernateDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        EXISTS_HQL = "select 1 from " + entityClass.getSimpleName() + " where id = ?";
        DELETE_HQL = "delete from " + entityClass.getSimpleName() + " where id = ?";
    }

    /**
     * 判断id所代表的实体是否存在
     * @param id
     * @return
     */
    public boolean exists(Integer id) {
        Integer rs = null;
        try {
            rs = (Integer) unique(EXISTS_HQL, new Object[]{id});
        } catch (Exception e) {
            rs = null;
            logger.warn(null, e);
        }
        return rs != null;
    }

    /**
     * 获取指定实体
     * @param id
     * @return
     */
    public T get(Integer id) {
        return hibernateTemplate.get(entityClass, id);
    }

    /**
     * 删除指定实体
     * @param id
     */
    public void delete(Integer id) {
        excuteUpdate(DELETE_HQL, new Object[]{id});

    }

    /**
     * 保存实体
     * @param o
     * @return
     */
    public Integer save(T o){
        return (Integer)hibernateTemplate.save(o);
    }

    /**
     * 更新实体
     * @param o
     */
    public void update(T o){
        hibernateTemplate.update(o);
    }

    /**
     * 批量更新、保存实体
     * @param list
     */
    public void saveOrUpdate(List<T> list) {
        for (T entity : list) {
            hibernateTemplate.saveOrUpdate(entity);
        }
    }

    /**
     * protected方法
     * 主要是弥补hibernateTemplate一些常用方法的缺失
     */

    /**
     * 获取唯一返回值/对象
     * @param hql
     * @param params
     * @return
     */
    protected Object unique(final String hql, final Object[] params) {

        return hibernateTemplate.execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                setParameters(query,params);
                return query.uniqueResult();
            }
        });
    }
    protected Object unique(final String hql, final List params) {
        Object[] paramArr = params.toArray();
        return unique(hql,paramArr);
    }


    /**
     * 执行delete、update操作
     * @param hql
     * @param params
     * @return
     */
    protected int excuteUpdate(final String hql, final Object[] params) {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                setParameters(query, params);
                return query.executeUpdate();
            }
        });
    }

    /**
     * 分页查询
     * @param hql
     * @param params
     * @return 返回List
     */
    protected List<T> pagingQuery(final String hql, final Object[] params, final int firstRow, final int maxRow) {
        return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                setParameters(query, params);
                if (firstRow != -1) {
                    query.setFirstResult(firstRow);
                }
                if (maxRow != Constants.INFINITE_PAGE_SIZE) {
                    query.setMaxResults(maxRow);
                }
                return query.list();
            }
        });
    }

    /**
     * 分页查询
     * @param hql
     * @param params
     * @param firstRow
     * @param maxRow
     * @return
     */
    protected List<T> pagingQuery(final String hql, final List params, final int firstRow, final int maxRow) {
        return pagingQuery(hql, params.toArray(), firstRow, maxRow);
    }

    /**
     * 查询
     * @param hql
     * @param params
     * @return
     */
    protected List<T> query(final String hql, final Object[] params) {
        return pagingQuery(hql, params, -1, -1);
    }

    /**
     * 查询
     * @param hql
     * @param params
     * @return
     */
    protected List<T> query(final String hql, final List params) {
        return pagingQuery(hql, params.toArray(), -1, -1);
    }

    /******* 私有方法  ************/
    /**
     * 设置参数绑定。
     * 在使用hibernateTemplate中使用回调操作原生Query时使用。
     * 因为hibernate Query原本的setParamters需要同时传入参数数组和参数类型数组，不够方便。
     * @param query
     * @param params //数组类型
     * @return
     */
    protected Query setParameters(Query query, Object[] params) {
        int i = 0;
        for (Object param : params) {
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
     * @param params //List类型
     * @return
     */
    protected Query setParameters(Query query, List params) {
        int i = 0;
        for (Object param : params) {
            query.setParameter(i, param);
            ++i;
        }
        return query;
    }

}
