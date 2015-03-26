package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.vo.DoctorVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
     * 结合一些约束获取某医院的医生分页
     * @param hospitalId
     * @param name
     * @param deptClassCode
     * @param accountName
     * @return
     */
    public Paging<DoctorVo> getDoctorPaging(Integer hospitalId, String name, Integer deptClassCode, String accountName, Integer pn) {
        Paging<DoctorVo> doctorVoPaging = null;
        if(pn == null){
            pn = 1;
        }
        Paging<Doctor> doctorPaging = doctorDao.getDoctorsPagingBy(hospitalId, name, deptClassCode, accountName, pn, Constants.DEFAULT_PAGE_SIZE);
        List<DoctorVo> doctorVos = DoctorVo.parse(doctorPaging.getItems());
        doctorVoPaging = new Paging<DoctorVo>(doctorVos,doctorPaging.getPn(),doctorPaging.getPageSize(),doctorPaging.getTotalSize());
        return doctorVoPaging;
    }

    /**
     * 创建医院账号
     * @param doctorToSave
     * @return
     */
    public ApiRet<Doctor> createDoctor(Doctor doctorToSave) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorToSave,doctor);
        doctor.setCreateDateTime(new Date());
        doctor.setPassword(SecurityUtil.password(doctor.getPassword()));
        doctor.setAccountStatusCode(Constants.AccountStatus.NORMAL);
        doctorDao.save(doctor);
        return new ApiRet<Doctor>(doctor);
    }

    /**
     * 判断指定医院是否有指定的医生
     * @param hospitalId
     * @param doctorId
     * @return
     */
    public boolean hasThisDoctor(Integer hospitalId, Integer doctorId) {
        Doctor doctor = doctorDao.get(doctorId);
        return hospitalId.equals(doctor.getHospitalId());
    }
}
