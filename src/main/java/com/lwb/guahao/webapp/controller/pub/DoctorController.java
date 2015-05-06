package com.lwb.guahao.webapp.controller.pub;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.qo.DoctorDailyScheduleQo;
import com.lwb.guahao.webapp.component.PagingComponent;
import com.lwb.guahao.webapp.service.DoctorPerTimeScheduleService;
import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleQoVo;
import com.lwb.guahao.webapp.vo.DoctorDailyScheduleVo;
import com.lwb.guahao.webapp.vo.DoctorVo;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

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
    @Resource
    private PagingComponent pagingComponent;

    @RequestMapping(value = "{doctorIdStr}/detail")
    public String doctorDetail(HttpServletRequest request, Model model, @PathVariable String doctorIdStr, DoctorDailyScheduleQoVo doctorDailyScheduleQoVo) throws ParseException {
        Integer doctorId = Integer.valueOf(doctorIdStr);
        //获取医生信息
        DoctorVo doctorVo = doctorService.getDoctor(doctorId);

        /*解析查询条件对象*/
        doctorDailyScheduleQoVo.setDoctorId(doctorIdStr);
        DoctorDailyScheduleQo doctorDailyScheduleQo = DoctorDailyScheduleQo.parse(doctorDailyScheduleQoVo);

        //查询医生排班
        ApiRet<Paging<DoctorDailyScheduleVo>> apiRet = doctorPerTimeScheduleService.getPagingBy(doctorDailyScheduleQo);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
        model.addAttribute("doctor", doctorVo);
        model.addAttribute("doctorDailySchedulePaging", (Paging<DoctorDailyScheduleVo>) apiRet.getData());
        model.addAttribute("doctorDailyScheduleQo",doctorDailyScheduleQoVo);
        model.addAttribute("queryStringWithoutPn", pagingComponent.getQueryStringWithoutPn(request));

        return "/pub/doctor/detail";
    }
}
