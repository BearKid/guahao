<%@ page import="com.lwb.guahao.common.model.Reservation" %>
<%@ page import="com.lwb.guahao.common.Constants" %>
<%@ page import="com.lwb.guahao.webapp.vo.ReservationVo" %>
<%--
  User: Lu Weibiao
  Date: 2015/5/7 15:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    #reservationSearchForm{
        margin-top: 20px;
    }
    #reservationTable{
        margin-top: 20px;
    }
    #reservationTable td{
        vertical-align: middle;
    }
    #reservationTable .operation{
        display: inline-block;
        margin: 5px;
    }
</style>
<form id="reservationSearchForm" class="form-inline"
      action="${applicationScope.contextPath}/myPer/reservations">
    <div class="form-group">
        <label for="createDateTimeStart">订单日期</label>
        <input name="createDateTimeStart" type="text" class="form-control" id="createDateTimeStart" placeholder="起始日期"
               value="${reservationQo.createDateTimeStart}"> ~
        <input name="createDateTimeEnd" type="text" class="form-control" id="createDateTimeEnd" placeholder="结束日期"
               value="${reservationQo.createDateTimeEnd}">
    </div>
    <button id="jsSubmitReservationSearchForm" type="button" class="btn btn-default">查找</button>
</form>
<table id="reservationTable" class="table table-bordered">
    <tr>
        <th>就诊时间</th>
        <th>医生</th>
        <th>医院</th>
        <th>挂号费（元）</th>
        <th>预约状态</th>
        <th>订单创建时间</th>
        <th>操作</th>
    </tr>
    <c:forEach var="reservation" items="${reservationPaging.items}">
        <tr data-reservation-id="${reservation.id}">
            <td>${reservation.visitDoctorDay}<br/>${reservation.doctorPerTimeSchedule.startTime} ~ ${reservation.doctorPerTimeSchedule.endTime}</td>
            <td><a href="${applicationScope.contextPath}/doctor/${reservation.doctorId}/detail">${reservation.doctorName}</a></td>
            <td><a href="${applicationScope.contextPath}/hospital/${reservation.hospitalId}/detail">${reservation.hospitalName}</a></td>
            <td>${reservation.doctorPerTimeSchedule.price}</td>
            <td id="orderStatusName-${reservation.id}" class="orderStatusName">${reservation.orderStatusName}</td>
            <td>${reservation.createDateTime}</td>
            <td id="reservationOperations-${reservation.id}">
                <c:set var="reservationTemp" value="${reservation}" scope="request"/>
                <%
                    ReservationVo reservation = (ReservationVo)request.getAttribute("reservationTemp");
                    Integer orderStatusCode = Integer.valueOf((String) reservation.getOrderStatusCode());
                %>

                <% if (orderStatusCode.equals(Constants.OrderStatus.UN_PAYED) || orderStatusCode.equals(Constants.OrderStatus.PAYED)){ %>
                <a href="#" class="operation jsCancelReservation" data-reservation-id="${reservation.id}">取消预约</a>
                <% } else {%>
                <span>-</span>
                <% } %>

                <% if (orderStatusCode.equals(Constants.OrderStatus.UN_PAYED)){ %>
                <a href="#" class="operation jsPayReservation" data-reservation-id="${reservation.id}">支付</a>
                <% } %>
            </td>
        </tr>
    </c:forEach>
</table>
<div id="reservationPager"></div>
<script src="${applicationScope.contextPath}/js/jquery/pager.js"></script>
<script>
    //表单条件查询预约订单
    $("#jsSubmitReservationSearchForm").click(function(){
       MyPerUser.loadHtmlByForm("#reservationSearchForm");
    });

    //查询预约订单分页
    $("#reservationPager").pager({
        pagenumber: ${reservationPaging.pn},
        pagecount: ${reservationPaging.totalPages},
        buttonClickCallback: function (pn) {
            var url = "${applicationScope.contextPath}/myPer/reservations?${queryStringWithoutPn}&pn="+pn;
            MyPerUser.loadHtmlByUrl(url);
        }
    });

    //取消预约
    $(".jsCancelReservation").click(function(){
        $this = $(this);
        var reservationId = $this.data("reservation-id") ;
        var url = "${applicationScope.contextPath}/myPer/reservation/" + reservationId + "/cancel.json";
        getJsonByUrl(url,function(data){
            $ModalBox.open(data.msg);
            if(data.ret === API_RET_SUCCESS){
                setTimeout(function(){
                    $ModalBox.close();
                    $("#orderStatusName-" + reservationId).html("预约已取消");
                    $("#reservationOperations-" + reservationId).html("-");
                },1000);
            }
        });
    });
    //支付预约订单
    $(".jsPayReservation").click(function(){
        $this = $(this);
        var reservationId = $this.data("reservation-id") ;
        var url = "${applicationScope.contextPath}/myPer/reservation/" + reservationId + "/pay.json";
        getJsonByUrl(url,function(data){
            $ModalBox.open(data.msg);
            if(data.ret === API_RET_SUCCESS){
                setTimeout(function(){
                    $ModalBox.close();
                    $("#orderStatusName-" + reservationId).html("预约成功");
                    $this.hide();
                },2000);
            }
        });
    });
</script>
