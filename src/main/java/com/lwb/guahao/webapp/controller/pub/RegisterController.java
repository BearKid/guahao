package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.option.util.AreaUtil;
import com.lwb.guahao.common.util.FieldValidationUtil;
import com.lwb.guahao.common.model.Hospital;
import com.lwb.guahao.common.model.PerUser;
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
        return "/pub/register/hospital/registerPage";
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
        String msg = "";
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
            msg += "请将信息填写完整" + System.lineSeparator();
        } else{
            if(!FieldValidationUtil.isTelPhone(hospitalForm.getTelPhone())){
                msg += "无效国定电话号码" + System.lineSeparator();
            }
            if(!rePwd.equals(hospitalForm.getPassword())){
                msg += "重复输入密码错误" + System.lineSeparator();
            }
            if(hospitalService.isRegistered(hospitalForm.getEmail())){
                msg += "邮箱已经被注册" + System.lineSeparator();
            }
        }
        ApiRet apiRet = new ApiRet();
        if(StringUtils.isEmpty(msg)) {
            apiRet = hospitalService.register(hospitalForm);
            Hospital newHospital = (Hospital)apiRet.getData();
            loginService.hospitalLogin(request,newHospital.getEmail(), hospitalForm.getPassword());//注意传入的密码应该为明文密码
            model.addAttribute("redirectUrl","/myHospital/index");
        } else{
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg(msg);
        }
        model.addAttribute("ret",apiRet.getRet());
        model.addAttribute("msg",apiRet.getMsg());
    }

    /**
     * 个人账户注册页面
     * @return
     */
    @RequestMapping(value = "per", method = RequestMethod.GET)
    public String perUserRegisterPage(){
        return "/pub/register/per/registerPage";
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
            apiRet = perUserService.register(perUserForm);
            if(apiRet.getRet() == ApiRet.RET_SUCCESS){
                PerUser newUser = (PerUser)apiRet.getData();
                apiRet.setRet(ApiRet.RET_SUCCESS);
                String account = !StringUtils.isEmpty(newUser.getEmail()) ? newUser.getEmail() : newUser.getMobilePhone();
                loginService.perUserLogin(request, account, perUserForm.getPassword());//注意传入的密码应该为明文密码
                model.addAttribute("redirectUrl", "/myPer/index");
            }
        } else {
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("你填写注册信息有误");
        }
        model.addAttribute("ret",apiRet.getRet());
        model.addAttribute("msg",apiRet.getMsg());
    }
}
