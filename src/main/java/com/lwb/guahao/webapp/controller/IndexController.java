package com.lwb.guahao.webapp.controller;

import com.lwb.guahao.common.AreaUtil;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.component.WebPageComponent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/13 20:28.
 */
@Controller
@RequestMapping(value="")
public class IndexController {
    @Resource
    private WebPageComponent webPageComponent;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model){
        System.out.println("/index");
        model.addAttribute("areaList", AreaUtil.areaList);
        return "index";
    }
    @RequestMapping(value = "user")
    public ModelAndView user(HttpServletRequest request, ModelAndView model){
        System.out.println("/user");
        PerUser perUser = new PerUser();
        perUser.setId(1239);
        perUser.setCreDate(new Date());
        perUser.setAccountStatus(Constants.AccountStatus.NORMAL);
        perUser.setEmail("1030240314@qq.com");
        perUser.setIdCard("44190019930106735");
        perUser.setName("芦苇表");

        model.addObject("perUser",perUser);
        model.setViewName("user");
        return model;
    }
    @RequestMapping(value = "")
    public String index2(HttpServletRequest request, Model model){
        System.out.println("/");
        return "index";
    }
}
