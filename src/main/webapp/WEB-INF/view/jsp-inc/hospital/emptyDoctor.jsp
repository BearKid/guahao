<%@ page import="com.lwb.guahao.common.constants.Constants" %>
<%--
  User: Lu Weibiao
  Date: 2015/3/18 22:08
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/inc/common/deptClassSelectBox"/>

<form id="createDoctorForm" class="form-signin"
      action="${applicationScope.contextPath}/hospital/doctor/create.json"
      method="post">

    <div class="form-group">
        <label for="inputAccountName">账号名</label>
        <input name="accountName" type="text" id="inputAccountName" class="form-control" placeholder="必填" required=""
               autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputPassword">密码</label>
        <input name="password" type="text" id="inputPassword" class="form-control" placeholder="必填" required=""
               autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputName">医生名称</label>
        <input name="name" type="text" id="inputName" class="form-control" placeholder="必填" required=""
               autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputDeptClassName">所在科室</label>

        <div class="input-group">
            <input name="deptClassCode" type="hidden" id="inputDeptClassCode" value="" required=""/>
            <input type="text" id="inputDeptClassName" class="form-control" placeholder="选择所在科室" value="" readonly
                   required/>
          <span class="input-group-btn">
            <button id="jsDeptClasses" class="btn btn-default" type="button">选择</button>
          </span>
        </div>
    </div>
    <div class="form-group">
        <label for="inputSexMale">性别</label>
        <label>
            <input name="sex" type="radio" value="<%=Constants.SexType.MALE%>" id="inputSexMale" placeholder="必填" checked required autofocus/>
            男
        </label>
        <label>
            <input name="sex" type="radio" value="<%=Constants.SexType.FEMALE%>" id="inputSexFemale"  placeholder="必填" required autofocus/>
            女
        </label>
    </div>
    <div class="form-group">
        <label for="inputAge">年龄</label>
        <input name="age" type="text" id="inputAge" class="form-control" placeholder=""
               autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputTitle">头衔/级别</label>
        <input name="title" type="text" id="inputTitle" class="form-control" placeholder=""
               autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputPrice">默认挂号费</label>
        <input name="price" type="text" id="inputPrice" class="form-control" placeholder=""
               autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputGoodAtTags">擅长标签</label>
        <input name="goodAtTags" type="text" id="inputGoodAtTags" class="form-control" placeholder="用英文逗号分隔多个词"
               autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputBrief">简介</label>
        <input name="brief" type="text" id="inputBrief" class="form-control" placeholder=""
               autofocus=""/>
    </div>

    <input id="submitCreateDoctorForm" class="btn btn-lg btn-primary btn-block" type="button" value="提交"/>
</form>
<script>
    //创建医生-提交
    $("#submitCreateDoctorForm").click(function () {
        Hospital.getJsonByForm("#createDoctorForm", function (data) {
            $ModalBox.open(data.msg);
            if (data.ret == API_RET_SUCCESS) {
                setTimeout(function () {
                    $ModalBox.close();
                    var navMap = new Map();
                    navMap.put("医院管理", "${applicationScope.contextPath}/hospital/doctors");
                    Hospital.loadHtmlByUrl("${applicationScope.contextPath}/hospital/doctors",navMap);
                }, 2000);
            }
            console.log("create dcotor end");
        });
    });
</script>