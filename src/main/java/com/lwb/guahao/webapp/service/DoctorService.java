package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.webapp.dao.DoctorDao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 0:20
 */
@Service
public class DoctorService {
    @Resource
    private DoctorDao doctorDao;
    public Doctor register(Doctor doctor){
        Doctor newUser = new Doctor();
        BeanUtils.copyProperties(doctor, newUser);
        newUser.setPassword(SecurityUtil.password(newUser.getPassword()));
        newUser.setAccountStatusCode(Constants.AccountStatus.UN_VERIFIED);
        newUser.setCreateDate(new Date());
        doctorDao.save(newUser);
        return newUser;
    }
}
