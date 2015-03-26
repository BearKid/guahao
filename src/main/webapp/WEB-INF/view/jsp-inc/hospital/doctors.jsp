<%--
  User: Lu Weibiao
  Date: 2015/3/15 10:45
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${applicationScope.contextPath}/js/jquery/pager.js"></script>
<style>
    #doctorSearchForm {
        margin-bottom: 20px;
    }
</style>
<jsp:include page="/inc/common/deptClassSelectBox"/>
<form id="doctorSearchForm" class="form-inline" action="${applicationScope.contextPath}/hospital/doctors">
    <div class="form-group">
        <label for="name">名称</label>
        <input name="name" type="text" class="form-control" id="name" placeholder="输入医生名称" value="${name}">
    </div>
    <div class="form-group">
        <label for="inputDeptClassName">科室类目</label>
        <input id="inputDeptClassName" type="text" class="form-control" placeholder="选择科室" value="${deptClassName}" readonly>
        <button id="jsDeptClasses" class="btn btn-default" type="button">选择</button>
        <input name="deptClassCode" id="inputDeptClassCode" type="hidden" class="form-control" value="${deptClassCode}">
    </div>
    <div class="form-group">
        <label for="accountName">账号名</label>
        <input name="accountName" type="text" class="form-control" id="accountName" placeholder="输入账号"
               value="${accountName}">
    </div>
    <button type="submit" class="btn btn-default">查找</button>
    <button id="jsCreateDoctor" type="button" class="btn btn-default">创建账号</button>
</form>
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
    <c:forEach var="doctor" items="${doctorPaging.items}">
        <tr data-doctor-id="${doctor.id}">
            <td>${doctor.accountName}</td>
            <td>${doctor.name}</td>
            <td>${doctor.sex}</td>
            <td>${doctor.age}</td>
            <td>${doctor.deptClassName}</td>
            <td>${doctor.price}</td>
            <td>
                <a class="jsDoctorDetail" href="${applicationScope.contextPath}/doctor/${doctor.id}"
                   target="_blank">详情</a>
                <a class="jsEditDoctor" href="#">修改</a>
                <a class="jsDoctorShcedule" href="#">排班</a>
            </td>
        </tr>
    </c:forEach>
</table>
<div id="doctorSearchPager"></div>
<script>
    /*************** 上面部分以后提取到公共文件 分割线 ************************/

    //查找结果分页
    $("#doctorSearchPager").pager({
        pagenumber: ${doctorPaging.pn},
        pagecount: ${doctorPaging.totalPages},
        buttonClickCallback: function (pn) {
            var url = "${applicationScope.contextPath}/hospital/doctors?${queryStringWithoutPn}&pn=" + pn;
            var navMap = new Map();
            navMap.put("医院管理", "${applicationScope.contextPath}/hospital/doctors");
            Hospital.loadHtmlByUrl(url, navMap);
        }
    });
    //查找医生
    $("#doctorSearchForm").ajaxForm({
        dataType: "html",
        beforeSubmit: function () {
            showLoadingGif();
            return true;
        },
        success: function (data) {
            hideLoadingGif();
            $("#main").html(data);
        }
    });

    //创建医生页面
    $("#jsCreateDoctor").click(function () {
        var url = "${applicationScope.contextPath}/hospital/doctor/empty";
        var navMap = new Map();
        navMap.put("医生管理", "${applicationScope.contextPath}/hospital/doctors");
        navMap.put("创建账号", url);
        Hospital.loadHtmlByUrl(url, navMap);
    });

    //医生详情页
    $(".jsDoctorDetail").click(function () {
        var doctorId = $(this).parent().parent().data("doctorId");
        var url = "${applicationScope.contextPath}/doctor/" + doctorId;
        window.open(url);
    });

    //修改医生信息页面
    $(".jsEditDoctor").click(function () {
        var doctorId = $(this).parent().parent().data("doctorId");
        var url = "${applicationScope.contextPath}/hospital/doctor/" + doctorId + "/edit";
        var navMap = new Map();
        navMap.put("医生管理", "${applicationScope.contextPath}/hospital/doctors");
        navMap.put("修改医生信息", url);
        Hospital.loadHtmlByUrl(url, navMap);
    });

    //医生排班页面
    $(".jsDoctorShcedule").click(function () {
        var doctorId = $(this).parent().parent().data("doctorId");
        var url = "${applicationScope.contextPath}/hospital/doctor/" + doctorId + "/dailySchedules";
        var navMap = new Map();
        navMap.put("医生管理", "${applicationScope.contextPath}/hospital/doctors");
        navMap.put("医生排班", url);
        Hospital.loadHtmlByUrl(url, navMap);
    });
</script>