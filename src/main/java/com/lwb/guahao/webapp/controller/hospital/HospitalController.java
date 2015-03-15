package com.lwb.guahao.webapp.controller.hospital;

import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.common.util.DeptClassUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.webapp.component.PagingComponent;
import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.LoginedHospital;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 22:10
 */
@Controller
@RequestMapping(value = "/hospital")
public class HospitalController {
    @Resource
    private LoginService loginService;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private PagingComponent pagingComponent;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model){
        return "/hospital/index";
    }
    @RequestMapping(value = "baseInfo")
    public String baseInfo(HttpServletRequest request, Model model){
        LoginedHospital loginedHospital = loginService.getLoginedHospital(request);
        model.addAttribute("hospital",loginedHospital);
        return "/../jsp-inc/hospital/baseInfo";
    }
    @RequestMapping(value = "doctors")
    public String doctors(HttpServletRequest request, Model model,
                          String name,
                          @RequestParam(required = false)Integer deptClassCode,
                          String accountName,
                          @RequestParam(required = false)Integer pn){
        Integer curHospitalId = loginService.getLoginedHospitalId(request);
        List<Doctor> doctors = hospitalService.getDoctors(curHospitalId, name, deptClassCode, accountName,pn);
        String queryStringWithoutPn = pagingComponent.getQueryStringWithoutPn(request);
        model.addAttribute("doctors",doctors);
        model.addAttribute("deptClassList", DeptClassUtil.deptClassList);
        model.addAttribute("queryStringWithoutPn",queryStringWithoutPn);
        model.addAttribute("name",name);
        model.addAttribute("deptClassCode",deptClassCode);
        model.addAttribute("accountName",accountName);
        model.addAttribute("pn",pn);
        return "/../jsp-inc/hospital/doctors";
    }
}
