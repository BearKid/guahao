package com.lwb.guahao.webapp.controller.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwb.guahao.common.AreaUtil;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.FieldValidationUtil;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public String logout(HttpServletRequest request, Model model){
        loginService.hospitalLogout(request);
        model.addAttribute("redirectUrl","/");
        return "redirect:/";
    }
}
