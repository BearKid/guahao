package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.model.PerUserBan;
import com.lwb.guahao.common.qo.util.PerUserBanQo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/5/12 16:50
 */
@Repository
public class PerUserBanDao extends BaseHibernateDao<PerUserBan> {
    /**
     * 判断是否存在符合查询条件的记录
     * @param perUserBanQo
     * @return
     */
    public boolean existsBy(PerUserBanQo perUserBanQo) {
        return countBy(perUserBanQo) > 0;
    }

    /**
     * 统计符合查询条件的记录数
     * @param perUserBanQo
     * @return
     */
    private int countBy(final PerUserBanQo perUserBanQo) {
        final Object[] fromHqlHolder = constructFromHqlBy(perUserBanQo);
        final String countHql = "select count(*) " + fromHqlHolder[0];
        final List params = (List)fromHqlHolder[1];
        final Long totalSize = (Long)unique(countHql, params);
        return totalSize.intValue();
    }

    /**
     * 根据查询条件构造出from子句
     * @param perUserBanQo
     * @return Object数组，[0]为hql，[1]为占位参数列表，List类型
     */
    private Object[] constructFromHqlBy(final PerUserBanQo perUserBanQo) {
        final StringBuilder fromHqlBuilder = new StringBuilder("from PerUserBan where 1=1");
        final List params = new ArrayList();

        final Integer id = perUserBanQo.getId();
        if(id != null){
            fromHqlBuilder.append(" and id = ?");
            params.add(id);
        }

        final Integer perUserId = perUserBanQo.getPerUserId();
        if(perUserId != null){
            fromHqlBuilder.append(" and perUserId = ?");
            params.add(perUserId);
        }

        final Date expireDateTimeStart = perUserBanQo.getExpireDateTimeStart();//>=过期时间
        if(expireDateTimeStart != null){
            fromHqlBuilder.append(" and expireDateTime >= ?");
            params.add(expireDateTimeStart);
        }

        final Date expireDateTimeEnd = perUserBanQo.getExpireDateTimeEnd();//<=过期时间
        if(expireDateTimeEnd != null){
            fromHqlBuilder.append(" and expireDateTime <= ?");
            params.add(expireDateTimeEnd);
        }

        final Date createDateTimeStart = perUserBanQo.getCreateDateTimeStart();//<=记录创建时间
        if(createDateTimeStart != null){
            fromHqlBuilder.append(" and createDateTime >= ?");
            params.add(createDateTimeStart);
        }

        final Date createDateTimeEnd = perUserBanQo.getCreateDateTimeEnd();//<=记录创建时间
        if(createDateTimeStart != null){
            fromHqlBuilder.append(" and createDateTime <= ?");
            params.add(createDateTimeEnd);
        }

        final Integer reasonCode = perUserBanQo.getReasonCode();//标识被禁原因的编码 参见Constants.PerUserBanReasonCode
        if(reasonCode != null){
            fromHqlBuilder.append(" and reasonCode = ?");
            params.add(reasonCode);
        }

        final String remark = perUserBanQo.getRemark();//备注
        if(!StringUtils.isEmpty(remark)){
            fromHqlBuilder.append(" and remark like ?");
            params.add("%" + remark + "%");
        }

        return new Object[]{
                fromHqlBuilder.toString(),
                params
        };
    }
}
