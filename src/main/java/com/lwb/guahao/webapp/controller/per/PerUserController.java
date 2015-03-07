package com.lwb.guahao.webapp.controller.per;

import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.service.PerUserService;
import com.lwb.guahao.webapp.vo.LoginedPerUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lu Weibiao on 2015/2/14 19:22.
 */
@Controller
@RequestMapping(value = "/per")
public class PerUserController {
    @Resource
    private PerUserService perUserService;
    @Resource
    private LoginService loginService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model) {
        LoginedPerUser loginedPerUser = loginService.getLoginedPerUser(request);
        model.addAttribute("perUser", loginedPerUser);
        return "/per/index";
    }
}
