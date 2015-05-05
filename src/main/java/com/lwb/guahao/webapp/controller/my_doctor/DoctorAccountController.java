package com.lwb.guahao.webapp.controller.my_doctor;

import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 1:20
 */
@Controller(value = "myDocotorAccountController")
public class DoctorAccountController {
    @Resource
    private LoginService loginService;
    @Resource
    private DoctorService doctorService;

    @RequestMapping(value = "/myDoctor/logout")
    public String logout(HttpServletRequest request, Model model){
        loginService.doctorLogout(request);
        model.addAttribute("redirectUrl","/");
        return "redirect:/";
    }
}
