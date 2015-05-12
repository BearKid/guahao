<%@ page import="com.lwb.guahao.common.Constants" %>
<%--
  User: Lu Weibiao
  Date: 2015/2/22 22:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
</head>
<body>
<!-- per/hospitalRegisterPage.jsp -->
<jsp:include page="/inc/headerBar">
    <jsp:param name="accountType" value="<%=Constants.AccountType.PER_USER %>"/>
</jsp:include>
<div class="container">
    <div class="container">
            <form id="perRegisterForm" class="form-signin"
              action="${applicationScope.contextPath}/register/per.json"
              method="post">
            <input name="areaCode" type="hidden" id="inputAreaCode" value="" required=""/>

            <h2 class="form-signin-heading">个人注册</h2>

            <div class="form-group">
                <label for="inputEmail">联系邮箱</label>
                <input name="email" type="email" id="inputEmail" class="form-control" placeholder="必填" required=""
                       autofocus=""/>
            </div>
            <div class="form-group">
                <label for="inputPassword">密码</label>
                <input name="password" type="password" id="inputPassword" class="form-control" placeholder="必填"
                       required=""/>
            </div>
            <div class="form-group">
                <label for="inputRePwd">确认密码</label>
                <input name="rePwd" type="password" id="inputRePwd" class="form-control" placeholder="重复输入密码"
                       required=""/>
            </div>
            <div class="form-group">
                <label for="inputName">真实姓名</label>
                <input name="name" type="text" id="inputName" class="form-control" placeholder="必填" required=""/>
            </div>
            <div class="form-group">
                <label for="inputMobilePhone">手机号码</label>
                <input name="mobilePhone" type="mobile" id="inputMobilePhone" class="form-control" placeholder="绑定手机" />
            </div>
            <div class="form-group">
                <label for="inputIdCard">身份证号码</label>
                <input name="idCard" type="text" id="inputIdCard" class="form-control" placeholder="必填" required=""/>
            </div>
                <div class="form-group">
                    <label for="inputBankCard">银行卡卡号</label>
                    <input name="bankCard" type="text" id="inputBankCard" class="form-control" placeholder="">
                </div>
            <input id="perRegisterFormSubmit" class="btn btn-lg btn-primary btn-block" type="button" value="提交"/>
        </form>
    </div>
</div>
</body>
<script>
    //提交注册表单
    $("#perRegisterFormSubmit").click(function () {
        MyPerUser.getJsonByForm("#perRegisterForm",function(data){
            $ModalBox.open(data.msg);
            if(data.ret === API_RET_SUCCESS){
                setTimeout(function(){
                    window.location = data.redirectUrl;
                },1000);
            }
        });
    });

</script>
</html>
