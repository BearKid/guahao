package com.lwb.guahao.webapp.controller.my_doctor;

import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.DoctorVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 1:09
 */
@Controller(value = "myDoctorController")
@RequestMapping(value = "/myDoctor")
public class MyDoctorController {
    @Resource
    private DoctorService doctorService;
    @Resource
    private LoginService loginService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model) {
        DoctorVo loginedDoctor = loginService.getLoginedDoctor(request);
        model.addAttribute("doctor", loginedDoctor);
        return "/my_doctor/index";
    }
}
