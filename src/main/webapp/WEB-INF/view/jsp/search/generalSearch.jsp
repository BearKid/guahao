<%--
  User: Lu Weibiao
  Date: 2015/4/27 21:17
--%>
<%@ page import="com.lwb.guahao.common.constants.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
        .doctorAvatar{}
        .doctorGoodAtTag{
            border: 1px solid #E4E4E4;
            background-color: #F2F2F2 ;
            color: #666;
        }
    </style>
</head>
<body>
<%--search/genenralSearch.jsp--%>
</body>
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.UNKNOWN%>"/>
</jsp:include>
<div>
    <a href="#">综合</a>
</div>
<div id="optionsPanel">
    地区：
    <c:forEach items="${areaList}" var="area">
        <a href="${applicationScope.contextPath}/search?${queryStringWithOutAreaCode}&areaCode=${area.code}">${area.name}</a>
    </c:forEach>
    科室：
    <c:forEach items="${deptClassList}" var="deptClass">
        <a href="${applicationScope.contextPath}/search?${queryStringWithOutDeptClassCode}&deptClassCode=${deptClass.code}">${deptClass.name}</a>
    </c:forEach>
    关键词：
    <form action="${applicationScope.contextPath}/search?${queryStringWithOutKeyWord}" method="GET">
        <input value="${searchQo.keyWord}" type="text"/>
        <input id="searchSubmit" type="button" value="确定"/>
    </form>
</div>

<c:forEach items="${doctorBySearchPaging.items}" var="doctor">
    <div>
        <div class="doctorAvatar"><img src="${applicationScope.contextPath}${doctor.avatarPath}"/></div>
        <div>
            <div>
            ${doctor.name}&nbsp;&nbsp;${doctor.deptClassName}&nbsp;&nbsp;
            ${doctor.title}&nbsp;&nbsp${doctor.age};&nbsp;&nbsp;${doctor.sex}
                <a href="${applicationScope.contextPath}/hospitalDetail/${doctor.hospitalId}">${doctor.hospitalName}</a>
            </div>
            <div>
                <c:forEach items="${doctor.goodAtTags}" var="goodAt">
                <span class="doctorGoodAtTag">${goodAt}</span>
                </c:forEach>
            </div>
            <div class="doctorBrief">${doctor.brief}</div>
        </div>
    </div>
</c:forEach>
</html>