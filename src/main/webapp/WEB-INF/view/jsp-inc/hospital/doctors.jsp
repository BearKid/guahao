<%--
  User: Lu Weibiao
  Date: 2015/3/15 10:45
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="deptClassPanel" class="popup-sidebar">
    <div style="position:relative;">
        <button id="jsCancelDeptClassPanel" type="button" class="close"><span>&times;</span></button>
        <button id="jsOkDeptClassPanel" type="button" class="close glyphicon glyphicon-ok"></button>
    </div>
    <h3>选择科室:<span id="selectedFirstDeptClass"></span><span id="selectedSecondDeptClass"></span></h3>
    <ul class="nav nav-tabs" style="margin-bottom: 1em;">
        <li id="firstDeptClassNavTag" role="presentation" class="active"><a href="#">一级科室</a></li>
        <li id="secondDeptClassNavTag" role="presentation" style="display: none;"><a href="#">二级科室</a></li>
    </ul>
    <div id="firstDeptClassPanel">
        <c:forEach var="firstDeptClass" items="${deptClassList}">
            <button type="button" class="btn btn-default" data-id="${firstDeptClass.code}">${firstDeptClass.name}</button>
        </c:forEach>
    </div>
    <div id="secondDeptClassPanel" style="display: none;">
        <c:forEach var="firstDeptClass" items="${deptClassList}">
            <div id="secondDeptClasses${firstDeptClass.code}" style="display: none;"><!-- 某个一级科室对应的所有二级科室 Wrapper -->
                <c:forEach var="secondDeptClass" items="${firstDeptClass.subDeptClassList}">
                    <button type="button" class="btn btn-default" data-id="${secondDeptClass.code}">${secondDeptClass.name}</button>
                    <!-- 某个一级科室对应的所有二级科室 -->
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
<form id="doctorSearchForm" class="form-inline" action="${applicationScope.contextPath}/hospital/doctors">
    <div class="form-group">
        <label for="name">名称</label>
        <input name="name" type="text" class="form-control" id="name" placeholder="输入医生名称">
    </div>
    <div class="form-group">
        <label for="inputDeptClassName">科室类目</label>
        <input id="inputDeptClassName" type="text" class="form-control" placeholder="选择科室">
        <span class="input-group-btn">
            <button id="jsDeptClasses" class="btn btn-default" type="button">选择</button>
          </span>
        <input name="deptClassCode" id="inputDeptClassCode" type="hidden" class="form-control">
    </div>
    <div class="form-group">
        <label for="accountName">账号名</label>
        <input name="accountName" type="text" class="form-control" id="accountName" placeholder="输入账号">
    </div>
    <button type="submit" class="btn btn-default">查找</button>
</form>
<button type="button" class="btn btn-default">创建账号</button>
<table class="table table-bordered">
   <tr>
       <th>账号</th>
       <th>名称</th>
       <th>性别</th>
       <th>年龄</th>
       <th>科室</th>
       <th>挂号费（元）</th>
       <th>操作</th>
   </tr>
    <c:forEach var="doctor" items="${doctors}">
    <tr data-doctor-id="${doctor.id}">
        <td>${doctor.accountName}</td>
        <td>${doctor.name}</td>
        <td>${doctor.sex}</td>
        <td>${doctor.age}</td>
        <td>${doctor.deptClassCode}</td>
        <td>${doctor.price}</td>
        <td>
            <a class="jsDoctorDetail" href="${applicationScope.contextPath}/doctor/${doctor.id}" target="_blank">详情</a>
            <a class="jsEditDoctor" href="#">修改</a>
            <a class="jsDoctorShcedule" href="#">排班</a>
        </td>
    </tr>
    </c:forEach>
</table>
<script>
    //弹出选择城市侧边栏
    $("#jsDeptClasses").click(function () {
        var $deptClassPanel = $("#deptClassPanel");
        $deptClassPanel.css("marginLeft", -$deptClassPanel.width());
        $deptClassPanel.show().animate({marginLeft: 0}, function () {
            $("#screenMask").show();
        });
    });
    //选择某个省份
    $("#firstDeptClassPanel button").click(function () {
        var $firstDeptClass = $(this);
        var firstDeptClassCode = $firstDeptClass.data("id");
        var firstDeptClassName = $firstDeptClass.text();
        var $firstDeptClassNavTag = $("#firstDeptClassNavTag");
        var $secondDeptClassNavTag = $("#secondDeptClassNavTag");
        var $firstDeptClassPanel = $("#firstDeptClassPanel");
        var $secondDeptClassesPanel = $("#secondDeptClassesPanel");
        //导航标签切换
        $firstDeptClassNavTag.removeClass("active");
        $secondDeptClassNavTag.addClass("active");
        $secondDeptClassNavTag.show();
        //面板切换
        $firstDeptClassPanel.hide();
        var $secondDeptClassesContainer = $secondDeptClassesPanel.children("#secondDeptClasses" + firstDeptClassCode);
        $secondDeptClassesContainer.addClass("active");
        $secondDeptClassesContainer.show();
        $secondDeptClassesPanel.show()
        //显示选择的地区
        $("#selectedFirstDeptClass").text(firstDeptClassName);
    });
    //选择某个市
    $("#secondDeptClassesPanel").find("button").click(function () {
        var $this = $(this);
        var secondDeptClassCode = $this.data("id");
        var secondDeptClassName = $this.text();
        //设置选择的城市
        $("#selectedCity").text(secondDeptClassName);
        var $jsOkDeptClassPanel = $("#jsOkDeptClassPanel");
        $jsOkDeptClassPanel.data("deptClassCode", secondDeptClassCode);
    });
    //切换回省份导航标签
    $("#firstDeptClassNavTag").click(function () {
        $("#selectedFirstDeptClass").text("");
        $("#selectedCity").text("");
        $("#jsOKCancelPanel").data("deptClassCode", "");
        //导航标签切换
        $("#secondDeptClassNavTag").removeClass("active").hide();
        $("#firstDeptClassNavTag").addClass("active").show();
        //面板切换
        var $firstDeptClassPanel = $("#firstDeptClassPanel");
        var $secondDeptClassesPanel = $("#secondDeptClassesPanel");
        $secondDeptClassesPanel.children("div .active").removeClass("active").hide();
        $secondDeptClassesPanel.hide();
        $firstDeptClassPanel.show();
    });
    //取消并关闭选择城市侧边栏
    $("#jsCancelDeptClassPanel").click(function () {
        $("#deptClassPanel").hide();
        $("#screenMask").hide();
        $("#firstDeptClassNavTag").trigger("click");
    });
    //确认已选择的城市并关闭侧边栏
    $("#jsOkDeptClassPanel").click(function () {
        var deptClassCode = $(this).data("deptClassCode");
        if (deptClassCode) {
            $("#inputDeptClassCode").val(deptClassCode);//设置选择的deptClassCode到表单
            $("#inputDeptClassName").val($("#selectedFirstDeptClass").text() + $("#selectedCity").text());
            $("#jsCancelDeptClassPanel").trigger("click");
        } else {
            alert("未选择城市");
        }
    });
    
    //查找医生
    $("#doctorSearchForm").ajaxSubmit({
        dataType:"html",
        beforeSubmit:function(){
            showLoadingGif();
            return true;
        },
        success: function (data) {
            hideLoadingGif();
            $("#main").html(data);
        }
    })
    //医生详情页
//    $(".jsDoctorDetail").click(function(){
//        var doctorId = $(this).parent().parent().data("doctorId");
//        var url = "/doctor/"+doctorId;
//        window.open(url);
//    });
    //修改医生信息页面
    $(".jsEditDoctor").click(function(){
        var doctorId = $(this).parent().parent().data("doctorId");
        var url = "/hospital/doctor/"+doctorId + "/edit";
        showLoadingGif();
        $("#main").load(url,function(){
            hideLoadingGif();
        });
    });
    //医生排班页面
    $(".jsDoctorShcedule").click(function(){
        var doctorId = $(this).parent().parent().data("doctorId");
        var url = "/hospital/doctor/"+doctorId + "/schedules";
        showLoadingGif();
        $("#main").load(url,function(){
            hideLoadingGif();
        });
    });
</script>