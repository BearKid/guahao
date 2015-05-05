package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.webapp.service.DoctorPerTimeScheduleService;
import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleQoVo;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleVo;
import com.lwb.guahao.webapp.vo.DoctorVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Lu Weibiao
 * Date: 2015/5/4 22:06
 */
@Controller(value = "publicDoctorController")
@RequestMapping(value = "/doctor")
public class DoctorController {
    @Resource
    private DoctorService doctorService;
    @Resource
    private DoctorPerTimeScheduleService doctorPerTimeScheduleService;

    @RequestMapping(value = "{doctorId}/detail")
    public String doctorDetail(HttpServletRequest request, Model model, @PathVariable Integer doctorId, DoctorDailyScheduleQoVo doctorDailyScheduleQoVo) {
        DoctorVo doctorVo = doctorService.getDoctor(doctorId);
        ApiRet<Paging<DoctorDailyScheduleVo>> apiRet = doctorPerTimeScheduleService.getPagingBy(doctorDailyScheduleQoVo);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
        model.addAttribute("doctor", doctorVo);
        model.addAttribute("doctorDailySchedulePaging", (Paging<DoctorDailyScheduleVo>) apiRet.getData());
        model.addAttribute("doctorDailyScheduleQo",doctorDailyScheduleQoVo);

        return "/pub/doctor/detail";
    }
}
