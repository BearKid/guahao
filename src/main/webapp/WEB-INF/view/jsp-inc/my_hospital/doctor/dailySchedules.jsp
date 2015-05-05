<%--
  User: Lu Weibiao
  Date: 2015/3/23 22:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .doctorDailySchedule tr:hover{
        background-color: #FFFFCC;
    }
    .doctorPerTimeScheduleList,.doctorPerTimeScheduleInputList{
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

    #_doctorPerTimeScheduleInputTemplate{
        display: none;
    }
</style>
<form id="doctorSearchForm" class="form-inline"
      action="${applicationScope.contextPath}/myHospital/doctor/${doctorDailyScheduleQo.doctorId}/dailySchedules">
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
    <button id="jsSubmitdoctorSearchForm" type="button" class="btn btn-default">查找</button>
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
                    <form id="doctorDailyScheduleSaveForm-${doctorDailySchedule.id}" class="doctorDailyScheduleSaveForm doctorDailySchedule"
                          action="${applicationScope.contextPath}/myHospital/dailySchedule/saveOrUpdate.json" method="POST">
                        <input name="scheduleDay" type="hidden" value="${doctorDailySchedule.takeEffectiveDate}">
                        <input name="doctorId" type="hidden" value="${doctorDailyScheduleQo.doctorId}">
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
                                        <td>
                                            <a class="doctorPerTimeScheduleInputAdd" href="#" data-doctor-daily-schedule-id="${doctorDailySchedule.id}">新增</a>
                                            <a class="doctorDailyScheduleSave" href="#"
                                               data-doctor-daily-schedule-id="${doctorDailySchedule.id}" style="display: none;">>保存</a>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td width="15%">${doctorDailySchedule.totalSource}</td>
                                        <td width="15%">${doctorDailySchedule.oddSource}</td>
                                        <td width="15%">
                                            <span id="doctorDailySchedulePrice-${doctorDailySchedule.id}">${doctorDailySchedule.price}</span>
                                            <input id="doctorDailySchedulePriceInput-${doctorDailySchedule.id}" name="price" type="text" value="${doctorDailySchedule.price}" style="display:none;width: 100%"/>
                                        </td>
                                        <c:if test="${doctorDailySchedule.totalSource != doctorDailySchedule.oddSource}">
                                            <td>
                                                <a href="#" class="doctorDailyScheduleToggleDetail" data-doctor-daily-schedule-id="${doctorDailySchedule.id}">详情</a>
                                            </td>
                                        </c:if>
                                        <c:if test="${doctorDailySchedule.totalSource eq doctorDailySchedule.oddSource}">
                                            <td>
                                                <a class="doctorDailyScheduleToggleDetail" href="#"
                                                data-doctor-daily-schedule-id="${doctorDailySchedule.id}">详情</a>
                                                <a class="doctorDailyScheduleEdit" href="#"
                                                   data-doctor-daily-schedule-id="${doctorDailySchedule.id}">修改</a>
                                                <a class="doctorDailyScheduleSave" href="#"
                                                   data-doctor-daily-schedule-id="${doctorDailySchedule.id}" style="display: none;">保存</a>
                                            </td>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </table>
                        <!--某天详细排班-只读-->
                        <table id="doctorPerTimeScheduleList-${doctorDailySchedule.id}" class="doctorPerTimeScheduleList" style="display:none;">
                            <c:forEach var="doctorPerTimeSchedule"
                                       items="${doctorDailySchedule.doctorPerTimeScheduleList}">
                                <tr class="doctorPerTimeSchedule" id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}"
                                    id="doctorPerTimeSchedule-${doctorPerTimeSchedule.id}">
                                    <td width="40%">
                                        <%--<span style="border-left: 4px solid #EC8D5C;"></span>--%>
                                    ${doctorPerTimeSchedule.startTime} ~ ${doctorPerTimeSchedule.endTime}</td>
                                    <td width="15%">${doctorPerTimeSchedule.totalSource}</td>
                                    <td width="15%">${doctorPerTimeSchedule.oddSource}</td>
                                    <td width="15%">${doctorPerTimeSchedule.price}</td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </table>
                        <!--某天详细排班-修改-->
                        <table id="doctorPerTimeScheduleInputList-${doctorDailySchedule.id}" class="doctorPerTimeScheduleInputList" style="display:none;">
                            <c:forEach var="doctorPerTimeSchedule"
                                       items="${doctorDailySchedule.doctorPerTimeScheduleList}">
                                <tr class="doctorPerTimeScheduleInput"
                                    id="doctorPerTimeScheduleInput-${doctorPerTimeSchedule.id}"
                                    data-id="${doctorPerTimeSchedule.id}">
                                        <%--<form action="${applicationScope.contextPath}/myHospital/doctor/${doctorPerTimeSchedule.doctorId}/dailySchedule/${doctorPerTimeSchedule.id}/saveOrUpdate" method="post">--%>
                                    <input name="doctorPerTimeScheduleId" type="hidden"
                                           value="${doctorPerTimeSchedule.id}"/>
                                    <td width="40%">
                                        <input name="startTime" type="text" value="${doctorPerTimeSchedule.startTime}"/>
                                        &nbsp;~&nbsp;
                                        <input name="endTime" type="text" value="${doctorPerTimeSchedule.endTime}"/>
                                    </td>
                                    <td width="15%">
                                        <input name="totalSource" type="text" value="${doctorPerTimeSchedule.totalSource}"/>
                                    </td>
                                    <td width="15%">-</td>
                                    <td width="15%">-</td>
                                    <td>
                                        <a class="doctorPerTimeScheduleInputAdd" href="#"
                                           data-doctor-per-time-schedule-id="${doctorPerTimeSchedule.id}">新增</a>
                                        <a class="doctorPerTimeScheduleDelete" href="#"
                                           data-doctor-per-time-schedule-id="${doctorPerTimeSchedule.id}">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    <!--排班输入栏模板-->
    <tr id="_doctorPerTimeScheduleInputTemplate" class="doctorPerTimeScheduleInput">
        <input name="doctorPerTimeScheduleId" type="hidden" value=""/>
        <td width="40%">
            <input name="startTime" type="text" value=""/>
            &nbsp;~&nbsp;
            <input name="endTime" type="text" value=""/>
        </td>
        <td width="15%"><input name="totalSource" type="text" value="" style="width: 100%"/></td>
        <td width="15%">-</td>
        <td width="15%">-</td>
        <td>
            <a class="doctorPerTimeScheduleInputAdd" href="#">新增</a>&nbsp;&nbsp;
            <a class="doctorPerTimeScheduleDelete" href="#" data-doctor-per-time-schedule-id="">删除</a>
        </td>
    </tr>
</table>
<div id="doctorDailySchedulePager"></div>
<span id="doctorDailyScheduleCurUrl" style="display: none;">
${applicationScope.contextPath}/myHospital/doctor/${doctorDailyScheduleQo.doctorId}/dailySchedules?${queryStringWithoutPn}&pn=${doctorDailySchedulePaging.pn}
</span>
<script>
    $("#jsSubmitdoctorSearchForm").click(function () {
        Hospital.loadHtmlByForm("#doctorSearchForm", null);
    });

    //在已存在的排班输入栏下方新增排班输入栏
    $(".doctorPerTimeScheduleInputList .doctorPerTimeScheduleInputAdd,#_doctorPerTimeScheduleInputTemplate .doctorPerTimeScheduleInputAdd").click(function () {
        var $this = $(this);
        //var doctorPerTimeScheduleId = $this.data("doctorPerTimeScheduleId");
        var $curDoctorPerTimeScheduleInput = $this.parents(".doctorPerTimeScheduleInput");//当前点击的新增按钮所在排班
        var $newDoctorPerTimeScheduleInput = $("#_doctorPerTimeScheduleInputTemplate").clone(true);//新排班输入栏
        $newDoctorPerTimeScheduleInput.attr("id", "");
        $newDoctorPerTimeScheduleInput.data("id", "");
        $curDoctorPerTimeScheduleInput.after($newDoctorPerTimeScheduleInput);//在下方插入新排班输入栏
    });

    //在某天没有排班的记录中新增排班输入栏
    $(".briefDoctorDailySchedule .doctorPerTimeScheduleInputAdd").click(function () {
        var $this = $(this);
        var doctorDailyScheduleId = $this.data("doctorDailyScheduleId");
        var briefDoctorDailyShchedule = $("#briefDoctorDailySchedule-" + doctorDailyScheduleId);
        /*切换挂号费字段为输入框*/
        var priceInput = briefDoctorDailyShchedule.find("input[name='price']");
        priceInput.show();
        priceInput.prev().hide();
        /*插入首条排班输入栏*/
        var $doctorPerTimeScheduleInputList = $("#doctorPerTimeScheduleInputList-"+doctorDailyScheduleId);
        var $newDoctorPerTimeScheduleInput = $("#_doctorPerTimeScheduleInputTemplate").clone(true);//新排班输入栏
        $newDoctorPerTimeScheduleInput.attr("id", "");
        $newDoctorPerTimeScheduleInput.data("id", "");
        $doctorPerTimeScheduleInputList.append($newDoctorPerTimeScheduleInput);//在下方插入新排班输入栏
        $doctorPerTimeScheduleInputList.show();
        /*切换新增按钮为保存按钮*/
        $this.hide();
        $this.next().show();
    });

    //展开、收起某天排班详情
    $(".briefDoctorDailySchedule .doctorDailyScheduleToggleDetail").click(function(){
        var $this = $(this);
        var doctorDailyScheduleId = $this.data("doctorDailyScheduleId");
        var $doctorDailyScheduleList = $("#doctorPerTimeScheduleList-" + doctorDailyScheduleId);

        if($doctorDailyScheduleList.css("display") === "none"){
            $doctorDailyScheduleList.show();

            //可能是从修改面板切换到详情面板
            var $doctorPerTimeScheduleInputList = $("#doctorPerTimeScheduleInputList-" + doctorDailyScheduleId);
            if($doctorPerTimeScheduleInputList.css("display") !== "none") {
                $doctorPerTimeScheduleInputList.hide();
                $this.next().show();//呈现修改按钮
                $this.next().next().hide();//隐藏保存按钮
                $("#doctorDailySchedulePriceInput-" + doctorDailyScheduleId).hide();
                $("#doctorDailySchedulePrice-" + doctorDailyScheduleId).show();
            }
        } else{
            $doctorDailyScheduleList.hide();
            $this.text("详情");
        }
     });

    //展开排班修改
    $(".briefDoctorDailySchedule .doctorDailyScheduleEdit").click(function () {
        var $this = $(this);
        var doctorDailyScheduleId = $this.data("doctorDailyScheduleId");
        var $briefDoctorDailySchedule = $("#briefDoctorDailySchedule-" + doctorDailyScheduleId);
        /*切换为保存按钮*/
        $this.hide();
        $this.next().show();
        /*切换为编辑面板*/
        $("#doctorPerTimeScheduleList-" + doctorDailyScheduleId).hide();
        $("#doctorPerTimeScheduleInputList-" + doctorDailyScheduleId).show();
        /*切换挂号费字段为输入框*/
        var $priceInput = $briefDoctorDailySchedule.find("input[name='price']");
        $priceInput.show();
        $priceInput.prev().hide();
    });

    //删除排班输入栏
    $(".doctorPerTimeScheduleDelete").click(function () {
        var $this = $(this);
        var $curDoctorPerTimeScheduleInput = $this.parents(".doctorPerTimeScheduleInput");//当前点击的删除按钮所在排班输入栏
        var curDoctorPerTimeScheduleId = $curDoctorPerTimeScheduleInput.data("id");
        var $curDoctorPerTimeSchedule = $("#doctorPerTimeSchedule-" + curDoctorPerTimeScheduleId);//当前点击的删除按钮所在排班展示栏
        if (curDoctorPerTimeScheduleId) {//数据库记录删除
            var url = "${applicationScope.contextPath}/myHospital/doctorPerTimeSchedule/" +curDoctorPerTimeScheduleId +"/del.json";
            Hospital.getJsonByUrl(url, function (data) {
                if (data.ret == API_RET_SUCCESS) {//删除当前行的UI
                    $curDoctorPerTimeScheduleInput.remove();
                    $curDoctorPerTimeSchedule.remove();
                } else {
                    $ModalBox.open(data.msg);
                }
            });
        } else {
            $curDoctorPerTimeScheduleInput.remove();
        }
    });

    //保存当天所有排班
    $(".doctorDailyScheduleSave").click(function () {
        var $this = $(this);
        var doctorDailyScheduleId = $this.data("doctorDailyScheduleId");
        var doctorDailyScheduleSaveForm = $this.parents(".doctorDailyScheduleSaveForm");
        Hospital.getJsonByForm("#doctorDailyScheduleSaveForm-" + doctorDailyScheduleId, function (data) {
            $ModalBox.open(data.msg);
            if (data.ret == API_RET_SUCCESS) {
                setTimeout(function () {
                    $ModalBox.close();
                    var url = $("#doctorDailyScheduleCurUrl").text();
                    console.log(url);
                    Hospital.loadHtmlByUrl(url, null);
                }, 1000);
            }
        });
    });

    //查找结果分页
    $("#doctorDailySchedulePager").pager({
        pagenumber: ${doctorDailySchedulePaging.pn},
        pagecount: ${doctorDailySchedulePaging.totalPages},
        buttonClickCallback: function (pn) {
            var url = "${applicationScope.contextPath}/myHospital/doctor/${doctorDailyScheduleQo.doctorId}/dailySchedules/${queryStringWithoutPn}&pn="+pn;
            Hospital.loadHtmlByUrl(url, null);
        }
    });
</script>
