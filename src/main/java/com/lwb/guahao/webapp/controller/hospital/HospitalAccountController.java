package com.lwb.guahao.webapp.controller.hospital;

import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lu Weibiao on 2015/2/22 21:31.
 */
@Controller
public class HospitalAccountController {
    @Resource
    private LoginService loginService;
    @Resource
    private HospitalService hospitalService;

    @RequestMapping(value = "hospital/logout")
    public String logout(HttpServletRequest request, Model model) {
        loginService.hospitalLogout(request);
        model.addAttribute("redirectUrl", "/");
        return "redirect:/";
    }
}
