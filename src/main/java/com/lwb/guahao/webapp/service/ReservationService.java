package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.model.*;
import com.lwb.guahao.common.qo.ReservationQo;
import com.lwb.guahao.common.util.lang.DateUtils;
import com.lwb.guahao.webapp.dao.*;
import com.lwb.guahao.webapp.vo.DoctorPerTimeScheduleVo;
import com.lwb.guahao.webapp.vo.ReservationVo;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/5/6 22:11
 * 挂号预约 Service
 */
@Service
@Transactional
public class ReservationService {
    private final static Logger logger = Logger.getLogger(ReservationService.class);
    @Resource
    private PerUserDao perUserDao;
    @Resource
    private DoctorDao doctorDao;
    @Resource
    private HospitalDao hospitalDao;
    @Resource
    private ReservationDao reservationDao;
    @Resource
    private DoctorPerTimeScheduleDao doctorPerTimeScheduleDao;
    @Resource
    private PerUserBanDao perUserBanDao;
    @Resource
    private DoctorPerTimeScheduleService doctorPerTimeScheduleService;
    @Resource
    private PerUserService perUserService;

    /**
     * 创建挂号预约订单
     * 1.需要校验该排班是否允许被预约;
     * 2.如果之前已经成功预约该排班，不能再预约
     * 3.验证是否为预约限制黑名单用户
     *
     * @param perUserId
     * @param doctorPerTimeScheduleId
     * @return
     */
    public ApiRet<Reservation> createReservation(final Integer perUserId, final Integer doctorPerTimeScheduleId) {
        ApiRet apiRet;

        //校验该排班是否处于可预约状态
        apiRet = doctorPerTimeScheduleService.isReservable(doctorPerTimeScheduleId);
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.FALSE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }
        //如果之前已经成功预约该排班，不能再预约
        ReservationQo reservationQo = new ReservationQo();
        reservationQo.setPerUserId(perUserId);
        reservationQo.setDoctorPerTimeScheduleId(doctorPerTimeScheduleId);
        reservationQo.setOrderStatusCodeIn(new Integer[]{Constants.OrderStatus.UN_PAYED, Constants.OrderStatus.PAYED, Constants.OrderStatus.PRESENT, Constants.OrderStatus.ABSENT});
        reservationQo.setPn(1);
        reservationQo.setPageSize(1);
        if (reservationDao.existsBy(reservationQo)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("已预约，不能重复预约");
            return apiRet;
        }
        //预约限制黑名单用户检查
        apiRet = perUserService.isForbiddenToReserve(perUserId);
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.TRUE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }

        DoctorPerTimeSchedule doctorPerTimeSchedule = doctorPerTimeScheduleDao.get(doctorPerTimeScheduleId);
        Doctor doctor = doctorDao.get(doctorPerTimeSchedule.getDoctorId());
        Hospital hospital = hospitalDao.get(doctor.getHospitalId());
        PerUser perUser = perUserDao.get(perUserId);

        Reservation reservation = new Reservation();
        reservation.setPerUserId(perUser.getId());
        reservation.setPerUserName(perUser.getName());
        reservation.setPerUserIdCard(perUser.getIdCard());
        reservation.setDoctorPerTimeScheduleId(doctorPerTimeScheduleId);
        reservation.setDoctorId(doctor.getId());
        reservation.setDoctorName(doctor.getName());
        reservation.setHospitalId(hospital.getId());
        reservation.setHospitalName(hospital.getName());
        reservation.setOrderStatusCode(Constants.OrderStatus.UN_PAYED);
        Date now = new Date();
        reservation.setCreateDateTime(now);
        reservation.setModifyDateTime(now);
        //保存新挂号预约记录
        reservationDao.save(reservation);

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("已创建预约订单，请尽快支付挂号费");
        apiRet.setData(reservation);

        return apiRet;
    }

    /**
     * 根据查询条件验证符合的预约记录是否存在
     * @param reservationQo
     * @return
     */
    private ApiRet<Boolean> existsReservation(final ReservationQo reservationQo) {
        ApiRet apiRet = new ApiRet();
        if (reservationDao.countBy(reservationQo) < 1) {
            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setData(Boolean.FALSE);
            apiRet.setMsg("挂号预约订单不存在");
            return apiRet;
        }

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setData(Boolean.TRUE);
        return apiRet;
    }

    /**
     * 取消挂号预约
     * - 验证预约订单是否存在
     * - 验证指定预约订单属于指定用户
     * - 验证预约订单对应的排班未过期
     * - 未支付和已支付情况下可取消订单，已支付情况下取消订单需将对应排班剩余号源加1
     * @param perUserId
     * @param reservationId
     * @return
     */
    public ApiRet cancelReservation(final Integer perUserId, final Integer reservationId) {
        ApiRet apiRet = new ApiRet();
        Reservation reservation = reservationDao.get(reservationId);
        //验证预约订单是否存在
        if (reservation == null) {
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("预约记录不存在");
            return apiRet;
        }
        //验证指定预约订单属于指定用户
        apiRet = isBelongToPerUser(perUserId, reservationId);
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.FALSE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }

        //验证订单是否处于未支付或已支付状态
        Integer orderStatusCode = reservation.getOrderStatusCode();
        if(!orderStatusCode.equals(Constants.OrderStatus.UN_PAYED) && !orderStatusCode.equals(Constants.OrderStatus.PAYED)){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("订单不是处于未支付或已支付状态，不能取消预约");
            return  apiRet;
        }
        //验证预约订单对应的排班未过期
        apiRet = doctorPerTimeScheduleService.isExpired(reservation.getDoctorPerTimeScheduleId());
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.TRUE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }
        //已支付情况下取消订单需将对应排班剩余号源加1并且将订单标记为待退款
        if(orderStatusCode.equals(Constants.OrderStatus.PAYED)){
            DoctorPerTimeSchedule doctorPerTimeSchedule = doctorPerTimeScheduleDao.get(reservation.getDoctorPerTimeScheduleId());
            doctorPerTimeSchedule.setOddSource(doctorPerTimeSchedule.getOddSource() + 1);
            doctorPerTimeScheduleDao.update(doctorPerTimeSchedule);
            reservation.setOrderStatusCode(Constants.OrderStatus.CANCEL_REFUNDING);
        } else {
            reservation.setOrderStatusCode(Constants.OrderStatus.CANCEL);
        }
        //更新预约订单状态
        reservationDao.update(reservation);

        //校验取消预约的频度是否达到上限
        apiRet = reachToMaxCancelFrequency(perUserId);
        if(apiRet.getData().equals(Boolean.TRUE)){
            PerUserBan perUserBan = new PerUserBan();
            perUserBan.setPerUserId(perUserId);
            perUserBan.setReasonCode(Constants.PerUserBanReason.TOO_MUCH_CANCEL);
            DateTime now = DateTime.now();
            DateTime aMonthLater = now.plusMonths(1);
            perUserBan.setExpireDateTime(aMonthLater.toDate());
            perUserBan.setCreateDateTime(now.toDate());
            perUserBanDao.save(perUserBan);

            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setMsg("取消挂号预约订单成功,但取消预约频度已达到上限，未来一个月内将无法预约");
            return apiRet;
        } else {
            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setMsg("取消挂号预约订单成功");
            return apiRet;
        }
    }

    /**
     * 判断指定用户是否取消预约的频度达到了上限
     * @param perUserId
     * @return
     */
    private ApiRet<Boolean> reachToMaxCancelFrequency(Integer perUserId) {
        ApiRet<Boolean> apiRet = new ApiRet<Boolean>();
        //构造查询条件
        ReservationQo reservationQo = new ReservationQo();
        reservationQo.setPerUserId(perUserId);
        reservationQo.setOrderStatusCodeIn(new Integer[]{Constants.OrderStatus.CANCEL_REFUNDING, Constants.OrderStatus.CANCEL});
        DateTime now = DateTime.now();
        DateTime curMonthStartDay = now.withTimeAtStartOfDay().withDayOfMonth(1);
        reservationQo.setModifyDateTimeStart(curMonthStartDay.toDate());
        reservationQo.setModifyDateTimeEnd(now.toDate());

        int totalSize = reservationDao.countBy(reservationQo);
        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setData(totalSize >= 2);
        return apiRet;
    }

    /**
     * 支付挂号预约订单
     * - 验证预约订单存在
     * - 验证指定预约订单处于待支付状态
     * - 验证对应排班是否处于可被预约状态
     * - 验证指定预约订单属于指定用户
     * - 验证用户是否被禁
     * @param perUserId
     * @param reservationId
     * @return
     */
    public ApiRet payForReservation(final Integer perUserId, final Integer reservationId) {
        ApiRet apiRet = new ApiRet();

        //验证预约订单存在
        Reservation reservation = reservationDao.get(reservationId);
        if (reservation == null) {
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("订单不存在");
            return apiRet;
        }

        //验证指定预约订单处于待支付状态
        if(!reservation.getOrderStatusCode().equals(Constants.OrderStatus.UN_PAYED)){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("预约挂号订单未处于待支付状态");
        }
        //验证对应排班是否处于可被预约状态
        apiRet = doctorPerTimeScheduleService.isReservable(reservation.getDoctorPerTimeScheduleId());
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.FALSE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }

        //验证指定预约订单属于指定用户
        apiRet = isBelongToPerUser(perUserId, reservationId);
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.FALSE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }

        //预约限制黑名单用户检查
        apiRet = perUserService.isForbiddenToReserve(perUserId);
        if (apiRet.getRet() == ApiRet.RET_FAIL || apiRet.getData().equals(Boolean.FALSE)) {
            apiRet.setRet(ApiRet.RET_FAIL);
            return apiRet;
        }

        //TODO 执行支付
        //更新预约订单状态
        reservation.setOrderStatusCode(Constants.OrderStatus.PAYED);
        reservationDao.update(reservation);
        //对应排班的剩余号源减1
        DoctorPerTimeSchedule doctorPerTimeSchedule = doctorPerTimeScheduleDao.get(reservation.getDoctorPerTimeScheduleId());
        doctorPerTimeSchedule.setOddSource(doctorPerTimeSchedule.getOddSource() - 1);
        doctorPerTimeScheduleDao.update(doctorPerTimeSchedule);

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("挂号预约订单支付成功");
        return apiRet;
    }

    /**
     * 验证指定预约订单是否属于指定用户
     * TODO review 以这个方法作为思考案例
     * @param perUserId
     * @param reservationId
     * @return
     */
    private ApiRet<Boolean> isBelongToPerUser(final Integer perUserId, final Integer reservationId) {
        ApiRet<Boolean> apiRet = new ApiRet<Boolean>();
        Reservation reservation = reservationDao.get(reservationId);
        if (reservation == null) {//业务级别的错误处理
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("指定排班不存在");
            return apiRet;
        }
        if (reservation.getPerUserId() == null) {//数据库级别的完整性错误处理
            RuntimeException e = new RuntimeException("Reservation#" + reservation.getPerUserId() + "#.getPerUserId() is null");
            logger.error(null, e);
            throw e;
        }
        if (!reservation.getPerUserId().equals(perUserId)) {//业务级别的正常处理
            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setData(Boolean.FALSE);
            return apiRet;
        }

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setData(Boolean.TRUE);
        return apiRet;
    }

    /**
     * 根据查询条件查找预约订单记录分页
     * @param reservationQo
     * @return
     */
    public ApiRet<Paging<ReservationVo>> getReservationPagingBy(ReservationQo reservationQo) {
        ApiRet apiRet = new ApiRet();

        Paging<Reservation> reservationPaging = reservationDao.getReservationPagingBy(reservationQo);

        List<Reservation> reservationList = reservationPaging.getItems();
        List<ReservationVo> reservationVoList = new ArrayList<ReservationVo>(reservationList.size());
        for(Reservation reservation : reservationList){
            ReservationVo reservationVo = ReservationVo.parse(reservation);

            //预约的医生排班信息
            DoctorPerTimeSchedule doctorPerTimeSchedule = doctorPerTimeScheduleDao.get(reservation.getDoctorPerTimeScheduleId());
            reservationVo.setDoctorPerTimeSchedule(DoctorPerTimeScheduleVo.parse(doctorPerTimeSchedule));

            //就诊日期
            Date visitDoctorDate = doctorPerTimeSchedule.getStartDateTime();
            reservationVo.setVisitDoctorDay(visitDoctorDate == null ? "未知" : DateUtils.yearMonthDayWeekFormatter.format(visitDoctorDate));

            reservationVoList.add(reservationVo);
        }
        Paging<ReservationVo> reservationVoPaging = new Paging<ReservationVo>(reservationVoList, reservationPaging.getPn(), reservationPaging.getPageSize(), reservationPaging.getTotalSize());
        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setData(reservationVoPaging);
        return apiRet;
    }

    /**
     * 更新指定预约记录为指定的状态
     * @param reservationId
     * @return
     */
    public ApiRet updateReservationOrderStatus(Integer reservationId, Integer orderStatus) {
        ApiRet apiRet = new ApiRet();
        Reservation reservation = reservationDao.get(reservationId);
        reservation.setOrderStatusCode(orderStatus);
        reservationDao.update(reservation);

        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("成功更新预约记录的状态");
        return apiRet;
    }
}
