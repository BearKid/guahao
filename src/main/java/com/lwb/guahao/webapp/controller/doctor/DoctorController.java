package com.lwb.guahao.webapp.controller.doctor;

import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.LoginedDoctor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 1:09
 */
@Controller
@RequestMapping(value = "/doctor")
public class DoctorController {
    @Resource
    private DoctorService doctorService;
    @Resource
    private LoginService loginService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model) {
        LoginedDoctor loginedDoctor = loginService.getLoginedDoctor(request);
        model.addAttribute("doctor", loginedDoctor);
        return "/doctor/index";
    }
}
