package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.webapp.vo.search.SearchQo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 22:47
 */
@Repository
public class DoctorDao extends BaseHibernateDao<Doctor> {
    public Doctor uniqueByAccountAndPwd(String accountName, String pwd) {
        String hql = "from Doctor as d where d.accountName = ? and d.password = ?";
        Object[] params = new Object[]{
                accountName, pwd
        };
        return (Doctor) unique(hql, params);
    }

    public Paging<Doctor> getDoctorsPagingBy(Integer hospitalId, String name, Integer deptClasCode, String accountName,
                                           final int pn, final int pageSize) {
        final String selectHql = "select d";
        final String countHql = "select count(*)";
        final StringBuilder fromHqlBuilder = new StringBuilder(" from Doctor d where 1=1");
        final List params = new ArrayList();

        if (hospitalId != null) {
            fromHqlBuilder.append(" and d.hospital.id = ?");
            params.add(hospitalId);
        }
        if (!StringUtils.isEmpty(name)) {
            fromHqlBuilder.append(" and d.name = ?");
            params.add(name);
        }
        if (deptClasCode != null) {
            fromHqlBuilder.append(" and d.deptClassCode = ?");
            params.add(deptClasCode);
        }
        if (!StringUtils.isEmpty(accountName)) {
            fromHqlBuilder.append(" and d.accountName = ?");
            params.add(accountName);
        }
        fromHqlBuilder.append(" order by d.createDateTime");

        final String finalSelectHql = selectHql + fromHqlBuilder.toString();
        List<Doctor> doctors = pagingQuery(finalSelectHql, params, (pn-1)*pageSize, pageSize);

        final String finalCountHql = countHql + fromHqlBuilder.toString();

        Long totalSize = (Long)unique(finalCountHql,params);

        return new Paging<Doctor>(doctors,pn,pageSize,totalSize.intValue());
    }

    /**
     * 公共搜索页的搜索条件查询医生分页
     * @param searchQo
     * @return
     */
    public Paging<Doctor> getDoctorsPagingBy(SearchQo searchQo) {
        Integer pn = searchQo.getPn();
        if(pn == null){
            pn = 1;
        }

        Integer pageSize = searchQo.getPageSize();
        if(pageSize == null || pageSize < 1) {
            throw new IllegalArgumentException("pageSize is illegal :[" + pageSize + "]");
        }

        final String selectHql = "select d";
        final String countHql = "select count(*)";
        final StringBuilder fromHqlBuilder = new StringBuilder(" from Doctor d where 1=1");
        final List params = new ArrayList();

        Integer areaCode = searchQo.getAreaCode();
        if(areaCode != null){
            fromHqlBuilder.append(" and d.hospital.areaCode = ?");
            params.add(areaCode);
        }

        Integer deptClassCode = searchQo.getDeptClassCode();
        if(deptClassCode != null){
            fromHqlBuilder.append(" and d.deptClassCode = ?");
            params.add(deptClassCode);
        }

        String keyWord = searchQo.getKeyWord();
        if(!StringUtils.isEmpty(keyWord)){
            fromHqlBuilder.append(" and (d.name like ? or d.goodAtTags like ? or d.brief like ?)");
            params.add(keyWord);
            params.add(keyWord);
            params.add(keyWord);
        }

        List<Doctor> doctorList = pagingQuery(selectHql + fromHqlBuilder.toString(),params, (pn -1) * pageSize, pageSize);
        Long totalSize = (Long)unique(countHql + fromHqlBuilder.toString(),params);
        Paging<Doctor> doctorPaging = new Paging<Doctor>(doctorList,pn,pageSize,totalSize.intValue());

        return doctorPaging;
    }
}
