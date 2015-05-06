package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.common.model.Doctor;
import com.lwb.guahao.common.model.Hospital;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.vo.DoctorVo;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

        DoctorVo doctorVo = DoctorVo.parse(doctor,hospital);
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
}
