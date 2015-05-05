package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.webapp.component.WebPageComponent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lu Weibiao on 2015/2/13 20:28.
 */
@Controller
@RequestMapping(value = "")
public class IndexController {
    @Resource
    private WebPageComponent webPageComponent;

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, Model model) {
        return "index";
    }
}
