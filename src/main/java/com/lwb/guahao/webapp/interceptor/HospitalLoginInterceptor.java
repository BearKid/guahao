package com.lwb.guahao.webapp.interceptor;

import com.lwb.guahao.webapp.service.LoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Lu Weibiao
 * Date: 2015/3/1 15:19
 */
@Component
public class HospitalLoginInterceptor implements HandlerInterceptor {
    @Resource
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isLogined = loginService.isHospitalLogined(request);
        System.out.println("hospital account is logined:" + isLogined);
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
