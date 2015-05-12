package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.util.FieldValidationUtil;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.common.model.Doctor;
import com.lwb.guahao.common.model.Hospital;
import com.lwb.guahao.common.model.PerUser;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.dao.HttpSessionDao;
import com.lwb.guahao.webapp.dao.PerUserDao;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.webapp.vo.PerUserVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 20:25
 */
@Service
@Transactional
public class LoginService {
    private final static Logger logger = Logger.getLogger(LoginService.class);
    @Resource
    private HttpSessionDao httpSessionDao;
    @Resource
    private HospitalDao hospitalDao;
    @Resource
    private PerUserDao perUserDao;
    @Resource
    private DoctorDao doctorDao;

    /**
     * 个人账号登录
     * @param account
     * @param password
     * @param request
     * @return
     */
    public PerUserVo perUserLogin(HttpServletRequest request,String account, String password) {
        PerUserVo perUserVo = null;
        PerUser perUser = null;
        String codedPwd = SecurityUtil.password(password);
        if(FieldValidationUtil.isEmail(account)){
            perUser = perUserDao.getByEmailAndPwd(account,codedPwd);
        } else if(FieldValidationUtil.isMobilePhone(account)){
            perUser = perUserDao.getByPhoneAndPwd(account,codedPwd);
        }
        if(perUser != null){
            perUser.setLatestLoginDate(new Date());//更新登录时间
            perUserDao.update(perUser);
            perUserVo = PerUserVo.parse(perUser);
            httpSessionDao.saveLoginedPerUser(request, perUserVo);
        }
        return perUserVo;
    }

    /**
     * 医院账号登录
     * @param email
     * @param pwd
     * @return
     */
    public HospitalVo hospitalLogin(HttpServletRequest request,String email, String pwd){
        HospitalVo loginedHospital = null;
        Hospital hospital = hospitalDao.uniqueByEmailAndPwd(email, SecurityUtil.password(pwd));
        if(hospital != null) {
            hospital.setLatestLoginDateTime(new Date()); //更新登录时间
            hospitalDao.update(hospital);
            loginedHospital = HospitalVo.parse(hospital);
            httpSessionDao.saveLoginedHospital(request, loginedHospital); //缓存
        }
        return loginedHospital;
    }

    /**
     * 医生账号登录
     * @param accountName
     * @param pwd
     * @param request
     * @return
     */
    public DoctorVo doctorLogin(HttpServletRequest request,String accountName, String pwd){
        DoctorVo loginedDoctor = null;
        Doctor doctor = doctorDao.uniqueByAccountAndPwd(accountName, SecurityUtil.password(pwd));
        if(doctor != null){
            doctor.setLatestLoginDateTime(new Date());//更新登录时间
            doctorDao.update(doctor);
            loginedDoctor = DoctorVo.parse(doctor);
            httpSessionDao.saveLoginedDocotr(request, loginedDoctor); //缓存
        }
        return loginedDoctor;
    }

    /**
     * 读取当前已登录医院账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public HospitalVo getLoginedHospital(HttpServletRequest request){
        return httpSessionDao.getLoginedHospital(request);
    }

    /**
     * 读取当前已登录个人账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public PerUserVo getLoginedPerUser(HttpServletRequest request) {
        return httpSessionDao.getLoginedPerUser(request);
    }

    /**
     * 读取当前已登录医生账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public DoctorVo getLoginedDoctor(HttpServletRequest request) {
        return httpSessionDao.getLoginedDoctor(request);
    }

    /**
     * 判断客户端是否已经登录为医院账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isHospitalLogined(HttpServletRequest request){
        return httpSessionDao.getLoginedHospital(request) != null;
    }

    /**
     * 医院账号退出登录
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public HospitalVo hospitalLogout(HttpServletRequest request) {
        return httpSessionDao.deleteHospital(request);
    }

    /**
     * 个人账号退出登录
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public PerUserVo perUserLogout(HttpServletRequest request){
        return httpSessionDao.deleteLoginedPerUser(request);
    }

    /**
     * 医生账号退出登录
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public DoctorVo doctorLogout(HttpServletRequest request){
        return httpSessionDao.deleteLoginedDoctor(request);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getLoginedHospitalId(HttpServletRequest request){
        HospitalVo hospital = getLoginedHospital(request);
        String idStr = (hospital == null) ? null : hospital.getId();
        Integer id = StringUtils.isEmpty(idStr) ? null : Integer.valueOf(hospital.getId());
        return id;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getLoginedPerUserId(HttpServletRequest request) {
        PerUserVo perUser = getLoginedPerUser(request);
        String idStr = (perUser == null) ? null : perUser.getId();
        Integer id = StringUtils.isEmpty(idStr) ? null : Integer.valueOf(perUser.getId());
        return id;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getLoginedDoctorId(HttpServletRequest request) {
        DoctorVo doctor = getLoginedDoctor(request);
        String idStr = (doctor == null) ? null : doctor.getId();
        Integer id = StringUtils.isEmpty(idStr) ? null : Integer.valueOf(doctor.getId());
        return id;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isPerUserLogined(HttpServletRequest request) {
       return httpSessionDao.getLoginedPerUser(request) != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isDoctorLogined(HttpServletRequest request) {
        return httpSessionDao.getLoginedDoctor(request) != null;
    }
}
