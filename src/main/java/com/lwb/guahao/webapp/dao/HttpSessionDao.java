package com.lwb.guahao.webapp.dao;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 21:30
 */

import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.webapp.vo.PerUserVo;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录缓存信息Dao
 */
@Repository
public class HttpSessionDao {
    private final static String LOGINED_HOSPITAL_KEY = "loginedHospital:key";
    private static final String LOGINED_DOCTOR_KEY = "loginedDoctor:key";
    private static final String LOGINED_PER_USER_KEY = "loginedPerUser:key";

    /**
     * 缓存登录态的个人账号信息
     * @param request
     * @return
     */
    public void saveLoginedPerUser(HttpServletRequest request,PerUserVo perUserVo) {
        HttpSession session = request.getSession();
        session.setAttribute(LOGINED_PER_USER_KEY, perUserVo);
    }

    /**
     * 缓存登录态的医院账号登录信息
     * @param request
     * @param loginedHospital
     */
    public void saveLoginedHospital(HttpServletRequest request, HospitalVo loginedHospital) {
        HttpSession session = request.getSession();
        session.setAttribute(LOGINED_HOSPITAL_KEY, loginedHospital);
    }

    /**
     * 缓存登录态的医生账号登录信息
     * @param request
     * @param loginedDoctor
     */
    public void saveLoginedDocotr(HttpServletRequest request, DoctorVo loginedDoctor) {
        HttpSession session = request.getSession();
        session.setAttribute(LOGINED_DOCTOR_KEY, loginedDoctor);
    }

    /**
     * 从缓存区取出登录态的医院账号登录信息
     * @param request
     * @return
     */
    public HospitalVo getLoginedHospital(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (HospitalVo) session.getAttribute(LOGINED_HOSPITAL_KEY);
    }

    /**
     * 从缓存区取出登录态的个人账号登录信息
     * @param request
     * @return
     */
    public PerUserVo getLoginedPerUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (PerUserVo) session.getAttribute(LOGINED_PER_USER_KEY);
    }

    /**
     * 从缓存区取出登录态医生账号登录信息
     * @param request
     * @return
     */
    public DoctorVo getLoginedDoctor(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (DoctorVo) session.getAttribute(LOGINED_DOCTOR_KEY);
    }

    /**
     * 从缓存区删除登录态的医院账号信息
     * @param request
     * @return
     */
    public HospitalVo deleteHospital(HttpServletRequest request) {
        HttpSession session = request.getSession();
        HospitalVo loginedHospital = (HospitalVo) session.getAttribute(LOGINED_HOSPITAL_KEY);
        session.removeAttribute(LOGINED_HOSPITAL_KEY);
        return loginedHospital;
    }

    /**
     * 从缓存区删除登录态的个人账号信息
     * @param request
     * @return
     */
    public PerUserVo deleteLoginedPerUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        PerUserVo perUserVo = (PerUserVo)session.getAttribute(LOGINED_PER_USER_KEY);
        session.removeAttribute(LOGINED_PER_USER_KEY);
        return perUserVo;
    }

    /**
     * 从缓存区删除登录态的医生账号信息
     * @param request
     * @return
     */
    public DoctorVo deleteLoginedDoctor(HttpServletRequest request) {
        HttpSession session = request.getSession();
        DoctorVo loginedDoctor = (DoctorVo)session.getAttribute(LOGINED_DOCTOR_KEY);
        session.removeAttribute(LOGINED_DOCTOR_KEY);
        return loginedDoctor;
    }
}
