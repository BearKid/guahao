<%--
  User: Lu Weibiao
  Date: 2015/5/5 23:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="title">${department.deptClassName}</div>
<!-- 医生列表 -->
<div id="docotorBySearchPaging">
    <c:forEach items="${doctorList}" var="doctor">
        <div class="doctor">
            <%--<div class="avatar"><img src="${applicationScope.contextPath}${doctor.avatarPath}"/></div>--%>
            <div>
                <div class="row1">
                    <a class="name" href="${applicationScope.contextPath}/doctor/${doctor.id}/detail" target="_blank">${doctor.name}</a>&nbsp;&nbsp;${doctor.deptClassName}&nbsp;&nbsp;
                        ${doctor.title}&nbsp;&nbsp;${doctor.age}岁&nbsp;&nbsp;${doctor.sex}&nbsp;&nbsp;
                    <a class="hospitalName" href="${applicationScope.contextPath}/hospital/${doctor.hospitalId}/detail">${doctor.hospital.name}</a>
                </div>
                <div class="row2">
                    <c:forEach items="${doctor.goodAtTags}" var="goodAt">
                        <span class="goodAtTag">${goodAt}</span>
                    </c:forEach>
                </div>
                <div class="doctorBrief row3"><b>简介：</b>${doctor.brief}</div>
            </div>
        </div>
    </c:forEach>
</div>
