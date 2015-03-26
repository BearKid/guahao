<%--
  User: Lu Weibiao
  Date: 2015/3/23 22:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form id="doctorSearchForm" class="form-inline" action="${applicationScope.contextPath}/hospital/doctor/${doctorDailyScheduleQo.doctorId}/dailySchedules">
    <div class="form-group">
        <label for="startDay">排班日期</label>
        <input name="startDay" type="text" class="form-control" id="startDay" placeholder="起始日期" value="${doctorDailyScheduleQo.startDay}">
        <input name="endDay" type="text" class="form-control" id="endDay" placeholder="起始日期" value="${doctorDailyScheduleQo.endDay}">
    </div>
    <div class="checkbox">
        <label>
            <input name="ignoreNoScheduleDay"type="checkbox" value="true" <c:if test="${not empty doctorDailyScheduleQo.ignoreNoScheduleDay}">checked</c:if>/> 忽略没排班的日期
        </label>
    </div>
    <button id="jsSubmitdoctorSearchForm" type="button" class="btn btn-default">查找</button>
</form>
<table class="table table-bordered">
    <tr>
        <th>日期</th>
        <th>总号源</th>
        <th>剩余号源</th>
        <th>挂号费用（元）</th>
        <th>操作</th>
    </tr>
    <c:if test="${empty doctorDailySchedulePaging || empty doctorDailySchedulePaging.items}">
        <tr>
            <td colspan="5">无</td>
        </tr>
    </c:if>
    <c:if test="${not empty doctorDailySchedulePaging.items}">
        <c:forEach var="doctorDailySchedule" items="${doctorDailySchedulePaging.items}">
            <form id="doctorDailyScheduleSaveForm-${doctorDailySchedule.id}"
                  action="${applicationScope.contextPath}/hospital/doctor/${doctorDailySchedule.doctorId}/dailySchedule/saveOrUpdate">
                <input name="scheduleDay" type="hidden" value="${doctorDailySchedule.takeEffectiveDate}">
                <tr>
                    <td>${doctorDailySchedule.takeEffectiveDate}</td>
                    <c:choose>
                        <c:when test="${empty doctorDailySchedule.doctorPerTimeScheduleList}">
                            <td> /</td>
                            <td> /</td>
                            <td> /</td>
                            <td>
                                <a href="#" class="doctorPerTimeScheduleAdd"
                                   data-doctorDailyScheduleId="${doctorDailySchedule.id}">新增</a>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>${doctorDailySchedule.totalSource}</td>
                            <td>${doctorDailySchedule.oddSource}</td>
                            <td>
                                <span>${doctorDailySchedule.price}</span>
                                <input type="text" value="${doctorDailySchedule.price}" style="display:none"/>
                            </td>
                            <c:if test="${doctorDailySchedule.totalSource != doctorDailySchedule.oddSource}">
                                <td>
                                    <a href="#" data-doctorDailyScheduleListId="${doctorDailySchedule.id}">展开</a>
                                </td>
                            </c:if>
                            <c:if test="${doctorDailySchedule.totalSource eq doctorDailySchedule.oddSource}">
                                <td>
                                    <a href="#" data-doctorDailyScheduleListId="${doctorDailySchedule.id}">展开</a>
                                    <a href="#" data-doctorDailyScheduleListId="${doctorDailySchedule.id}">修改</a>
                                    <a class="doctorPerTimeScheduleListSave" href="#"
                                       data-doctorDailyScheduleId="${doctorDailySchedule.id}">保存</a>
                                        <%--<a href="#">删除</a>--%>
                                </td>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <c:forEach var="doctorPerTimeSchedule" items="${doctorDailySchedule.doctorPerTimeScheduleList}">
                    <tr class="doctorPerTimeScheduleList doctorPerTimeScheduleList-${doctorDailySchedule.id}"
                        id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}"
                        style="display:none;">
                        <td>${doctorPerTimeSchedule.startTime} ~ ${doctorPerTimeSchedule.endTime}</td>
                        <td>${doctorPerTimeSchedule.totalSource}</td>
                        <td>${doctorPerTimeSchedule.oddSource}</td>
                        <td>${doctorPerTimeSchedule.price}</td>
                        <td></td>
                    </tr>
                </c:forEach>
                <c:forEach var="doctorPerTimeSchedule" items="${doctorDailySchedule.doctorPerTimeScheduleList}">
                    <tr class="doctorPerTimeScheduleList doctorPerTimeScheduleList-${doctorDailySchedule.id}"
                        id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}"
                        style="display:none;">
                            <%--<form action="${applicationScope.contextPath}/hospital/doctor/${doctorPerTimeSchedule.doctorId}/dailySchedule/${doctorPerTimeSchedule.id}/saveOrUpdate" method="post">--%>
                        <input name="doctorPerTimeScheduleId" type="hidden" value="${doctorPerTimeSchedule.id}"/>
                        <td>
                            <input name="startTime" type="text" value="${doctorPerTimeSchedule.startTime}"/>
                            &nbsp;~&nbsp;
                            <input name="endTime" type="text" value="${doctorPerTimeSchedule.endTime}"/>
                        </td>
                        <td><input name="totalSource" type="text" value="${doctorPerTimeSchedule.totalSource}"/></td>
                        <td>-</td>
                        <td>-</td>
                        <td><a class="doctorPerTimeScheduleAdd" href="#">新增</a></td>
                        <td><a class="doctorPerTimeScheduleDelete" href="#"
                               data-doctorPerTimeScheduleId="${doctorPerTimeSchedule.id}">删除</a></td>
                    </tr>
                </c:forEach>
            </form>
        </c:forEach>
    </c:if>
</table>
<script>
    $("#jsSubmitdoctorSearchForm").click(function(){
       Hospital.loadHtmlByForm("#doctorSearchForm",null);
    });
    //TODO 新增排班
    $("#doctorPerTimeScheduleAdd").click(function(){
        var doctorPerTimeScheduleRow = $('<tr class="doctorPerTimeScheduleList doctorPerTimeScheduleList-${doctorDailySchedule.id}"
        id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}">
        <input name="doctorPerTimeScheduleId" type="hidden" value=""/>
        <td>
        <input name="startTime" type="text" value=""/>
        &nbsp;~&nbsp;
        <input name="endTime" type="text" value=""/>
        </td>
        <td><input name="totalSource" type="text" value=""/></td>
        <td>-</td>
        <td>-</td>
        <td><a class="doctorPerTimeScheduleAdd" href="#">新增</a></td>
        <td><a class="doctorPerTimeScheduleDelete" href="#"
        data-doctorPerTimeScheduleId="${doctorPerTimeSchedule.id}">删除</a></td>
        </tr>');
    });
</script>
