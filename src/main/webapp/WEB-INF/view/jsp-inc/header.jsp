<%--
  User: Lu Weibiao
  Date: 2015/3/2 21:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${applicationScope.contextPath}">${applicationScope.seoTitle}</a>
            <c:if test="${not empty accountInfo}">
            <a class="navbar-brand" href="${accountInfo.accountContextPath}/index">[${accountInfo.accountTypeName}]</a>
            </c:if>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty accountInfo}">
                <li><a href="${applicationScope.contextPath}${accountInfo.accountContextPath}/setting">帐号设置</a></li>
                </c:if>
                <li><a href="${applicationScope.contextPath}/help">帮助</a></li>
                <c:if test="${not empty accountInfo}">
                <li><a href="${applicationScope.contextPath}${accountInfo.accountContextPath}/logout">退出</a></li>
                </c:if>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>