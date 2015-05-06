<%--
  User: Lu Weibiao
  Date: 2015/5/4 22:44
--%>
<%@ page import="com.lwb.guahao.common.Constants" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
        /*医生基本信息样式*/
        .doctor {
            border: 1px solid #e4e4e4;
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 20px;
            margin-top: 20px;
        }

        .doctor .row1, .doctor .row2 {
            margin-bottom: 15px;
        }

        .doctor .avatar {
        }

        .doctor .name, .hospital .name {
            color: #333;
            font-weight: bold;
            text-decoration: underline;
        }

        .doctor .hospitalName {
            background-color: #FFcc00;
            font-weight: bold;
            color: #fff;
            padding: 2px 10px;
        }

        .doctor .goodAtTag {
            background-color: #FF9966;
            border-radius: 5px;
            color: #fff;
            padding: 2px 5px;
            margin-right: 5px;
        }

        /*排班表样式*/
        #doctorDailyScheduleList table tr:hover{
            background-color: #FFFFCC;
        }
        .doctorPerTimeScheduleList{
            border-left-style: dotted;
        }
        #doctorDailyScheduleList{
            width: 100%;
            margin-top: 20px;
        }

        #doctorDailyScheduleList th{
            border: 1px solid #ddd;
            margin: 0px;
            padding: 8px;
        }
        #doctorDailyScheduleList td{
            margin: 0px;
            padding: 0px;
            border: none;
        }
        #doctorDailyScheduleList table{
            width: 100%;
            margin: 0px;
        }
        #doctorDailyScheduleList table td{
            border: 1px solid #ddd;
            border-top: none;
            padding: 8px;
        }

    </style>
</head>
<body>
<!-- /pub/doctor/detail.jsp -->
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.UNKNOWN %>"/>
</jsp:include>
<div class="container">
    <!-- 医生基本信息 -->
    <div class="doctor">
        <div class="avatar"><img src="${applicationScope.contextPath}${doctor.avatarPath}"/></div>
        <div>
            <div class="row1">
                <a class="name" href="${applicationScope.contextPath}/doctor/${doctor.id}/detail">${doctor.name}</a>&nbsp;&nbsp;${doctor.deptClassName}&nbsp;&nbsp;
                ${doctor.title}&nbsp;&nbsp;${doctor.age}岁&nbsp;&nbsp;${doctor.sex}&nbsp;&nbsp;
                <a class="hospitalName"
                   href="${applicationScope.contextPath}/hospital/${doctor.hospitalId}/detail">${doctor.hospital.name}</a>
            </div>
            <div class="row2">
                <c:forEach items="${doctor.goodAtTags}" var="goodAt">
                    <span class="goodAtTag">${goodAt}</span>
                </c:forEach>
            </div>
            <div class="doctorBrief row3"><b>简介：</b>${doctor.brief}</div>
        </div>
    </div>

    <!-- 医生排班 -->
    <form id="doctorDailyScheduleSearchForm" class="form-inline"
          action="${applicationScope.contextPath}/doctor/${doctor.id}/detail">
        <div class="form-group">
            <label for="startDay">排班日期</label>
            <input name="startDay" type="text" class="form-control" id="startDay" placeholder="起始日期"
                   value="${doctorDailyScheduleQo.startDay}"> ~
            <input name="endDay" type="text" class="form-control" id="endDay" placeholder="结束日期"
                   value="${doctorDailyScheduleQo.endDay}">
        </div>
        <div class="checkbox">
            <label>
                <input name="ignoreNoScheduleDay" type="checkbox" value="true"
                       <c:if test="${not empty doctorDailyScheduleQo.ignoreNoScheduleDay}">checked</c:if>/> 忽略没排班的日期
            </label>
        </div>
        <button id="jsSubmitdoctorSearchForm" type="submit" class="btn btn-default">查找</button>
    </form>
    <table id="doctorDailyScheduleList">
        <tr>
            <th width="40%">日期</th>
            <th width="15%">总号源</th>
            <th width="15%">剩余号源</th>
            <th width="15%">挂号费用（元）</th>
            <th>操作</th>
        </tr>
        <c:if test="${empty doctorDailySchedulePaging || empty doctorDailySchedulePaging.items}">
            <tr>
                <td colspan="5">无</td>
            </tr>
        </c:if>
        <c:if test="${not empty doctorDailySchedulePaging.items}">
            <c:forEach var="doctorDailySchedule" items="${doctorDailySchedulePaging.items}">
                <tr>
                    <td colspan="5">
                        <!--某天排班-简略-->
                        <table id="briefDoctorDailySchedule-${doctorDailySchedule.id}" class="briefDoctorDailySchedule">
                            <tr>
                                <td width="40%">${doctorDailySchedule.takeEffectiveDate}</td>
                                <c:choose>
                                    <c:when test="${empty doctorDailySchedule.doctorPerTimeScheduleList}">
                                        <td width="15%"><span>/</span></td>
                                        <td width="15%"><span>/</span></td>
                                        <td width="15%">
                                            <span>/</span>
                                            <input name="price" type="text" value="" style="display:none;width: 100% "/>
                                        </td>
                                        <td>-</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td width="15%">${doctorDailySchedule.totalSource}</td>
                                        <td width="15%">${doctorDailySchedule.oddSource}</td>
                                        <td width="15%">
                                            <span>${doctorDailySchedule.price}</span>
                                            <input name="price" type="text" value="${doctorDailySchedule.price}"
                                                   style="display:none;width: 100%"/>
                                        </td>
                                        <td>
                                            <a href="#" class="doctorDailyScheduleToggleDetail"
                                               data-doctor-daily-schedule-id="${doctorDailySchedule.id}">展开</a>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </table>
                        <!-- 某天详细排班 -->
                        <table id="doctorPerTimeScheduleList-${doctorDailySchedule.id}"
                               class="doctorPerTimeScheduleList" style="display:none;">
                            <c:forEach var="doctorPerTimeSchedule"
                                       items="${doctorDailySchedule.doctorPerTimeScheduleList}">
                                <tr class="doctorPerTimeSchedule" id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}"
                                    id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}">
                                    <td width="40%">
                                            ${doctorPerTimeSchedule.startTime} ~ ${doctorPerTimeSchedule.endTime}</td>
                                    <td width="15%">${doctorPerTimeSchedule.totalSource}</td>
                                    <td width="15%">${doctorPerTimeSchedule.oddSource}</td>
                                    <td width="15%">${doctorPerTimeSchedule.price}</td>
                                    <td>
                                        <a class="doctorPerTimeScheduleReserve" href="#"
                                           data-doctor-per-time-schedule-id="${doctorPerTimeSchedule.id}">预约</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <div id="doctorDailySchedulePager"></div>
</div>
</body>
<script src="${applicationScope.contextPath}/js/jquery/pager.js"></script>
<script>

    //展开、收起某天排班详情
    $(".briefDoctorDailySchedule .doctorDailyScheduleToggleDetail").click(function () {
        var $this = $(this);
        var doctorDailyScheduleId = $this.data("doctorDailyScheduleId");
        var $doctorDailyScheduleList = $("#doctorPerTimeScheduleList-" + doctorDailyScheduleId);

        if ($doctorDailyScheduleList.css("display") === "none") {
            $doctorDailyScheduleList.show();
            $this.text("收起");
            //可能是从修改面板切换到详情面板
            var $doctorPerTimeScheduleInputList = $("#doctorPerTimeScheduleInputList-" + doctorDailyScheduleId);
            if ($doctorPerTimeScheduleInputList.css("display") !== "none") {
                $doctorPerTimeScheduleInputList.hide();
                $this.next().show();//呈现修改按钮
                $this.next().next().hide();//隐藏保存按钮
            }
        } else {
            $doctorDailyScheduleList.hide();
            $this.text("展开");
        }
    });

    //查找结果分页
    $("#doctorDailySchedulePager").pager({
        pagenumber: ${doctorDailySchedulePaging.pn},
        pagecount: ${doctorDailySchedulePaging.totalPages},
        buttonClickCallback: function (pn) {
            var url = "${applicationScope.contextPath}/doctor/${doctor.id}/detail?${queryStringWithoutPn}&pn=" + pn;
            window.location = url;
        }
    });

    //预约某个排班
    $(".doctorPerTimeScheduleReserve").click(function () {
        var $this = $(this);
        var curDoctorPerTimeScheduleId = $this.data("doctorPerTimeScheduleId");
        var url = "${applicationScope.contextPath}/myPer/doctorPerTimeSchedule/" + curDoctorPerTimeScheduleId + "/reserve.json";
        getJsonByUrl(url, function (data) {
            $ModalBox.open(data.msg);
        });
    });
</script>
</html>