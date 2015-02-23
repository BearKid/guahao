package com.lwb.guahao.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lu Weibiao on 2015/2/22 21:31.
 */
@Controller
public class HospitalAccountController {
    /**
     * 医院账户注册页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "hospital/register", method = RequestMethod.GET)
    public String registerPage(HttpServletRequest request, Model model){
        return "hospital/registerPage";
    }
}
