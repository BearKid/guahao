package com.lwb.guahao.webapp.controller.hospital;

import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.webapp.vo.LoginedHospital;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 22:10
 */
@Controller
@RequestMapping(value = "/hospital")
public class HospitalController {
    @Resource
    private LoginService loginService;
    @Resource
    private HospitalService hospitalService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model){
        LoginedHospital loginedHospital = loginService.getLoginedHospital(request);
        model.addAttribute("hospital",loginedHospital);
        return "/hospital/index";
    }
}
