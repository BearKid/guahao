package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.vo.search.SearchQo;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    public Paging<Hospital> getHospitalPagingBy(SearchQo searchQo) {
        Integer pn = searchQo.getPn();
        if(pn == null){
            pn = 1;
        }

        Integer pageSize = searchQo.getPageSize();
        if(pageSize == null || pageSize < 1) {
            throw new IllegalArgumentException("pageSize is illegal :[" + pageSize + "]");
        }

        final String selectHql = "select h";
        final String countHql = "select count(*)";
        final StringBuilder fromHqlBuilder = new StringBuilder(" from Hospital h where 1=1");
        final List params = new ArrayList();

        Integer areaCode = searchQo.getAreaCode();
        if(areaCode != null){
            fromHqlBuilder.append(" and h.areaCode = ?");
            params.add(areaCode);
        }

        String keyWord = searchQo.getKeyWord();
        if(!StringUtils.isEmpty(keyWord)){
            fromHqlBuilder.append(" and (h.name like ? or h.brief like ?)");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
        }

        List<Hospital> hospitalList = pagingQuery(selectHql + fromHqlBuilder.toString(), params, (pn -1) * pageSize, pageSize);
        Long totalSize = (Long)unique(countHql + fromHqlBuilder.toString(), params);
        Paging<Hospital> hospitalPaging = new Paging<Hospital>(hospitalList, pn, pageSize, totalSize.intValue());

        return hospitalPaging;
    }
}
