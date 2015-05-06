package com.lwb.guahao.webapp.interceptor;

import com.lwb.guahao.webapp.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Lu Weibiao
 * Date: 2015/3/1 15:19
 * 医院账号操作拦截器
 * 包括：登录验证
 */
@Component
public class MyHospitalInterceptor implements HandlerInterceptor {
    private final static Logger logger = Logger.getLogger(MyHospitalInterceptor.class);
    @Resource
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isLogined = loginService.isHospitalLogined(request);
        logger.debug("hospital account is logined:" + isLogined);
        if(!isLogined){
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
