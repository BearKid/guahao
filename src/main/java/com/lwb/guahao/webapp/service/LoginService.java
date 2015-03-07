package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.FieldValidationUtil;
import com.lwb.guahao.common.SecurityUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.dao.DoctorDao;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.dao.HttpSessionDao;
import com.lwb.guahao.webapp.dao.PerUserDao;
import com.lwb.guahao.webapp.vo.LoginedDoctor;
import com.lwb.guahao.webapp.vo.LoginedHospital;
import com.lwb.guahao.webapp.vo.LoginedPerUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public LoginedPerUser perUserLogin(HttpServletRequest request,String account, String password) {
        LoginedPerUser loginedPerUser = null;
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
            loginedPerUser = LoginedPerUser.parse(perUser);
            httpSessionDao.saveLoginedPerUser(request,loginedPerUser);
        }
        return loginedPerUser;
    }

    /**
     * 医院账号登录
     * @param email
     * @param pwd
     * @return
     */
    public LoginedHospital hospitalLogin(HttpServletRequest request,String email, String pwd){
        LoginedHospital loginedHospital = null;
        Hospital hospital = hospitalDao.uniqueByEmailAndPwd(email, SecurityUtil.password(pwd));
        if(hospital != null) {
            hospital.setLatestLoginDate(new Date()); //更新登录时间
            hospitalDao.update(hospital);
            loginedHospital = LoginedHospital.parse(hospital);
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
    public LoginedDoctor doctorLogin(HttpServletRequest request,String accountName, String pwd){
        LoginedDoctor loginedDoctor = null;
        Doctor doctor = doctorDao.uniqueByAccountAndPwd(accountName, SecurityUtil.password(pwd));
        if(doctor != null){
            doctor.setLatestLoginDate(new Date());//更新登录时间
            doctorDao.update(doctor);
            loginedDoctor = LoginedDoctor.parse(doctor);
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
    public LoginedHospital getLoginedHospital(HttpServletRequest request){
        return httpSessionDao.getLoginedHospital(request);
    }

    /**
     * 读取当前已登录个人账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public LoginedPerUser getLoginedPerUser(HttpServletRequest request) {
        return httpSessionDao.getLoginedPerUser(request);
    }

    /**
     * 读取当前已登录医生账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public LoginedDoctor getLoginedDoctor(HttpServletRequest request) {
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
    public LoginedHospital hospitalLogout(HttpServletRequest request) {
        return httpSessionDao.deleteHospital(request);
    }

    /**
     * 个人账号退出登录
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public LoginedPerUser perUserLogout(HttpServletRequest request){
        return httpSessionDao.deleteLoginedPerUser(request);
    }

    /**
     * 医生账号退出登录
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public LoginedDoctor doctorLogout(HttpServletRequest request){
        return httpSessionDao.deleteLoginedDoctor(request);
    }
}
