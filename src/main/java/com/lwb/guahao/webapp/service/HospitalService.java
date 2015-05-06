package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.DoctorPerTimeSchedule;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.DoctorPerTimeScheduleDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import org.apache.log4j.Logger;
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
    private final static Logger logger = Logger.getLogger(HospitalService.class);
    @Resource
    private HospitalDao hospitalDao;
    @Resource
    private DoctorDao doctorDao;
    @Resource
    private DoctorPerTimeScheduleDao doctorPerTimeScheduleDao;
    @Resource
    private DoctorService doctorService;

    /**
     * 注册新的医院账户
     * @param hospital
     * @return
     */
    public ApiRet<Hospital> register(Hospital hospital){
        ApiRet<Hospital> apiRet = new ApiRet<Hospital>();
        Hospital newUser = new Hospital();
        BeanUtils.copyProperties(hospital,newUser);//防止传入的hospital被修改
        newUser.setPassword(SecurityUtil.password(hospital.getPassword()));
        newUser.setCreateDateTime(new Date());
        newUser.setAccountStatusCode(Constants.AccountStatus.UN_VERIFIED);
        hospitalDao.save(newUser);
        apiRet.setRet(ApiRet.RET_SUCCESS);
        apiRet.setMsg("注册成功");
        apiRet.setData(newUser);
        return apiRet;
    }

    /**
     * 更新
     * @param hospital
     */
    public Hospital update(Hospital hospital){
        Hospital newHospital = new Hospital();
        BeanUtils.copyProperties(hospital, newHospital);//防止传入的hospital被修改
        newHospital.setModifyDateTime(new Date());
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
    @Transactional(readOnly = true)
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
        ApiRet<Doctor> apiRet = new ApiRet<Doctor>();
        if(!doctorService.isRegistered(doctorToSave)) {
            Doctor doctor = new Doctor();
            BeanUtils.copyProperties(doctorToSave, doctor);
            doctor.setCreateDateTime(new Date());
            doctor.setPassword(SecurityUtil.password(doctor.getPassword()));
            doctor.setAccountStatusCode(Constants.AccountStatus.NORMAL);
            doctorDao.save(doctor);
            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setMsg("创建成功");
            apiRet.setData(doctor);
        } else {
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("账号名已被占用");
        }
        return apiRet;
    }

    /**
     * 判断指定医院是否有指定的医生
     * @param hospitalId
     * @param doctorId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean hasThisDoctor(Integer hospitalId, Integer doctorId) {
        Doctor doctor = doctorDao.get(doctorId);
        return hospitalId.equals(doctor.getHospitalId());
    }


    /**
     * 判断指定医院是否有该医生排班
     * @param doctorPerTimeScheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean hasThisPerTimeSchedule(Integer hospitalId, Integer doctorPerTimeScheduleId) {
        boolean hasThisPerTimeSchedule;
        DoctorPerTimeSchedule doctorPerTimeSchedule = doctorPerTimeScheduleDao.get(doctorPerTimeScheduleId);
        Integer doctorId = doctorPerTimeSchedule.getDoctorId();
        if(doctorId == null) {
            hasThisPerTimeSchedule = false;
        } else {
            Doctor doctor = doctorDao.get(doctorId);
            hasThisPerTimeSchedule = hospitalId.equals(doctor.getHospitalId());
        }
        return hasThisPerTimeSchedule;
    }

    /**
     * 获取用户视图层医院信息Hospital
     * @param hospitalId
     * @return
     */
    @Transactional(readOnly = true)
    public HospitalVo getHospital(Integer hospitalId) {
        Hospital hospital = hospitalDao.get(hospitalId);
        HospitalVo hospitalVo = HospitalVo.parse(hospital);
        return hospitalVo;
    }
}
