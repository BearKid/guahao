package com.lwb.guahao.webapp.controller.inc;

import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.LoginedDoctor;
import com.lwb.guahao.webapp.vo.LoginedHospital;
import com.lwb.guahao.webapp.vo.LoginedPerUser;
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
                    LoginedPerUser perUser = loginService.getLoginedPerUser(request);
                    if (perUser != null) {
                        accountInfo.put("accountTypeName", "个人");
                        accountInfo.put("name", perUser.getName());
                        accountInfo.put("accountContextPath", "/per");
                        break;
                    }
                    LoginedDoctor doctor = loginService.getLoginedDoctor(request);
                    if (doctor != null) {
                        accountInfo.put("accountTypeName", "医生");
                        accountInfo.put("name", doctor.getName());
                        accountInfo.put("accountContextPath", "/doctor");
                        break;
                    }
                    LoginedHospital hospital = loginService.getLoginedHospital(request);
                    if (hospital != null) {
                        accountInfo.put("accountTypeName", "医院");
                        accountInfo.put("name", hospital.getName());
                        accountInfo.put("accountContextPath", "/hospital");
                        break;
                    }
                    break;
                }
                case Constants.AccountType.PER_USER: {
                    LoginedPerUser perUser = loginService.getLoginedPerUser(request);
                    if (perUser != null) {
                        accountInfo.put("accountTypeName", "个人");
                        accountInfo.put("name", perUser.getName());
                        accountInfo.put("accountContextPath", "/per");
                    }
                    break;
                }
                case Constants.AccountType.DOCTOR: {
                    LoginedDoctor doctor = loginService.getLoginedDoctor(request);
                    if (doctor != null) {
                        accountInfo.put("accountTypeName", "医生");
                        accountInfo.put("name", doctor.getName());
                        accountInfo.put("accountContextPath", "/doctor");
                    }
                    break;
                }
                case Constants.AccountType.HOSPITAL: {
                    LoginedHospital hospital = loginService.getLoginedHospital(request);
                    if (hospital != null) {
                        accountInfo.put("accountTypeName", "医院");
                        accountInfo.put("name", hospital.getName());
                        accountInfo.put("accountContextPath", "/hospital");
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
}
