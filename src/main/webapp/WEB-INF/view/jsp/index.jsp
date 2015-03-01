<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
        #loginFormWrapper{
            height: 150px;
        }
    </style>
</head>
<body>
index.jsp
</body>
<div class="container">
    <div id="loginFormWrapper">
        <ul class="nav nav-tabs" style="margin-bottom: 1em;">
            <li id="showPerUserLoginForm" role="presentation" class="active"><a href="#">个人登录</a></li>
            <li id="showHospitalLoginForm" role="presentation" style="display: none;"><a href="#">医院登录</a></li>
            <li id="showDoctorLoginForm" role="presentation" style="display: none;"><a href="#">医生登录</a></li>
        </ul>
        <form id="perUserLoginForm" class="form-signin active" action="${applicationScope.contextPath}/login/per.json" method="post">
            <h2 class="form-signin-heading">个人登录</h2>
            <label for="inputPerUserAccountName" class="sr-only">Email address</label>
            <input name="accountName" type="email" id="inputPerUserAccountName" class="form-control" placeholder="邮箱" required autofocus>
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
            <input name="accountName" type="email" id="inputDoctorAccountName" class="form-control" placeholder="邮箱" required autofocus>
            <label for="inputDoctorPassword" class="sr-only">Password</label>
            <input name="password" type="password" id="inputDoctorPassword" class="form-control" placeholder="密码" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        </form>
        </div>
</div>
<script>
    /* 切换登录面板*/
    $("#showPerUserLoginForm").click(function(){
        $("#loginForm .active").slideUp();
        $("#perUserLoginForm").slideDown();
    });
    $("#showHospitalLoginForm").click(function(){
        $("#loginForm .active").slideUp);
        $("#hospitalLoginForm").slideDown();
    });
    $("#showDoctorLoginForm").click(function(){
        $("#loginForm .active").slideUp);
        $("#doctorLoginForm").slideDown();
    });
    /* 账号登录 */
    $("#perUserLoginForm").submit(function(){
        $(this).ajaxSubmit({
            success:function(data){
                if(data.warnMsg) alert(data.warnMsg);
                else window.location = data.redirectUrl;
            }
        });
        return false;
    });
    $("#hospitalLoginForm").submit(function(){
        $(this).ajaxSubmit({
            success:function(data){
                if(data.warnMsg) alert(data.warnMsg);
                else window.location = data.redirectUrl;
            }
        });
        return false;
    });
    $("#doctorLoginForm").submit(function(){
        $(this).ajaxSubmit({
            success:function(data){
                if(data.warnMsg) alert(data.warnMsg);
                else window.location = data.redirectUrl;
            }
        });
        return false;
    });
</script>
</html>