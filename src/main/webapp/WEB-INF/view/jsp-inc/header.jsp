<%--
  User: Lu Weibiao
  Date: 2015/3/2 21:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="screenMask" class="screen-mask"></div>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${applicationScope.contextPath}/">${applicationScope.seoTitle}</a>
            <c:if test="${not empty accountInfo}">
            <a class="navbar-brand" href="${applicationScope.contextPath}${accountInfo.accountContextPath}/index">[${accountInfo.accountTypeName}]</a>
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
                <c:if test="${empty accountInfo}">
                    <li class="btn-group">
                        <a id="jsToggleRegisterMenu" href="#">注册</a>
                            <ul id="registerMenu" class="dropdown-menu" role="menu">
                                <li><a href="${applicationScope.contextPath}/register/per">个人注册</a></li>
                                <li class="divider"></li>
                                <li><a href="${applicationScope.contextPath}/register/hospital">医院注册</a></li>
                            </ul>
                    </li>
                    <li><a id="jsShowLoginFormsWrapper" href="#">登录</a></li>
                </c:if>
            </ul>
            <form class="navbar-form navbar-right" action="${applicationScope.contextPath}/search" method="GET">
                <input name="keyWord" type="text" class="form-control" placeholder="搜索...">
                <button type="submit" style="display: none">提交</button>
            </form>
        </div>
    </div>
</nav>
<div class="container">
    <div id="loginFormsWrapper" style="display:none;">
        <ul id="loginFormsNav" class="nav nav-tabs" style="margin-bottom: 1em;">
            <li class="loginFormNavTag active" data-target-id="perUserLoginForm" role="presentation"><a href="#">个人登录</a></li>
            <li class="loginFormNavTag" data-target-id="hospitalLoginForm" role="presentation"><a href="#">医院登录</a></li>
            <li class="loginFormNavTag" data-target-id="doctorLoginForm"role="presentation"><a href="#">医生登录</a></li>
        </ul>
        <form id="perUserLoginForm" class="form-signin active loginForm" action="${applicationScope.contextPath}/login/per.json" method="post">
            <h2 class="form-signin-heading">个人登录</h2>
            <label for="inputPerUserAccountName" class="sr-only">Email address</label>
            <input name="accountName" type="text" id="inputPerUserAccountName" class="form-control" placeholder="邮箱|手机号码" required autofocus>
            <label for="inputPerUserPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="inputPerUserPassword" class="form-control" placeholder="密码" required>
            <button class="btn btn-lg btn-primary btn-block loginBtn" type="button">登录</button>
        </form>

        <form id="hospitalLoginForm" class="form-signin loginForm" style="display: none;" action="${applicationScope.contextPath}/login/hospital.json" method="post">
            <h2 class="form-signin-heading">医院登录</h2>
            <label for="inputEmail" class="sr-only">Email address</label>
            <input name="accountName" type="email" id="inputEmail" class="form-control" placeholder="邮箱" required autofocus>
            <label for="inputPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required>
            <button class="btn btn-lg btn-primary btn-block loginBtn" type="button">登录</button>
        </form>

        <form id="doctorLoginForm" class="form-signin loginForm" style="display: none;" action="${applicationScope.contextPath}/login/doctor.json" method="post">
            <h2 class="form-signin-heading">医生登录</h2>
            <label for="inputDoctorAccountName" class="sr-only">Email address</label>
            <input name="accountName" type="text" id="inputDoctorAccountName" class="form-control" placeholder="账号名" required autofocus>
            <label for="inputDoctorPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="inputDoctorPassword" class="form-control" placeholder="密码" required>
            <button class="btn btn-lg btn-primary btn-block loginBtn" type="button">登录</button>
        </form>
    </div>
</div>
<script>
    $(".loginForm .loginBtn").click(function(){
        var $this = $(this);
        var formId = $this.parents(".loginForm").attr("id");
        getJsonByForm("#"+ formId,function(data) {
            if (data.ret == API_RET_SUCCESS) {
                window.location = data.redirectUrl;
            } else{
                $ModalBox.open(data.msg);
            }
        });
    });
</script>