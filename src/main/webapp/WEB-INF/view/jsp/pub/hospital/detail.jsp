<%--
  User: Lu Weibiao
  Date: 2015/5/5 21:16
--%>
<%@ page import="com.lwb.guahao.common.Constants" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
        /*医院基本信息板块*/
        #hospitalSection {
            border: 1px solid #e4e4e4;
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 20px;
            margin-top: 20px;
        }

        #hospitalSection .row1, #hospitalSection .row2 {
            margin-bottom: 15px;
        }

        #hospitalSection .name {
            color: #333;
            font-weight: bold;
            text-decoration: underline;
        }

        #hospitalSection .brief {

        }

        /*医院科室板块*/
        #deptSection {

        }

        #deptSection .deptList{
            margin-top: 25px;
        }
        #deptSection .firstDeptCol {
            /*margin: 15px 0px;*/
        }

        #deptSection .firstDeptName {
            font-weight: bold;
            border-left: 3px solid #ffaa00;
            padding-left: 10px;
        }

        #deptSection .secondDeptName {
            border: 1px solid #666;
            background-color: #f2f2f2;
            color: #333;
            padding: 2px 5px;
            margin: 0px 10px 10px 0px;
            display: inline-block;
        }

        #deptSection .secondDeptName:hover {
            border: 1px solid #eee;
            background-color: #ffbb00;
            color: #fff;
        }

        /*医生基本信息列表样式*/
        .doctor {
            border-bottom: 1px solid #e4e4e4;
            border-radius: 5px;
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
    </style>
</head>
<body>
<!-- /pub/hospital/detail.jsp -->
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.UNKNOWN %>"/>
</jsp:include>
<div class="container">
    <!-- 医院基本信息板块 -->
    <div id="hospitalSection">
        <%--<div class="doctorAvatar"><img src="${applicationScope.contextPath}${hospital.avatarPath}"/></div>--%>
        <div>
            <div class="row1">
                <a class="name"
                   href="${applicationScope.contextPath}/hospital/${hospital.id}/detail">${hospital.name}</a>
                &nbsp;&nbsp;${hospital.areaName}&nbsp;&nbsp;
            </div>
            <div class="row2"><b>地址：</b>${hospital.address}</div>
            <div class="brief row3"><b>简介：</b>${hospital.brief}</div>
        </div>
    </div>

    <!-- 医院科室板块 -->
    <div id="deptSection" class="section">
        <div class="title">所有科室</div>
        <div class="deptList">
        <c:forEach items="${departmentList}" var="firstDept">
            <div class="row">
                <div class="col-xs-12 col-sm-1 col-md-1 firstDeptCol" style="margin-bottom: 15px;"><!-- 顶级科室 -->
                    <span class="firstDeptName">${firstDept.deptClassName}</span>
                </div>
                <div class="col-xs-12 col-sm-11 col-md-11" style="margin-bottom: 15px;">
                    <c:forEach items="${firstDept.subDepartmentList}" var="secondDept"><!-- 二级科室 -->
                    <a href="#" data-dept-class-code="${secondDept.deptClassCode}" class="secondDeptName btn">
                            ${secondDept.deptClassName}&nbsp;(${secondDept.doctorNum})
                    </a>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
        </div>
    </div>
</div>
</body>
<script>
    //异步加载指定科室页面
    $(".secondDeptName").click(function () {
        $this = $(this);
        var deptClassCode = $this.data("dept-class-code");
        var url = "${applicationScope.contextPath}/hospital/${hospital.id}/deptment/" + deptClassCode;
        loadHtmlByUrl("#deptSection", url);
    });
</script>
</html>
