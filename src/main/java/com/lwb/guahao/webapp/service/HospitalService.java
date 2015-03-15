package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.print.Doc;
import java.util.Date;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/2/23 23:29
 */
@Service
@Transactional
public class HospitalService {
    @Resource
    private HospitalDao hospitalDao;
    @Resource
    private DoctorDao doctorDao;

    /**
     * 注册新的医院账户
     * @param hospital
     * @return
     */
    public Hospital register(Hospital hospital){
        Hospital newUser = new Hospital();
        BeanUtils.copyProperties(hospital,newUser);//防止传入的hospital被修改
        newUser.setPassword(SecurityUtil.password(hospital.getPassword()));
        newUser.setCreateDate(new Date());
        newUser.setAccountStatusCode(Constants.AccountStatus.UN_VERIFIED);
        hospitalDao.save(newUser);
        return newUser;
    }

    /**
     * 更新
     * @param hospital
     */
    public Hospital update(Hospital hospital){
        Hospital newHospital = new Hospital();
        BeanUtils.copyProperties(hospital,newHospital);//防止传入的hospital被修改
        newHospital.setModifiedDate(new Date());
        hospitalDao.update(newHospital);
        return newHospital;
    }

    /**
     * 判断邮箱是否已注册医院账号
     * @param email
     * @return
     */
    public boolean isRegistered(String email){
        return hospitalDao.uniqueByEmail(email) != null;
    }

    /**
     * 结合一些约束获取某医院的医生
     * @param hospitalId
     * @param name
     * @param deptClassCode
     * @param accountName
     * @return
     */
    public List<Doctor> getDoctors(Integer hospitalId, String name, Integer deptClassCode, String accountName,Integer pn) {
        List<Doctor> doctors = null;
        if(pn == null){
            pn = 1;
        }
        doctors = doctorDao.getDoctorsBy(hospitalId,name,deptClassCode,accountName,pn,Constants.DEFAULT_PAGE_SIZE);
        return doctors;
    }
}
