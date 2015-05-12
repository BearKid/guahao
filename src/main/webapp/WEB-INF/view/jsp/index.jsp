<%@ page import="com.lwb.guahao.common.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
        #main{
            max-width: 1000px;
        }
        #searchForm{
            margin:50px 0px;
        }
        #searchSubmitBtn{
            height: 45px;
            width: 100px;
            border-radius: 5px;
            background-color: #66CCFF;
            border: 1px solid #E4e4e4;
            color: #fff;
            font-size: 16px;
            text-align: center;
        }
        #searchSubmitBtn:hover {
            background-color: #66BBFF;
        }
        #searchInputBar{
            /*max-width: 530px;*/
            height: 45px;
            /*width: 100%;*/
            border: 1px solid #ccc;
            border-radius: 5px;
            text-indent: 1em;
            /*margin-left: 100px;*/
        }

        .hotSearch{
            margin-top:20px;
        }
        .hotSearch .classTitle{
            padding: 5px 8px;
            background-color: #FFCC00;
            font-size: 16px;
            color: #fff;
        }
        .hotSearch .items{
            padding-top: 30px;
            padding-left: 5px;
        }
        .hotSearch .item{
            margin-right: 10px;
            text-align: left;
            color: #333;
        }
    </style>

</head>
<body>
<%--index.jsp--%>
</body>
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.UNKNOWN%>"/>
</jsp:include>
<div id="main" class="container-fluid">
    <form id="searchForm" class="form-horizontal" action="/search" method="GET">
        <div class="form-group">
            <label class="sr-only" for="searchInputBar">搜索栏</label>
        <input id="searchInputBar" name="keyWord" type="text" placeholder="输入医院名、科室名、疾病名等" class="col-xs-8 col-sm-9 col-md-10"/>
        <div class="col-xs-2 col-sm-1 col-md-1">
            <button id="searchSubmitBtn" type="submit">搜索</button>
        </div>
            </div>

    </form>
    <%--TODO --%>
    <div class="hotSearch row">
        <div><span class="classTitle">热门医院</span></div>
        <div class="items">
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">广东省中医院</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">中山大学附属第三医院</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">广州医学院第三附属医院</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">广东药学院新天生殖医院</a>
        </div>
    </div>
    <div class="hotSearch row">
        <div><span class="classTitle">热门医生</span></div>
        <div class="items">
            <a class="item" href="${applicationScope.contextPath}/doctor/xxx/detail">骆东山</a>
            <a class="item" href="${applicationScope.contextPath}/doctor/xxx/detail">中山</a>
            <a class="item" href="${applicationScope.contextPath}/doctor/xxx/detail">学院第</a>
            <a class="item" href="${applicationScope.contextPath}/doctor/xxx/detail">广院殖</a>
            <a class="item" href="${applicationScope.contextPath}/doctor/xxx/detail">骆东山</a>
            <a class="item" href="${applicationScope.contextPath}/doctor/xxx/detail">中山</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">学院第</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">广院殖</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">骆东山</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">中山</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">学院第</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">广院殖</a>
        </div>
    </div>
    <div class="hotSearch row">
        <div><span class="classTitle">热门疾病</span></div>
        <div class="items">
            <a class="item" href="${applicationScope.contextPath}/search?keyWord=xxx">糖尿病胃炎</a>
            <a class="item" href="${applicationScope.contextPath}/search?keyWord=xxx">高血压</a>
            <a class="item" href="${applicationScope.contextPath}/search?keyWord=xxx">甲亢</a>
            <a class="item" href="${applicationScope.contextPath}/search?keyWord=xxx">冠心病</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">肝硬化</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">癫痫</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">月经不调</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">失眠</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">不孕不育</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">多囊卵巢</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">子宫肌瘤</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">卵巢囊肿</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">宫颈糜烂</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">内分泌失调</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">宫颈癌</a>
            <a class="item" href="${applicationScope.contextPath}/hospital/xxx/detail">失眠</a>
        </div>
    </div>
</div>
</html>