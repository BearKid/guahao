package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.vo.HospitalVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/5/5 17:06
 */
@Controller(value = "publicHospitalController")
@RequestMapping(value = "/hospital")
public class HospitalController {
    @Resource
    private HospitalService hospitalService;

    @RequestMapping(value = "{hospitalId}/detail")
    public String hospitalDetail(HttpServletRequest request,Model model,@PathVariable Integer hospitalId){
        HospitalVo hospitalVo = hospitalService.getHospital(hospitalId);
        Map<>
        return "/pub/hospital/detail";
    }
}
