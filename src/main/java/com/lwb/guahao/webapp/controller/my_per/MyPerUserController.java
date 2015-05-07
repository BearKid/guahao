package com.lwb.guahao.webapp.controller.my_per;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.Paging;
import com.lwb.guahao.common.qo.ReservationQo;
import com.lwb.guahao.webapp.component.PagingComponent;
import com.lwb.guahao.webapp.service.LoginService;
import com.lwb.guahao.webapp.service.PerUserService;
import com.lwb.guahao.webapp.service.ReservationService;
import com.lwb.guahao.webapp.vo.PerUserVo;
import com.lwb.guahao.webapp.vo.ReservationQoVo;
import com.lwb.guahao.webapp.vo.ReservationVo;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by Lu Weibiao on 2015/2/14 19:22.
 */
@Controller(value = "myPerUserController")
@RequestMapping(value = "/myPer")
public class MyPerUserController {
    @Resource
    private PerUserService perUserService;
    @Resource
    private LoginService loginService;
    @Resource
    private ReservationService reservationService;
    @Resource
    private PagingComponent pagingComponent;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, Model model) {
        PerUserVo perUserVo = loginService.getLoginedPerUser(request);
        model.addAttribute("perUser", perUserVo);
        return "/my_per/index";
    }

    /**
     * 预约管理页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "reservations")
    public String getReservations(HttpServletRequest request, Model model, ReservationQoVo reservationQoVo) throws ParseException{
        ApiRet apiRet;
        Integer perUserId = loginService.getLoginedPerUserId(request);

        ReservationQo reservationQo = ReservationQo.parse(reservationQoVo);
        reservationQo.setPerUserId(perUserId);
        if(reservationQo.getCreateDateTimeEnd()!= null) {//将结束日期设置为结束天的最晚时间
            DateTime dateTime = new DateTime(reservationQo.getCreateDateTimeEnd());
            reservationQo.setCreateDateTimeEnd(dateTime.plusDays(1).withTimeAtStartOfDay().toDate());
        }

        apiRet = reservationService.getReservationPagingBy(reservationQo);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
        model.addAttribute("reservationPaging",(Paging<ReservationVo>)apiRet.getData());
        model.addAttribute("reservationQo",reservationQoVo);
        model.addAttribute("queryStringWithoutPn",pagingComponent.getQueryStringWithoutPn(request));
        return "/my_per/reservations";
    }

    /**
     * 创建预约挂号订单
     * @param request
     * @param model
     */
    @RequestMapping(value = "reservation/create")
    public void createReservation(HttpServletRequest request,Model model){
        String doctorPerTimeScheduleIdStr = request.getParameter("doctorPerTimeScheduleId");
        Integer doctorPerTimeScheduleId = StringUtils.isEmpty(doctorPerTimeScheduleIdStr) ? null : Integer.valueOf(doctorPerTimeScheduleIdStr);
        Integer perUserId = loginService.getLoginedPerUserId(request);

        ApiRet apiRet;
        apiRet = reservationService.createReservation(perUserId, doctorPerTimeScheduleId);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
    }

    /**
     * 取消预约
     * @param request
     * @param model
     */
    @RequestMapping(value = "reservation/{reservationIdStr}/cancel")
    public void cancelReservation(HttpServletRequest request, Model model, @PathVariable String reservationIdStr){
        Integer perUserId = loginService.getLoginedPerUserId(request);
        Integer reservationId = StringUtils.isEmpty(reservationIdStr) ? null : Integer.valueOf(reservationIdStr);

        ApiRet apiRet;
        apiRet = reservationService.cancelReservation(perUserId, reservationId);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
    }

    @RequestMapping(value = "reservation/{reservationIdStr}/pay")
    public void payForReservation(HttpServletRequest request, Model model, @PathVariable String reservationIdStr){
        Integer perUserId = loginService.getLoginedPerUserId(request);
        Integer reservationId = StringUtils.isEmpty(reservationIdStr) ? null : Integer.valueOf(reservationIdStr);

        ApiRet apiRet;
        apiRet = reservationService.payForReservation(perUserId, reservationId);

        model.addAttribute("ret", apiRet.getRet());
        model.addAttribute("msg", apiRet.getMsg());
    }
}
