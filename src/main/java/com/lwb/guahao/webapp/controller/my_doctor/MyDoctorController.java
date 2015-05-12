package com.lwb.guahao.webapp.controller.my_doctor;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.qo.DoctorPerTimeScheduleQo;
import com.lwb.guahao.webapp.service.DoctorService;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.service.ReservationService;
import com.lwb.guahao.webapp.vo.DoctorPerTimeScheduleVo;
import com.lwb.guahao.webapp.vo.DoctorVo;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: Lu Weibiao
 * Date: 2015/3/8 1:09
 */
@Controller(value = "myDoctorController")
@RequestMapping(value = "/myDoctor")
public class MyDoctorController {
    @Resource
    private DoctorService doctorService;
    @Resource
    private LoginService loginService;
    @Resource
    private ReservationService reservationService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model) {
        DoctorVo loginedDoctor = loginService.getLoginedDoctor(request);
        model.addAttribute("doctor", loginedDoctor);
        return "/my_doctor/index";
    }

    @RequestMapping(value = "reservations")
    public String reservations(HttpServletRequest request,Model model){
        Integer doctorId = loginService.getLoginedDoctorId(request);

        DoctorPerTimeScheduleQo doctorPerTimeScheduleQo = new DoctorPerTimeScheduleQo();
        doctorPerTimeScheduleQo.setDoctorId(doctorId);

        DateTime startOfDay = DateTime.now().withTimeAtStartOfDay();
        DateTime endOfDay = startOfDay.plusDays(1);
        doctorPerTimeScheduleQo.setStartDateTime(startOfDay.toDate());
        doctorPerTimeScheduleQo.setEndDateTime(endOfDay.toDate());
        doctorPerTimeScheduleQo.setPageSize(Constants.INFINITE_PAGE_SIZE);

        Paging<DoctorPerTimeScheduleVo> doctorPerTimeSchedulePaging = doctorService.getDoctorPerTimeScheduleAndReservationsBy(doctorPerTimeScheduleQo);
        List<DoctorPerTimeScheduleVo> doctorPerTimeScheduleVoList = doctorPerTimeSchedulePaging.getItems();

        model.addAttribute("doctorPerTimeScheduleList",doctorPerTimeScheduleVoList);
        return "/my_doctor/reservations";
    }

    @RequestMapping(value = "reservation/{reservationId}/setPresent")
    public void setReservationPresent(HttpServletRequest request, Model model, @PathVariable Integer reservationId){
        ApiRet apiRet;
        apiRet = reservationService.updateReservationOrderStatus(reservationId,Constants.OrderStatus.PRESENT);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
    }

    @RequestMapping(value = "reservation/{reservationId}/setAbsent")
    public void setReservationAbsent(HttpServletRequest request, Model model, @PathVariable Integer reservationId){
        ApiRet apiRet;
        apiRet = reservationService.updateReservationOrderStatus(reservationId,Constants.OrderStatus.ABSENT);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
    }
}
