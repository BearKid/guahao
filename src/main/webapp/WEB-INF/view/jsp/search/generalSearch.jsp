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
        #main{
            max-width: 1000px;
        }

        #searchNav{
            border-bottom: 1px solid #E4E4E4;
            padding: 5px;
        }
        #searchNav .tab{
            font-size: 20px;
            color: #666666;
        }
        #searchNav .tab.active{
            background-color: #66CCFF;
            color: #fff;
        }
        #searchFilterPanel{
            margin: 20px 0px;
            border-bottom: 1px solid #E4E4E4;
        }
        #searchFilterPanel .filterType{
            margin-bottom: 10px;
        }
        #searchFilterPanel .filterType .title{
            font-weight: bold;
        }
        #searchFilterPanel .filterType .option{
            color:#333;
            margin-right: 10px;
        }
        #docotorBySearchPaging .doctor,#hospitalBySearchPaging .hospital{
            border-bottom: 1px solid #e4e4e4;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 20px;
            margin-top: 20px;
        }
        .doctor .row1, .doctor .row2{
            margin-bottom: 10px;
        }
        .doctor .avatar {
        }
        .doctor .name,.hospital .name{
            color: #333;
            font-weight: bold;
            text-decoration: underline;
        }
        .doctor .hospitalName{
            background-color: #FFCC00;
            color: #ffffff;
            padding: 2px 5px;
        }
        .doctor .goodAtTag {
            background-color: #FF9966;
            border-radius: 5px;
            color: #fff;
            padding: 2px 5px;
            margin-right: 5px;
        }

        #hospitalBySearchPaging .hospital{ }

        .hospital .row1{
            margin-bottom: 10px;
        }

        .hospital .brief{

        }
    </style>
</head>
<body>
<%--search/genenralSearch.jsp--%>
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.UNKNOWN%>"/>
</jsp:include>
<div id="main" class="container-fluid">
    <div id="searchNav">
    <a class="tab" href="#">综合</a>
</div>
<div id="searchFilterPanel">
    <div class="filterType">
        <span class="title">&nbsp;&nbsp;&nbsp;地区：</span>
        <a class="option"  href="${applicationScope.contextPath}/search?${queryStringWithOutAreaCode}">所有</a>
        <a class="option"  href="${applicationScope.contextPath}/search?${queryStringWithOutAreaCode}&areaCode=441900">东莞</a>
        <a class="option"  id="jsAreas" href="#">更多</a>

        <form id="searchFormWithAreaCode" style="display: none;"
              action="#">
            <input type="text" id="inputAreaCode" name="areaCode" value=""/>
            <input type="text" id="inputAreaName" value=""/>
        </form>
    </div>
    <div class="filterType">
        <span class="title">&nbsp;&nbsp;&nbsp;科室：</span>
        <a class="option" href="${applicationScope.contextPath}/search?${queryStringWithOutDeptClassCode}">所有</a>
        <a class="option"  href="${applicationScope.contextPath}/search?${queryStringWithOutDeptClassCode}&deptClassCode=xxx">xxx</a>
        <a class="option"  id="jsDeptClasses" href="#">更多</a>

        <form id="searchFormWithDepClassCode" style="display: none;"
              action="#">
            <input type="text" id="inputDeptClassCode" name="deptClassCode" value=""/>
            <input type="text" id="inputDeptClassName" value=""/>
        </form>
    </div>
    <div class="filterType">
        <span class="title">关键词：</span>
        <form id="searchFormWithKeyWord" class="form-inline" style="display: inline;" action="#" method="GET">
            <div class="form-group">
                <input id="searchInputKeyWord" value="${searchQo.keyWord}" type="text" class="form-control"/>
            </div>
            <button id="searchSubmit" type="button" class="btn btn-default">确定</button>
        </form>
    </div>
</div>
<div id="searchPaging">
<!-- 医生列表 -->
<div id="docotorBySearchPaging">
<c:forEach items="${doctorBySearchPaging.items}" var="doctor">
    <div class="doctor">
        <div class="avatar"><img src="${applicationScope.contextPath}${doctor.avatarPath}"/></div>
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
<!-- 医院列表 -->
<div id="hospitalBySearchPaging">
    <c:forEach items="${hospitalBySearchPaging.items}" var="hospital">
        <div class="hospital">
            <%--<div class="doctorAvatar"><img src="${applicationScope.contextPath}${hospital.avatarPath}"/></div>--%>
            <div>
                <div class="row1">
                    <a class="name" href="${applicationScope.contextPath}/hospital/${hospital.id}/detail">${hospital.name}</a>
                    &nbsp;&nbsp;${hospital.areaName}&nbsp;&nbsp;地址：${hospital.address}
                </div>
                <div class="brief row2"><b>简介：</b>${hospital.brief}</div>
            </div>
        </div>
    </c:forEach>
</div>
<div id="generalSearchPager"></div>
</div>
</div>

<jsp:include page="/inc/common/areaSelectBox"/>
<jsp:include page="/inc/common/deptClassSelectBox"/>
</body>
<script src="${applicationScope.contextPath}/js/jquery/pager.js"></script>
<script>
    //地区选项栏-发起请求
    $("#inputAreaCode").change(function () {
        var url = "${applicationScope.contextPath}/search?${queryStringWithOutAreaCode}";
        var $form = $("#searchFormWithAreaCode");
        var areaCode = $form.find("#inputAreaCode").val();
        url = url + "&areaCode=" + areaCode;
        window.location = url;
    });
    //科室选项栏-发起请求
    $("#inputDeptClassCode").change(function () {
        var url = "${applicationScope.contextPath}/search?${queryStringWithOutDeptClassCode}";
        var $form = $("#searchFormWithDepClassCode");
        var deptClassCode = $form.find("#inputDeptClassCode").val();
        url = url + "&deptClassCode=" + deptClassCode;
        window.location = url;
    });

    $("#searchSubmit").click(function(){
       var url = "${applicationScope.contextPath}/search?${queryStringWithOutKeyWord}";
        var keyWord = $("#searchInputKeyWord").val();
        url = url + "&keyWord=" + keyWord;
        window.location = url;
    });
    //查找结果分页
    $("#generalSearchPager").pager({
        pagenumber: ${doctorBySearchPaging.pn},
        pagecount: ${doctorBySearchPaging.totalPages},
        buttonClickCallback: function (pn) {
            var url = "${applicationScope.contextPath}/search?${queryStringWithOutPn}&pn="+pn;
            window.location = url;
        }
    });
</script>
</html>