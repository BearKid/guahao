package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.util.AreaUtil;
import com.lwb.guahao.common.util.FieldValidationUtil;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.service.PerUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 账号注册控制类
 * @autor:Lu Weibiao Date: 2015/3/2 22:07
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {
    @Resource
    private LoginService loginService;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private PerUserService perUserService;

    /**
     * 医院账户注册页面
     * @return
     */
    @RequestMapping(value = "hospital", method = RequestMethod.GET)
    public String hospitalRegisterPage(Model model){
        model.addAttribute("areaList", AreaUtil.areaList);
        return "hospital/registerPage";
    }

    /**
     * 医院账号提交注册信息
     * @param hospitalForm
     * @param rePwd
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "hospital", method = RequestMethod.POST)
    public void doHospitalRegister(Hospital hospitalForm, String rePwd, HttpServletRequest request, Model model){
        Map errMsg = new HashMap<String,String>();
        if(!StringUtils.hasText(hospitalForm.getEmail()) //字段验证
                || !StringUtils.hasText(hospitalForm.getName())
                || !StringUtils.hasText(hospitalForm.getAddress())
                || !StringUtils.hasText(hospitalForm.getLinkman())
                || !StringUtils.hasText(hospitalForm.getBrief())
                || !StringUtils.hasText(hospitalForm.getTelPhone())
                || !StringUtils.hasText(hospitalForm.getPassword())
                || !StringUtils.hasText(rePwd)
                || hospitalForm.getAreaCode() == null
                ){
            errMsg.put("unfinished","请将信息填写完整");
        } else{
            if(!FieldValidationUtil.isTelPhone(hospitalForm.getTelPhone())){
                errMsg.put("telPhone","无效号码");
            }
            if(!rePwd.equals(hospitalForm.getPassword())){
                errMsg.put("rePwd","重复输入密码错误");
            }
            if(hospitalService.isRegistered(hospitalForm.getEmail())){
                errMsg.put("isRegistered","邮箱已经被注册");
            }
        }
        if(errMsg.isEmpty()){
            Hospital newHospital = hospitalService.register(hospitalForm);
            System.out.println(this.getClass() + ":" + hospitalForm.getEmail() + " " + hospitalForm.getPassword());
            loginService.hospitalLogin(request,newHospital.getEmail(), hospitalForm.getPassword());//注意传入的密码应该为明文密码
            model.addAttribute("redirectUrl","/hospital/index");
        } else{
            model.addAttribute("errMsg",errMsg);
        }
    }

    /**
     * 个人账户注册页面
     * @return
     */
    @RequestMapping(value = "per", method = RequestMethod.GET)
    public String perUserRegisterPage(){
        return "per/registerPage";
    }

    @RequestMapping(value = "per", method = RequestMethod.POST)
    public void doPerRegister(PerUser perUserForm, String rePwd, HttpServletRequest request, Model model){
        ApiRet apiRet = new ApiRet();

        boolean isValid;
        isValid = (!StringUtils.isEmpty(perUserForm.getEmail()) || !StringUtils.isEmpty(perUserForm.getMobilePhone()))
                && !StringUtils.isEmpty(perUserForm.getName())
                && !StringUtils.isEmpty(perUserForm.getPassword())
                && !StringUtils.isEmpty(rePwd) && rePwd.equals(perUserForm.getPassword())
                && FieldValidationUtil.isEmail(perUserForm.getEmail())
                && (StringUtils.isEmpty(perUserForm.getMobilePhone()) || FieldValidationUtil.isMobilePhone(perUserForm.getMobilePhone()));

        if(isValid){
            PerUser newUser = perUserService.register(perUserForm);
            if(newUser == null){
                apiRet.setRet(ApiRet.RET_FAIL);
                apiRet.setMsg("账号已注册");
            } else {
                apiRet.setRet(ApiRet.RET_SUCCESS);
                String account = !StringUtils.isEmpty(newUser.getEmail()) ? newUser.getEmail() : newUser.getMobilePhone();
                loginService.perUserLogin(request, account, perUserForm.getPassword());//注意传入的密码应该为明文密码
                model.addAttribute("redirectUrl", "/per/index");
            }
        } else {
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("你填写注册信息有误");
        }
        model.addAttribute("ret",apiRet.getRet());
        model.addAttribute("msg",apiRet.getMsg());
    }
}
