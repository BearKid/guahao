package com.lwb.guahao.webapp.controller.my_per;

import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.service.PerUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 1:18
 */
@Controller(value = "myPerUserAccountController")
public class PerUserAccountController {
    @Resource
    private LoginService loginService;
    @Resource
    private PerUserService perUserService;

    @RequestMapping(value = "myPer/logout")
    public String logout(HttpServletRequest request, Model model){
        loginService.perUserLogout(request);
        model.addAttribute("redirectUrl","/");
        return "redirect:/";
    }
}
