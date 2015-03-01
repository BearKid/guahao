package com.lwb.guahao.webapp.controller;

import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 22:13
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 医院账号登录
     * @param request
     * @param model
     * @param email
     * @param password
     * @param redirectUrl
     */
    @RequestMapping(value = "hospital", method = RequestMethod.POST)
    public void doHospitalLogin(HttpServletRequest request, Model model, String email, String password, String redirectUrl){
        Hospital hospital = loginService.hospitalLogin(email,password,request);
        String warnMsg = null;
        if(hospital == null){
            warnMsg = "账号不存在或密码输入错误";
        }
        if(!StringUtils.isEmpty(warnMsg)) {
            model.addAttribute("warnMsg",warnMsg);
        } else{
            System.out.println(redirectUrl);
            model.addAttribute("redirectUrl", (redirectUrl == null) ? "/hospital/index" : redirectUrl );
        }
    }
}
