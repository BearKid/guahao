package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.model.DoctorPerTimeSchedule;
import com.lwb.guahao.common.model.Reservation;
import com.lwb.guahao.common.qo.DoctorPerTimeScheduleQo;
import com.lwb.guahao.common.qo.ReservationQo;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.common.model.Doctor;
import com.lwb.guahao.common.model.Hospital;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.DoctorPerTimeScheduleDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.dao.ReservationDao;
import com.lwb.guahao.webapp.vo.DoctorPerTimeScheduleVo;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.webapp.vo.ReservationVo;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 0:20
 */
@Service
@Transactional
public class DoctorService {
    private final static Logger logger = Logger.getLogger(DoctorService.class);
    @Resource
    private DoctorDao doctorDao;
    @Resource
    private HospitalDao hospitalDao;
    @Resource
    private DoctorPerTimeScheduleDao doctorPerTimeScheduleDao;
    @Resource
    private ReservationDao reservationDao;

    /**
     * 医生账号注册
     * @param doctor
     * @return
     */
    public Doctor register(Doctor doctor){
        Doctor newUser = new Doctor();
        BeanUtils.copyProperties(doctor, newUser);
        newUser.setPassword(SecurityUtil.password(newUser.getPassword()));
        newUser.setAccountStatusCode(Constants.AccountStatus.UN_VERIFIED);
        newUser.setCreateDateTime(new Date());
        doctorDao.save(newUser);
        return newUser;
    }

    /**
     * 根据医生id获取Doctor VO
     * @param doctorId
     * @return
     */
    @Transactional(readOnly = true)
    public DoctorVo getDoctor(Integer doctorId) {
        Doctor doctor = doctorDao.get(doctorId);
        Hospital hospital = hospitalDao.get(doctor.getHospitalId());

        DoctorVo doctorVo = DoctorVo.parse(doctor);
        doctorVo.setHospital(HospitalVo.parse(hospital));
        return doctorVo;
    }

    /**
     * 验证指定的医生账号是否已注册
     * @param doctor
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isRegistered(Doctor doctor) {
        return doctorDao.existsAccountName(doctor.getAccountName());
    }

    /**
     * 获取符合根据查询条件的记录集分页
     * @param doctorPerTimeScheduleQo
     * @return
     */
    public Paging<DoctorPerTimeScheduleVo> getDoctorPerTimeScheduleAndReservationsBy(DoctorPerTimeScheduleQo doctorPerTimeScheduleQo) {

        Paging<DoctorPerTimeSchedule> perTimeSchedulePaging = doctorPerTimeScheduleDao.getPagingBy(doctorPerTimeScheduleQo);
        List<DoctorPerTimeSchedule> sourceList = perTimeSchedulePaging.getItems();
        List<DoctorPerTimeScheduleVo> scheduleVoList = new ArrayList<DoctorPerTimeScheduleVo>(sourceList.size());
        for(DoctorPerTimeSchedule item : sourceList){
            DoctorPerTimeScheduleVo scheduleVo = DoctorPerTimeScheduleVo.parse(item);
            //加入对应的所有预约记录
            ReservationQo reservationQo = new ReservationQo();
            reservationQo.setDoctorPerTimeScheduleId(item.getId());
            List<Reservation> reservationList = reservationDao.getReservationPagingBy(reservationQo).getItems();
            List<ReservationVo> reservationVoList = new ArrayList<ReservationVo>(reservationList.size());
            for(Reservation reservation : reservationList){
                ReservationVo reservationVo = ReservationVo.parse(reservation);
                reservationVoList.add(reservationVo);
            }
            scheduleVo.setReservationList(reservationVoList);
            scheduleVoList.add(scheduleVo);
        }
        Paging<DoctorPerTimeScheduleVo> voPaging = new Paging<DoctorPerTimeScheduleVo>(
                scheduleVoList,perTimeSchedulePaging.getPn(), perTimeSchedulePaging.getPageSize(), perTimeSchedulePaging.getTotalSize());
        return voPaging;
    }
}
