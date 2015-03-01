package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.SecurityUtil;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.dao.HospitalDao;
import com.lwb.guahao.webapp.dao.HttpSessionDao;
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
    private HospitalDao hospitalDao;
    @Resource
    private HttpSessionDao httpSessionDao;
    /**
     * 医院登录
     * @param email
     * @param pwd
     * @return
     */
    public Hospital hospitalLogin(String email, String pwd, HttpServletRequest request){
        Hospital hospital = hospitalDao.uniqueByEmailAndPwd(email, SecurityUtil.password(pwd));
        if(hospital != null) {
            hospital.setLatestLoginDatetime(new Date()); //更新登录时间
            hospitalDao.update(hospital);
            httpSessionDao.saveHospital(request, hospital); //缓存
            return hospital;
        } else return null;
    }

    /**
     * 读取当前已登录医院账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Hospital getLoginedHospital(HttpServletRequest request){
        return httpSessionDao.getHospital(request);
    }

    /**
     * 判断客户端是否已经登录为医院账号
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isHospitalLogined(HttpServletRequest request){
        return httpSessionDao.getHospital(request) != null;
    }

    /**
     * 医院账号退出登录
     * @param request
     * @return
     */
    public Hospital hospitalLogout(HttpServletRequest request) {
        return httpSessionDao.deleteHospital(request);
    }
}
