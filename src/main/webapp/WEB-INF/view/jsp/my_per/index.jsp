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
    <jsp:param name="accountType" value="<%=Constants.AccountType.PER_USER%>"/>
</jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2 col-sm-2 col-md-2 sidebar" id="">
            <ul class="nav nav-sidebar" id="nav-sidebar">
                <li class="active item"><a href="${applicationScope.contextPath}/myPer/index">基本信息</a></li>
                <li class="item"><a id="jsShowReservations" href="#">预约管理</a></li>
                <li class="item"><a href="#">账号安全</a></li>
            </ul>
        </div>
        <div id="main" class="col-xs-10 col-xs-offset-2 col-sm-10 col-sm-offset-2 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">基本信息</h1>
            <dl class="dl-horizontal">
                <dt>姓名</dt>
                <dd>${perUser.name}</dd>
            </dl>

            <dl class="dl-horizontal">
                <dt>性别</dt>
                <dd>${perUser.sex}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>年龄</dt>
                <dd>${perUser.age}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>所在地区</dt>
                <dd>${perUser.areaName}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>手机号</dt>
                <dd>${perUser.mobilePhone}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>身份证号码</dt>
                <dd>${perUser.idCard}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>绑定邮箱</dt>
                <dd>${perUser.email}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>银行卡号</dt>
                <dd>${perUser.bankCard}</dd>
            </dl>
            <h1 class="page-header">账号信息</h1>
            <dl class="dl-horizontal">
                <dt>账号状态</dt>
                <dd>${perUser.accountStatusName}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>最近登录时间</dt>
                <dd>${perUser.latestLoginDateTime}</dd>
            </dl>

            <dl class="dl-horizontal">
                <dt>账号创建时间</dt>
                <dd>${perUser.createDateTime}</dd>
            </dl>
        </div>
    </div>
</div>
</body>
<script>
    $("#jsShowReservations").click(function () {
       var url = "${applicationScope.contextPath}/myPer/reservations";
        MyPerUser.loadHtmlByUrl(url);
    });
    $("#nav-sidebar .item").click(function(){
        $this = $(this);
        $this.addClass("active");
        $this.siblings().removeClass("active");
    });
</script>
</html>
