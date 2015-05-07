package com.lwb.guahao.webapp.controller.inc;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.option.util.AreaUtil;
import com.lwb.guahao.common.option.util.DeptClassUtil;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.HospitalVo;
import com.lwb.guahao.webapp.vo.PerUserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @autor:Administrator Date: 2015/3/2
 * Time: 21:23
 */
@Controller
public class CommonIncController {
    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/inc/headerBar")
    public String headerBar(HttpServletRequest request, Model model, String accountType) {
        Map accountInfo = null;
        if (!StringUtils.isEmpty(accountType)) {
            accountInfo = new HashMap();
            int accountTypeCode = Integer.parseInt(accountType);
            switch (accountTypeCode) {
                case Constants.AccountType.UNKNOWN: {//未知类型，自动检测
                    PerUserVo perUser = loginService.getLoginedPerUser(request);
                    if (perUser != null) {
                        accountInfo.put("accountTypeName", "个人");
                        accountInfo.put("name", perUser.getName());
                        accountInfo.put("accountContextPath", "/myPer");
                        break;
                    }
                    DoctorVo doctor = loginService.getLoginedDoctor(request);
                    if (doctor != null) {
                        accountInfo.put("accountTypeName", "医生");
                        accountInfo.put("name", doctor.getName());
                        accountInfo.put("accountContextPath", "/myDoctor");
                        break;
                    }
                    HospitalVo hospital = loginService.getLoginedHospital(request);
                    if (hospital != null) {
                        accountInfo.put("accountTypeName", "医院");
                        accountInfo.put("name", hospital.getName());
                        accountInfo.put("accountContextPath", "/myHospital");
                        break;
                    }
                    break;
                }
                case Constants.AccountType.PER_USER: {
                    PerUserVo perUser = loginService.getLoginedPerUser(request);
                    if (perUser != null) {
                        accountInfo.put("accountTypeName", "个人");
                        accountInfo.put("name", perUser.getName());
                        accountInfo.put("accountContextPath", "/myPer");
                    }
                    break;
                }
                case Constants.AccountType.DOCTOR: {
                    DoctorVo doctor = loginService.getLoginedDoctor(request);
                    if (doctor != null) {
                        accountInfo.put("accountTypeName", "医生");
                        accountInfo.put("name", doctor.getName());
                        accountInfo.put("accountContextPath", "/myDoctor");
                    }
                    break;
                }
                case Constants.AccountType.HOSPITAL: {
                    HospitalVo hospital = loginService.getLoginedHospital(request);
                    if (hospital != null) {
                        accountInfo.put("accountTypeName", "医院");
                        accountInfo.put("name", hospital.getName());
                        accountInfo.put("accountContextPath", "/myHospital");
                    }
                    break;
                }
                default:
                    break;
            }
        }
        model.addAttribute("accountInfo", accountInfo);
        return "/../jsp-inc/header";
    }

    @RequestMapping(value = "/inc/common/deptClassSelectBox")
    public String deptClassSelectBox(HttpServletRequest request, Model model) {
        model.addAttribute("deptClassList", DeptClassUtil.deptClassList);
        return "/../jsp-inc/common/deptClassSelectBox";
    }

    @RequestMapping(value = "/inc/common/areaSelectBox")
    public String areaSelectBox(HttpServletRequest request, Model model) {
        model.addAttribute("areaList", AreaUtil.areaList);
        return "/../jsp-inc/common/areaSelectBox";
    }
}
