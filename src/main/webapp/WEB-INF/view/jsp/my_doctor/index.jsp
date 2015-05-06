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
<%--<!-- per/index.jsp-->--%>
<body>
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.DOCTOR%>"/>
</jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-4 col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">基本信息</a></li>
                <li><a href="#">排班/预约</a></li>
                <li><a href="#">账号安全</a></li>
            </ul>
        </div>
        <div class="col-xs-8 col-xs-offset-4 col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">基本信息</h1>
            <dl class="dl-horizontal">
                <dt>姓名</dt>
                <dd>${doctor.name}</dd>
            </dl>

            <dl class="dl-horizontal">
                <dt>性别</dt>
                <dd>${doctor.sex}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>年龄</dt>
                <dd>${doctor.age}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>默认挂号费</dt>
                <dd>${doctor.price}元</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>所在科室</dt>
                <dd>${doctor.deptClassName}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>擅长</dt>
                <dd>${doctor.goodAtTags}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>医生简介</dt>
                <dd>${doctor.brief}</dd>
            </dl>

            <h1 class="page-header">账号信息</h1>
            <dl class="dl-horizontal">
                <dt>账号状态</dt>
                <dd>${doctor.accountStatusName}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>最近登录时间</dt>
                <dd>${doctor.latestLoginDateTime}</dd>
            </dl>

            <dl class="dl-horizontal">
                <dt>账号创建时间</dt>
                <dd>${doctor.createDateTime}</dd>
            </dl>

        </div>
    </div>
</div>
</body>
</html>
