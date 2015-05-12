<%--
  User: Lu Weibiao
  Date: 2015/5/12 22:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 今天详细排班以及预约情况 -->
<h4>今天的排班和预约情况：</h4>
<c:forEach var="doctorPerTimeSchedule"
           items="${doctorPerTimeScheduleList}">
<table id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}"
       class="table table-bordered table-hover doctorPerTimeSchedule">
        <tr>
            <td>${doctorPerTimeSchedule.startTime} ~ ${doctorPerTimeSchedule.endTime} &nbsp;
            <a class="toggleReservationList" href="javascript:void(0);" data-doctor-per-time-schedule-id="${doctorPerTimeSchedule.id}">展开</a> </td>
        </tr>
    <tr>
        <td>
            <table id="reservationList-${doctorPerTimeSchedule.id}" class="table table-bordered table-hover reservationList" style="display:none;">
                <tr>
                    <th>患者名称</th>
                    <th>身份证号</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="reservation" items="${doctorPerTimeSchedule.reservationList}">
                    <tr class="reservation" id="reservation-${reservation.id}">
                        <td> ${reservation.perUserName}</td>
                        <td>${reservation.perUserIdCard}</td>
                        <td id="reservationOperation-${reservation.id}">
                            <a class="operation presentReservation" href="javascript:void(0);" data-reservation-id="${reservation.id}">应约</a>
                            <a class="operation absenceReservation" href="javascript:void(0);" data-reservation-id="${reservation.id}">爽约</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
</c:forEach>
<script>
    $(".presentReservation").click(function(){
        $this = $(this);
        var reservationId = $this.data("reservation-id");
        var url = "${applicationScope.contextPath}/myDoctor/reservation/" + reservationId + "/setPresent.json";
        getJsonByUrl(url,function(data){
            $ModalBox.open(data.msg);
            if(data.ret === API_RET_SUCCESS){
                setTimeout(function(){
                    $ModalBox.close();
                    $("#reservationOperation-" + reservationId).html("已应约");
                },1000);
            }
        });
    });

    $(".absenceReservation").click(function(){
        $this = $(this);
        var reservationId = $this.data("reservation-id");
        var url = "${applicationScope.contextPath}/myDoctor/reservation/" + reservationId + "/setAbsent.json";
        getJsonByUrl(url,function(data){
            $ModalBox.open(data.msg);
            if(data.ret === API_RET_SUCCESS){
                setTimeout(function(){
                    $ModalBox.close();
                    $("#reservationOperation-" + reservationId).html("已爽约");
                },1000);
            }
        });
    });

    $(".toggleReservationList").click(function(){
       $this = $(this);
        var doctorPerTimeScheduleId = $this.data("doctor-per-time-schedule-id");
        $("#reservationList-" + doctorPerTimeScheduleId).toggle();
    });
</script>

