<%--
  User: Lu Weibiao
  Date: 2015/3/15 17:32
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <dd>${hospital.createDateTime}</dd>
</dl>
<dl class="dl-horizontal">
    <dt>最近登录时间</dt>
    <dd>${hospital.latestLoginDateTime}</dd>
</dl>
