package com.lwb.guahao.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwb.guahao.common.AreaUtil;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.FieldValidationUtil;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.service.HospitalService;
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
    private HospitalService hospitalService;
    /**
     * 医院账户注册页面
     * @return
     */
    @RequestMapping(value = "hospital/register", method = RequestMethod.GET)
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
    @RequestMapping(value = "hospital/register", method = RequestMethod.POST)
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
            String h;
            try {
                h = new ObjectMapper().writeValueAsString(hospital);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            errMsg.put("unfinished","请将信息填写完整" + h);
        } else{
            if(!FieldValidationUtil.isTelPhone(hospital.getTelPhone())){
                errMsg.put("telPhone","无效号码");
            }
            if(!rePwd.equals(hospital.getPassword())){
                errMsg.put("rePwd","重复输入密码错误");
            }
        }
        if(errMsg.isEmpty()){
            Integer id = hospitalService.save(hospital);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(Constants.LOGIN_HOSPITAL_ID, id);//注册成功后登录
            model.addAttribute("redirectUrl","/hospital/index");
        } else{
            model.addAttribute("errMsg",errMsg);
        }
    }
}
