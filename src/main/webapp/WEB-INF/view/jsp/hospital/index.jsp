<%--
  User: Lu Weibiao
  Date: 2015/2/22 22:30
--%>
<%@ page import="com.lwb.guahao.common.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
    </style>
</head>
<%--<!-- hospital/index.jsp-->--%>
<body>
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.HOSPITAL%>"/>
</jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-4 col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">基本信息</a></li>
                <li><a href="#">医生管理</a></li>
                <li><a href="#">账号安全</a></li>
            </ul>
        </div>
        <div class="col-xs-8 col-xs-offset-4 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">基本信息</h1>
            <dl class="dl-horizontal">
                <dt>医院名称</dt>
                <dd>${hospital.name}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>所在地区</dt>
                <dd>${hospital.areaName}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>医院地址</dt>
                <dd>${hospital.address}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>医院联系人</dt>
                <dd>${hospital.linkman}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>联系固定电话</dt>
                <dd>${hospital.telPhone}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>医院简介</dt>
                <dd>${hospital.brief}</dd>
            </dl>

            <h1 class="page-header">账号信息</h1>
            <dl class="dl-horizontal">
                <dt>账号状态</dt>
                <dd>${hospital.accountStatusName}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>账号创建日期</dt>
                <dd>${hospital.createDate}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>最近登录时间</dt>
                <dd>${hospital.latestLoginDatetime}</dd>
            </dl>
        </div>
    </div>
</div>
</body>
</html>
