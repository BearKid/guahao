package com.lwb.guahao.webapp.dao;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 21:30
 */

import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.model.PerUser;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录缓存信息Dao
 */
@Repository
public class HttpSessionDao {
    private final static String HOSPITAL_KEY = "hospital:key";
    private static final String PER_USER_KEY = "perUser:key";
    private static final String DOCTOR_KEY = "doctor:key";

    /**
     * 缓存医院账号登录信息
     * Session作为内存缓存区
     *
     * @param request
     * @param hospital
     */
    public void saveHospital(HttpServletRequest request, Hospital hospital) {
        HttpSession session = request.getSession();
        session.setAttribute(HOSPITAL_KEY, hospital);
    }

    /**
     * 从缓存区取出医院账号登录信息
     * @param request
     * @return
     */
    public Hospital getHospital(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Hospital) session.getAttribute(HOSPITAL_KEY);
    }

    /**
     * 从缓存区取出个人账号登录信息
     * @param request
     * @return
     */
    public PerUser getPerUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (PerUser) session.getAttribute(PER_USER_KEY);
    }
    /**
     * 从缓存区取出医生账号登录信息
     * @param request
     * @return
     */
    public Doctor getDoctor(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Doctor) session.getAttribute(DOCTOR_KEY);
    }
    /**
     * 从缓存区删除医院账号信息
     * @param request
     * @return
     */
    public Hospital deleteHospital(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Hospital hospital = (Hospital)session.getAttribute(HOSPITAL_KEY);
        session.removeAttribute(HOSPITAL_KEY);
        return hospital;
    }

}
