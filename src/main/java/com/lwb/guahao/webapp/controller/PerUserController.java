package com.lwb.guahao.webapp.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.service.PerUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/14 19:22.
 */
@Controller
@RequestMapping(value = "/per")
public class PerUserController {
    @Resource
    private PerUserService perUserService;

    @RequestMapping(value = "new/{id}")
    public String index(@PathVariable Integer id, HttpServletRequest request, Model model){
        PerUser user = new PerUser();
        if(id == 0) user.setId(null);
        else user.setId(id);
        user.setName("bill");
        user.setEmail(new Date(System.currentTimeMillis()).toString());
        user.setIdCard("1254235");
        user.setAccountStatus(Constants.AccountStatus.FORBIDDEN);
        user.setCreDate(new Date());
        user.setPassword("123");
        perUserService.saveOrUpdate(user);

        model.addAttribute(new Integer(111));
        model.addAttribute(new Integer(520));
        model.addAttribute("hello world");
        return "user";
    }
    @RequestMapping(value = "info/{id}")
    public String info(@PathVariable Integer id, HttpServletRequest request, Model model){
        model.addAttribute("perUser1", perUserService.get(id));
        model.addAttribute(new Integer(111));
        model.addAttribute(new Integer(520));
        model.addAttribute("per info");
        return "user";
    }
    @RequestMapping(value = "tx")
    public void tx(){
        perUserService.txTest();
    }
}
