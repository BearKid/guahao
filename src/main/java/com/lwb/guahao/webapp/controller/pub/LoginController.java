package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.util.FieldValidationUtil;
import com.lwb.guahao.webapp.component.WebPageComponent;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.webapp.vo.PerUserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        String msg = "";
        if (!FieldValidationUtil.isEmail(accountName)) {
            msg += "账号邮箱不合法" + System.lineSeparator();
        }
        if (StringUtils.isEmpty(password)) {
            msg += "密码不能为空" + System.lineSeparator();;
        }
        if (!StringUtils.isEmpty(msg)) {
            model.addAttribute("ret", ApiRet.RET_FAIL);
            model.addAttribute("msg", msg);
            return;
        }
        //尝试登录
        HospitalVo loginedHospital = loginService.hospitalLogin(request, accountName, password);
        if (loginedHospital == null) {
            msg = "账号不存在或密码输入错误";
            model.addAttribute("ret", ApiRet.RET_FAIL);
            model.addAttribute("msg", msg);
        } else {
            model.addAttribute("ret",ApiRet.RET_SUCCESS);
            model.addAttribute("redirectUrl", (redirectUrl == null) ? webPageComponent.getAppContextPath() + "/myHospital/index" : redirectUrl);
        }
    }

    @RequestMapping(value = "per", method = RequestMethod.POST)
    public void doPerUserLogin(HttpServletRequest request, Model model, String accountName, String password, String redirectUrl) {
        String msg = "";
        if (StringUtils.isEmpty(accountName)) {
            msg += "请输入账号" + System.lineSeparator();
        } else if (!FieldValidationUtil.isMobilePhone(accountName) && !(FieldValidationUtil.isEmail(accountName))) {
            msg += "请输入邮箱或手机号码" + System.lineSeparator();
        }
        if (StringUtils.isEmpty(password)) {
            msg += "密码不能为空" + System.lineSeparator();
        }
        if (!StringUtils.isEmpty(msg)) {
            model.addAttribute("ret", ApiRet.RET_FAIL);
            model.addAttribute("msg", msg);
            return;
        }
        //尝试登录
        PerUserVo loginedUser = loginService.perUserLogin(request, accountName, password);
        if (loginedUser == null) {
            msg = "账号不存在或密码输入错误";
            model.addAttribute("ret",ApiRet.RET_FAIL);
            model.addAttribute("msg", msg);
        } else {
            model.addAttribute("ret",ApiRet.RET_SUCCESS);
            model.addAttribute("redirectUrl", (redirectUrl == null) ? webPageComponent.getAppContextPath() + "/myPer/index" : redirectUrl);
        }
    }

    @RequestMapping(value = "doctor", method = RequestMethod.POST)
    public void doDoctorLogin(HttpServletRequest request, Model model, String accountName, String password, String redirectUrl) {
        String msg = "";
        if (StringUtils.isEmpty(accountName)) {
            msg += "请输入账号" + System.lineSeparator();
        }
        if (StringUtils.isEmpty(password)) {
            msg += "密码不能为空"+ System.lineSeparator();
        }
        if (!StringUtils.isEmpty(msg)) {
            model.addAttribute("ret", ApiRet.RET_FAIL);
            model.addAttribute("msg", msg);
            return;
        }
        //尝试登录
        DoctorVo loginedDoctor = loginService.doctorLogin(request, accountName, password);
        if (loginedDoctor == null) {
            msg += "账号不存在或密码输入错误";
            model.addAttribute("ret",ApiRet.RET_FAIL);
            model.addAttribute("msg", msg);
        } else {
            model.addAttribute("ret",ApiRet.RET_SUCCESS);
            model.addAttribute("redirectUrl", (redirectUrl == null) ? webPageComponent.getAppContextPath() + "/myDoctor/index" : redirectUrl);
        }
    }
}
