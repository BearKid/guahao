package com.lwb.guahao.webapp.controller.hospital;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.common.util.DeptClassUtil;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.webapp.component.PagingComponent;
import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.LoginedHospital;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        Paging<DoctorVo> doctorPaging = hospitalService.getDoctorPaging(curHospitalId, name, deptClassCode, accountName, pn);
        String queryStringWithoutPn = pagingComponent.getQueryStringWithoutPn(request);
        model.addAttribute("doctorPaging",doctorPaging);
        model.addAttribute("deptClassList", DeptClassUtil.deptClassList);
        model.addAttribute("queryStringWithoutPn",queryStringWithoutPn);
        model.addAttribute("name",name);
        model.addAttribute("deptClassCode",deptClassCode);
        model.addAttribute("deptClassName", ConstantsMap.deptClassMap.get(deptClassCode));
        model.addAttribute("accountName",accountName);
        return "/../jsp-inc/hospital/doctors";
    }

    /**
     * 医生账号创建页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "doctor/empty")
    public String emptyDoctor(HttpServletRequest request, Model model){
        return "/../jsp-inc/hospital/emptyDoctor";
    }

    /**
     * 创建医生账号
     * @param request
     * @param model
     * @param doctor
     */
    @RequestMapping(value = "doctor/create",method = RequestMethod.POST)
    public void createDoctor(HttpServletRequest request, Model model,Doctor doctor){
        ApiRet apiRet = new ApiRet();
        if(StringUtils.isEmpty(doctor.getAccountName())){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("账号名不能为空");
        } else if(StringUtils.isEmpty(doctor.getPassword())){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("密码不能为空");
        } else if(StringUtils.isEmpty(doctor.getName())){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("医生名称不能为空");
        } else if(doctor.getDeptClassCode() == null){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("科室类目不能为空");
        } else if(StringUtils.isEmpty(doctor.getSex())){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("医生性别不能为空");
        }
        if(apiRet.getRet() != ApiRet.RET_FAIL){
            Integer curHospitalId = loginService.getLoginedHospitalId(request);
            doctor.setHospitalId(curHospitalId);
            ApiRet apiRetFromCreateDoctor = hospitalService.createDoctor(doctor);
            apiRet.setRet(apiRetFromCreateDoctor.getRet());
            if(apiRetFromCreateDoctor.getRet() == ApiRet.RET_FAIL){
                apiRet.setMsg("创建医生账号失败！");
            } else if(apiRetFromCreateDoctor.getRet() == ApiRet.RET_SUCCESS){
                apiRet.setMsg("创建医生账号成功！");
            }
        }
        model.addAttribute("ret",apiRet.getRet());
        model.addAttribute("msg",apiRet.getMsg());
        model.addAttribute("data",apiRet.getData());
    }
}
