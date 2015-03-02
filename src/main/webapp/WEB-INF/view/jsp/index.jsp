<%@ page import="com.lwb.guahao.common.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
        #loginFormsWrapper{
            height: 150px;
        }
        .main{
            padding-top: 15px;
        }
    </style>

</head>
<body>
<%--index.jsp--%>
</body>
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.UNKNOWN%>"/>
</jsp:include>
<div class="container main">
    <div id="loginFormsWrapper">
        <ul id="loginFormsNav" class="nav nav-tabs" style="margin-bottom: 1em;">
            <li class="loginFormNavTag active" data-target-id="perUserLoginForm" role="presentation"><a href="#">个人登录</a></li>
            <li class="loginFormNavTag" data-target-id="hospitalLoginForm" role="presentation"><a href="#">医院登录</a></li>
            <li class="loginFormNavTag" data-target-id="doctorLoginForm"role="presentation"><a href="#">医生登录</a></li>
        </ul>
        <form id="perUserLoginForm" class="form-signin active" action="${applicationScope.contextPath}/login/per.json" method="post">
            <h2 class="form-signin-heading">个人登录</h2>
            <label for="inputPerUserAccountName" class="sr-only">Email address</label>
            <input name="accountName" type="email" id="inputPerUserAccountName" class="form-control" placeholder="邮箱|手机号码" required autofocus>
            <label for="inputPerUserPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="inputPerUserPassword" class="form-control" placeholder="密码" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        </form>

        <form id="hospitalLoginForm" class="form-signin" style="display: none;" action="${applicationScope.contextPath}/login/hospital.json" method="post">
            <h2 class="form-signin-heading">医院登录</h2>
            <label for="inputEmail" class="sr-only">Email address</label>
            <input name="email" type="email" id="inputEmail" class="form-control" placeholder="邮箱" required autofocus>
            <label for="inputPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="inputPassword" class="form-control" placeholder="密码" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        </form>

        <form id="doctorLoginForm" class="form-signin" style="display: none;" action="${applicationScope.contextPath}/login/doctor.json" method="post">
            <h2 class="form-signin-heading">医生登录</h2>
            <label for="inputDoctorAccountName" class="sr-only">Email address</label>
            <input name="accountName" type="email" id="inputDoctorAccountName" class="form-control" placeholder="账号名" required autofocus>
            <label for="inputDoctorPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="inputDoctorPassword" class="form-control" placeholder="密码" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        </form>
        </div>
</div>
<script>
    /* 切换登录面板*/
    $(".loginFormNavTag").click(function(){
        var $curTag = $(this);
        //切换标签
        $("#loginFormsNav .active").removeClass("active");
        $curTag.addClass("active");
        //切换表单
        var targetFormId = $curTag.data("targetId");
        $("#loginFormsWrapper form.active").hide().removeClass("active");
        $("#" + targetFormId).show().addClass('active');
    });
    /* 账号登录 */
    $("#loginFormsWrapper form").submit(function () {
        $(this).ajaxSubmit({
            success: function (data) {
                if (data.warnMsg) alert(data.warnMsg);
                else window.location = data.redirectUrl;
            }
        });
        return false;
    });
</script>
</html>