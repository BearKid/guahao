package com.lwb.guahao.webapp.controller.hospital;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.constants.ConstantsMap;
import com.lwb.guahao.common.util.DateUtils;
import com.lwb.guahao.common.util.DeptClassUtil;
import com.lwb.guahao.common.util.IntegerUtils;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.DoctorPerTimeSchedule;
import com.lwb.guahao.webapp.component.PagingComponent;
import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.service.HospitalService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleQoVo;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleVo;
import com.lwb.guahao.webapp.vo.DoctorVo;
import com.lwb.guahao.webapp.vo.LoginedHospital;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/2/28 22:10
 */
@Controller
@RequestMapping(value = "/hospital")
public class HospitalController {
    private static final Logger logger = Logger.getLogger(HospitalController.class);
    @Resource
    private LoginService loginService;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private DoctorPerTimeScheduleService doctorPerTimeScheduleService;
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

    /**
     * 获取某个医生的排班列表（分页）
     * @param request
     * @param model
     * @param doctorId
     * @param qoVo
     * @return
     */
    @RequestMapping(value = "doctor/{id}/dailySchedules",method = RequestMethod.GET)
    public String doctorDailySchedules(HttpServletRequest request, Model model, @PathVariable(value = "id") Integer doctorId,DoctorDailyScheduleQoVo qoVo){
        Integer curHospitalId = loginService.getLoginedHospitalId(request);
        ApiRet apiRet = new ApiRet();
        String view = null;
        if(!hospitalService.hasThisDoctor(curHospitalId,doctorId)){
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("没有访问权限");
            view = null;
        } else{
            qoVo.setDoctorId(doctorId);
            apiRet = doctorPerTimeScheduleService.getPagingBy(qoVo);
            model.addAttribute("doctorDailyScheduleQo",qoVo);
            model.addAttribute("doctorDailySchedulePaging",(Paging<DoctorDailyScheduleVo>)apiRet.getData());
            model.addAttribute("queryStringWithoutPn", pagingComponent.getQueryStringWithoutPn(request));
            view = "/../jsp-inc/hospital/doctor/dailySchedules";
        }
        model.addAttribute("ret",apiRet.getRet());
        model.addAttribute("msg",apiRet.getMsg());
//        return view;
        return "/../jsp-inc/hospital/doctor/dailySchedules";
    }
    @RequestMapping(value = "dailySchedule/saveOrUpdate",method = RequestMethod.POST)
    public String doctorDailyScheduleSaveOrUpdate(HttpServletRequest request, Model model){
        ApiRet apiRet = new ApiRet();
        /*根据传入参数构建doctorPerTimeSchedule List*/
        String[] doctorPerTimeScheduleIdArr = request.getParameterValues("doctorPerTimeScheduleId");
        String[] startTimeArr = request.getParameterValues("startTime");
        String[] endTimeArr = request.getParameterValues("endTime");
        String[] totalSourceArr = request.getParameterValues("totalSource");
        String price = request.getParameter("price");
        String scheduleDay = request.getParameter("scheduleDay");
        String doctorId = request.getParameter("doctorId");
        int size = doctorPerTimeScheduleIdArr.length;
        List<DoctorPerTimeSchedule> doctorPerTimeScheduleList = new ArrayList<DoctorPerTimeSchedule>(size);
        try {
            for (int i = 0; i < size; i++) {
                DoctorPerTimeSchedule schedule = new DoctorPerTimeSchedule();
                schedule.setId(IntegerUtils.parseString(doctorPerTimeScheduleIdArr[i], null));
                schedule.setDoctorId(Integer.valueOf(doctorId));
                Date startDateTime = DateUtils.yearMonthDayWeekTimeFormatter.parse(scheduleDay + " " + startTimeArr[i]);
                schedule.setStartDateTime(startDateTime);
                Date endDateTime = DateUtils.yearMonthDayWeekTimeFormatter.parse(scheduleDay + " " + endTimeArr[i]);
                schedule.setEndDateTime(endDateTime);
                schedule.setTotalSource(IntegerUtils.parseString(totalSourceArr[i], 0));
                schedule.setOddSource(schedule.getTotalSource());
                schedule.setPrice(IntegerUtils.parseString(price,0)*1.0);
                doctorPerTimeScheduleList.add(schedule);
            }
            doctorPerTimeScheduleService.saveOrUpdate(doctorPerTimeScheduleList);
        }catch (Exception e){
            logger.error("doctorDailyScheduleSaveOrUpdate",e);
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("保存失败");
        }
        model.addAttribute("ret",apiRet.getRet());
        model.addAttribute("msg",apiRet.getMsg());
        return null;
    }
}
