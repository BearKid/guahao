package com.lwb.guahao.webapp.dao;

import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.model.Reservation;
import com.lwb.guahao.common.qo.ReservationQo;
import com.lwb.guahao.common.qo.util.QoUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/5/6 22:28
 */
@Repository
public class ReservationDao extends BaseHibernateDao<Reservation>{
    /**
     * 根据查询条件查找挂号预约记录分页
     * @param reservationQo
     * @return
     */
    public Paging<Reservation> getReservationPagingBy(final ReservationQo reservationQo) {
        Object[] fromHqlHolder = constructFromHqlBy(reservationQo);
        final String fromHql = (String)fromHqlHolder[0];
        final List params = (List)fromHqlHolder[1];

        Integer firstIndex = QoUtil.getFirstIndex(reservationQo);
        Integer pageSize = QoUtil.getPageSize(reservationQo);
        Integer pn = QoUtil.getPn(reservationQo);

        String selectHql = "select r" + fromHql + " order by createDateTime desc";
        List<Reservation> reservationList = pagingQuery(selectHql, params, firstIndex, pageSize);

        String countHql = "select count(*) " +  fromHql;
        Long totalSize = (Long)unique(countHql, params);

        Paging<Reservation> reservationPaging = new Paging<Reservation>(reservationList, pn, pageSize, totalSize.intValue());
        return reservationPaging;
    }

    /**
     * 统计符合查询条件的记录数
     * @param reservationQo
     * @return
     */
    public int countBy(final ReservationQo reservationQo) {
        final Object[] countHqlHolder = constructFromHqlBy(reservationQo);
        final String countHql = "select count(*) " + countHqlHolder[0];
        final List params = (List)countHqlHolder[1];
        final Long totalSize = (Long)unique(countHql, params);
        return totalSize.intValue();
    }


    /**
     * 根据查询条件构造from子句
     * @param reservationQo
     * @return
     */
    private Object[] constructFromHqlBy(final ReservationQo reservationQo){
        final StringBuilder fromHqlBuilder = new StringBuilder(" from Reservation r where 1=1");
        List params = new ArrayList();

        Integer id = reservationQo.getId();
        if(id != null){
            fromHqlBuilder.append(" and id = ?");
            params.add(id);
        }

        Integer orderStatus = reservationQo.getOrderStatusCode();
        if(orderStatus != null){
            fromHqlBuilder.append(" and orderStatusCode = ?");
            params.add(orderStatus);
        }

        Integer[] orderStatusIn = reservationQo.getOrderStatusCodeIn();
        if(orderStatusIn != null && orderStatusIn.length > 0){
            fromHqlBuilder.append(" and orderStatusCode in (" + StringUtils.arrayToCommaDelimitedString(orderStatusIn) + ")");
        }

        Date createDateTimeStart = reservationQo.getCreateDateTimeStart(); //订单创建日期时间 - 起始时间
        if(createDateTimeStart != null){
            fromHqlBuilder.append(" and createDateTime >= ?");
            params.add(createDateTimeStart);
        }

        Date createDateTimeEnd = reservationQo.getCreateDateTimeEnd(); //订单创建日期时间 - 结束时间
        if(createDateTimeEnd != null){
            fromHqlBuilder.append(" and createDateTime <= ?");
            params.add(createDateTimeEnd);
        }

        Date modifyDateTimeStart = reservationQo.getModifyDateTimeStart(); //订单修改日期时间 - 起始时间
        if(modifyDateTimeStart != null){
            fromHqlBuilder.append(" and modifyDateTime >= ?");
            params.add(modifyDateTimeStart);
        }

        Date modifyDateTimeEnd = reservationQo.getModifyDateTimeEnd(); //订单修改日期时间 - 结束时间
        if(modifyDateTimeEnd != null){
            fromHqlBuilder.append(" and modifyDateTime <= ?");
            params.add(modifyDateTimeEnd);
        }

        Integer doctorId = reservationQo.getDoctorId(); //医生id
        if(doctorId != null){
            fromHqlBuilder.append(" and doctorId = ?");
            params.add(doctorId);
        }

        Integer doctorPerTimeScheduleId = reservationQo.getDoctorPerTimeScheduleId();
        if(doctorPerTimeScheduleId != null){
            fromHqlBuilder.append(" and doctorPerTimeScheduleId = ?");
            params.add(doctorPerTimeScheduleId);
        }

        Integer perUserId = reservationQo.getPerUserId(); //个人用户id
        if(perUserId != null){
            fromHqlBuilder.append(" and perUserId = ?");
            params.add(perUserId);
        }

        return new Object[]{
                fromHqlBuilder.toString(),
                params
        };
    }

    /**
     * 判断是否存在符合查询条件的记录
     * @param reservationQo
     * @return
     */
    public boolean existsBy(ReservationQo reservationQo) {
        return countBy(reservationQo) > 0;
    }
}
