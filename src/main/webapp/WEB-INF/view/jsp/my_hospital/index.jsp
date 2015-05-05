<%--
  User: Lu Weibiao
  Date: 2015/2/22 22:30
--%>
<%@ page import="com.lwb.guahao.common.constants.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
    </style>
</head>
<%--<!-- /my_hospital/index.jsp-->--%>
<body>
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.HOSPITAL%>"/>
</jsp:include>
<img id="loadingGif" src="${applicationScope.contextPath}/img/loading.gif" style="position: absolute;top:50%;left:50%;margin-top:-24px;margin-left: -24px;display: none;"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-4 col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="#" id="jsHospitalBaseInfo">基本信息</a></li>
                <li><a href="#" id="jsHospitalDoctors">医生管理</a></li>
                <li><a href="#" id="jsHospitalSecurity">账号安全</a></li>
            </ul>
        </div>
        <div class="col-xs-8 col-xs-offset-4 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
            <div id="stepBar"></div>
            <div id="main" class="main"></div>
        </div>
    </div>
</div>
</body>
<script>
    $(document).ready(function(){
        $("#jsHospitalBaseInfo").trigger("click");
    });
    $("#jsHospitalBaseInfo").click(function(){
        curNavItem = $(this).parent();
        navItems = curNavItem.siblings().removeClass("active");
        curNavItem.addClass("active");
        var navMap = new Map();
        navMap.put("基本信息","${applicationScope.contextPath}/myHospital/baseInfo");
        Hospital.loadHtmlByUrl("${applicationScope.contextPath}/myHospital/baseInfo",navMap);
    });
    $("#jsHospitalDoctors").click(function(){
        curNavItem = $(this).parent();
        navItems = curNavItem.siblings().removeClass("active");
        curNavItem.addClass("active");
        var navMap = new Map();
        navMap.put("医院管理","${applicationScope.contextPath}/myHospital/doctors");
        Hospital.loadHtmlByUrl("${applicationScope.contextPath}/myHospital/doctors",navMap);
    });
    $("#jsHospitalSecurity").click(function(){
        curNavItem = $(this).parent();
        navItems = curNavItem.siblings().removeClass("active");
        curNavItem.addClass("active");
        var navMap = new Map();
        navMap.put("账号安全","${applicationScope.contextPath}/myHospital/security");
        <%--var navMap = new Object();--%>
        <%--navMap["账号安全"] = "${applicationScope.contextPath}/myHospital/security";--%>
        Hospital.loadHtmlByUrl("${applicationScope.contextPath}/myHospital/security",navMap);
    });
</script>
</html>
