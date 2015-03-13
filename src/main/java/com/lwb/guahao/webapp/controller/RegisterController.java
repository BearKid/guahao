package com.lwb.guahao.webapp.controller;

import com.lwb.guahao.common.util.AreaUtil;
import com.lwb.guahao.common.util.FieldValidationUtil;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 账号注册控制类
 * @autor:Lu Weibiao Date: 2015/3/2 22:07
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {
    @Resource
    private LoginService loginService;
    @Resource
    private HospitalService hospitalService;
    /**
     * 医院账户注册页面
     * @return
     */
    @RequestMapping(value = "hospital", method = RequestMethod.GET)
    public String registerPage(Model model){
        model.addAttribute("areaList", AreaUtil.areaList);
        return "hospital/registerPage";
    }

    /**
     * 提交注册信息
     * @param hospital
     * @param rePwd
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "hospital", method = RequestMethod.POST)
    public void doRegister(Hospital hospital, String rePwd, HttpServletRequest request, Model model){
        Map errMsg = new HashMap<String,String>();
        if(!StringUtils.hasText(hospital.getEmail()) //字段验证
                || !StringUtils.hasText(hospital.getName())
                || !StringUtils.hasText(hospital.getAddress())
                || !StringUtils.hasText(hospital.getLinkman())
                || !StringUtils.hasText(hospital.getBrief())
                || !StringUtils.hasText(hospital.getTelPhone())
                || !StringUtils.hasText(hospital.getPassword())
                || !StringUtils.hasText(rePwd)
                || hospital.getAreaCode() == null
                ){
            errMsg.put("unfinished","请将信息填写完整");
        } else{
            if(!FieldValidationUtil.isTelPhone(hospital.getTelPhone())){
                errMsg.put("telPhone","无效号码");
            }
            if(!rePwd.equals(hospital.getPassword())){
                errMsg.put("rePwd","重复输入密码错误");
            }
            if(hospitalService.isRegistered(hospital.getEmail())){
                errMsg.put("isRegistered","邮箱已经被注册");
            }
        }
        if(errMsg.isEmpty()){
            Hospital newHospital = hospitalService.register(hospital);
            System.out.println(this.getClass() + ":" + hospital.getEmail() + " " + hospital.getPassword());
            loginService.hospitalLogin(request,newHospital.getEmail(),hospital.getPassword());//注意传入的密码应该为明文密码
            model.addAttribute("redirectUrl","/hospital/index");
        } else{
            model.addAttribute("errMsg",errMsg);
        }
    }
}
