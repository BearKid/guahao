package com.lwb.guahao.webapp.controller;

import com.lwb.guahao.common.util.FieldValidationUtil;
import com.lwb.guahao.webapp.component.WebPageComponent;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.LoginedDoctor;
import com.lwb.guahao.webapp.vo.LoginedHospital;
import com.lwb.guahao.webapp.vo.LoginedPerUser;
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
 * User: Lu Weibiao
 * Date: 2015/2/28 22:13
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Resource
    private LoginService loginService;
    @Resource
    private WebPageComponent webPageComponent;

    /**
     * 医院账号登录
     *
     * @param request
     * @param model
     * @param accountName
     * @param password
     * @param redirectUrl
     */
    @RequestMapping(value = "hospital", method = RequestMethod.POST)
    public void doHospitalLogin(HttpServletRequest request, Model model, String accountName, String password, String redirectUrl) {
        Map errMsg = new HashMap();
        if (!FieldValidationUtil.isEmail(accountName)) {
            errMsg.put("accountName", "账号邮箱不合法");
        }
        if (StringUtils.isEmpty(password)) {
            errMsg.put("password", "密码不能为空");
        }
        if (!errMsg.isEmpty()) {
            model.addAttribute("errMsg", errMsg);
            return;
        }
        //尝试登录
        LoginedHospital loginedHospital = loginService.hospitalLogin(request, accountName, password);
        if (loginedHospital == null) {
            errMsg.put("login", "账号不存在或密码输入错误");
            model.addAttribute("errMsg", errMsg);
        } else {
            model.addAttribute("redirectUrl", (redirectUrl == null) ? webPageComponent.getAppContextPath() + "/hospital/index" : redirectUrl);
        }
    }

    @RequestMapping(value = "per", method = RequestMethod.POST)
    public void doPerUserLogin(HttpServletRequest request, Model model, String accountName, String password, String redirectUrl) {
        Map errMsg = new HashMap();
        if (StringUtils.isEmpty(accountName)) {
            errMsg.put("account", "请输入账号");
        } else if (!FieldValidationUtil.isMobilePhone(accountName) && !(FieldValidationUtil.isEmail(accountName))) {
            errMsg.put("account", "请输入邮箱或手机号码");
        }
        if (StringUtils.isEmpty(password)) {
            errMsg.put("password", "密码不能为空");
        }
        if (!errMsg.isEmpty()) {
            model.addAttribute("errMsg", errMsg);
            return;
        }
        //尝试登录
        LoginedPerUser loginedUser = loginService.perUserLogin(request, accountName, password);
        if (loginedUser == null) {
            errMsg.put("login", "账号或密码输入错误");
            model.addAttribute("errMsg", errMsg);
        } else {
            model.addAttribute("redirectUrl", (redirectUrl == null) ? webPageComponent.getAppContextPath() + "/per/index" : redirectUrl);
        }
    }

    @RequestMapping(value = "doctor", method = RequestMethod.POST)
    public void doDoctorLogin(HttpServletRequest request, Model model, String accountName, String password, String redirectUrl) {
        Map errMsg = new HashMap();
        if (StringUtils.isEmpty(accountName)) {
            errMsg.put("accountName", "请输入账号");
        }
        if (StringUtils.isEmpty(password)) {
            errMsg.put("password", "密码不能为空");
        }
        if (!errMsg.isEmpty()) {
            model.addAttribute("errMsg", errMsg);
            return;
        }
        //尝试登录
        LoginedDoctor loginedDoctor = loginService.doctorLogin(request, accountName, password);
        if (loginedDoctor == null) {
            errMsg.put("login", "账号或密码输入错误");
            model.addAttribute("errMsg", errMsg);
        } else {
            model.addAttribute("redirectUrl", (redirectUrl == null) ? webPageComponent.getAppContextPath() + "/doctor/index" : redirectUrl);
        }
    }
}
