package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.dao.HospitalDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/2/23 23:29
 */
@Service
@Transactional
public class HospitalService {
    @Resource
    private HospitalDao hospitalDao;

    /**
     * 保存新的医院账户
     * @param hospital
     * @return
     */
    public Integer save(Hospital hospital){
        hospital.setCreDate(new Date());
        hospital.setAccountStatus(Constants.AccountStatus.UN_VERIFIED);
        return hospitalDao.save(hospital);
    }
}
