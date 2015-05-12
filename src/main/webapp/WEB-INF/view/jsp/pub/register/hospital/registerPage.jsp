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
    <style>
        #jsCancelAreasPanel {
            font-size: 28px;
            padding: 5px 8px;
        }

        #jsOkAreasPanel {
            font-size: 20px;
            padding: 5px 8px;
            width: auto;
        }

        #areasPanel {
            padding: 15px;
        }

        #areasPanel .btn {
            margin-top: 15px;
        }
    </style>
</head>
<body>
<!-- myHospital/hospitalRegisterPage.jsp -->
<jsp:include page="/inc/headerBar?accountType=1"/>
<div class="container">
    <div class="container">
        <div id="areasPanel" class="popup-sidebar">
            <div style="position:relative;">
                <button id="jsCancelAreasPanel" type="button" class="close"><span>&times;</span></button>
                <button id="jsOkAreasPanel" type="button" class="close glyphicon glyphicon-ok"></button>
            </div>
            <h3>选择城市:<span id="selectedProvince"></span><span id="selectedCity"></span></h3>
            <ul class="nav nav-tabs" style="margin-bottom: 1em;">
                <li id="provinceNavTag" role="presentation" class="active"><a href="#">省份</a></li>
                <li id="cityNavTag" role="presentation" style="display: none;"><a href="#">市</a></li>
            </ul>
            <div id="provincesPanel">
                <c:forEach var="province" items="${areaList}">
                    <button type="button" class="btn btn-default" data-id="${province.code}">${province.name}</button>
                </c:forEach>
            </div>
            <div id="citiesPanel" style="display: none;">
                <c:forEach var="province" items="${areaList}">
                    <div id="cities${province.code}" style="display: none;"><!-- 某个省份对应的所有市 Wrapper -->
                        <c:forEach var="city" items="${province.subAreaList}">
                            <button type="button" class="btn btn-default" data-id="${city.code}">${city.name}</button>
                            <!-- 某个省份对应的所有市 -->
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </div>
        <form id="hospitalRegisterForm" class="form-signin"
              action="${applicationScope.contextPath}/register/hospital.json"
              method="post">
            <input name="areaCode" type="hidden" id="inputAreaCode" value="" required=""/>

            <h2 class="form-signin-heading">医院注册</h2>

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
                <label for="inputName">医院名称</label>
                <input name="name" type="text" id="inputName" class="form-control" placeholder="必填" required=""/>
            </div>
            <div class="form-group">
                <label for="inputAreaName">所在省市</label>

                <div class="input-group">
                    <input type="text" id="inputAreaName" class="form-control" placeholder="选择所在省市" value="" readonly
                           required/>
          <span class="input-group-btn">
            <button id="jsAreas" class="btn btn-default" type="button">选择</button>
          </span>
                </div>
            </div>
            <div class="form-group">
                <label for="inputAddress">详细地址</label>
                <input name="address" type="text" id="inputAddress" class="form-control" placeholder="必填" required=""/>
            </div>
            <div class="form-group">
                <label for="inputLinkman">联系人</label>
                <input name="linkman" type="text" id="inputLinkman" class="form-control" placeholder="必填" required=""/>
            </div>
            <div class="form-group">
                <label for="inputTelPhone">联系固定电话</label>
                <input name="telPhone" type="tel" id="inputTelPhone" class="form-control" placeholder="必填" required=""/>
            </div>
            <div class="form-group">
                <label for="inputBrief">医院简介</label>
                <textarea name="brief" id="inputBrief" class="form-control" placeholder="必填" required=""></textarea>
            </div>
            <div class="form-group">
                <label for="inputBankCard">银行卡卡号</label>
                <input name="bankCard" type="tel" id="inputBankCard" class="form-control" placeholder=""/>
            </div>
            <input class="btn btn-lg btn-primary btn-block" type="submit" value="提交"/>
        </form>
    </div>
</div>
</body>
<script>
    //弹出选择城市侧边栏
    $("#jsAreas").click(function () {
        var $areasPanel = $("#areasPanel");
        $areasPanel.css("marginLeft", -$areasPanel.width());
        $areasPanel.show().animate({marginLeft: 0}, function () {
            $("#screenMask").show();
        });
    });
    //选择某个省份
    $("#provincesPanel button").click(function () {
        var $province = $(this);
        var provinceCode = $province.data("id");
        var provinceName = $province.text();
        var $provinceNavTag = $("#provinceNavTag");
        var $cityNavTag = $("#cityNavTag");
        var $provincesPanel = $("#provincesPanel");
        var $citiesPanel = $("#citiesPanel");
        //导航标签切换
        $provinceNavTag.removeClass("active");
        $cityNavTag.addClass("active");
        $cityNavTag.show();
        //面板切换
        $provincesPanel.hide();
        var $citiesContainer = $citiesPanel.children("#cities" + provinceCode);
        $citiesContainer.addClass("active");
        $citiesContainer.show();
        $citiesPanel.show()
        //显示选择的地区
        $("#selectedProvince").text(provinceName);
    });
    //选择某个市
    $("#citiesPanel").find("button").click(function () {
        var $this = $(this);
        var cityCode = $this.data("id");
        var cityName = $this.text();
        //设置选择的城市
        $("#selectedCity").text(cityName);
        var $jsOkAreasPanel = $("#jsOkAreasPanel");
        $jsOkAreasPanel.data("areaCode", cityCode);
    });
    //切换回省份导航标签
    $("#provinceNavTag").click(function () {
        $("#selectedProvince").text("");
        $("#selectedCity").text("");
        $("#jsOkAreasPanel").data("areaCode", "");
        //导航标签切换
        $("#cityNavTag").removeClass("active").hide();
        $("#provinceNavTag").addClass("active").show();
        //面板切换
        var $provincesPanel = $("#provincesPanel");
        var $citiesPanel = $("#citiesPanel");
        $citiesPanel.children("div .active").removeClass("active").hide();
        $citiesPanel.hide();
        $provincesPanel.show();
    });
    //取消并关闭选择城市侧边栏
    $("#jsCancelAreasPanel").click(function () {
        $("#areasPanel").hide();
        $("#screenMask").hide();
        $("#provinceNavTag").trigger("click");
    });
    //确认已选择的城市并关闭侧边栏
    $("#jsOkAreasPanel").click(function () {
        var areaCode = $(this).data("areaCode");
        if (areaCode) {
            $("#inputAreaCode").val(areaCode);//设置选择的areaCode到表单
            $("#inputAreaName").val($("#selectedProvince").text() + $("#selectedCity").text());
            $("#jsCancelAreasPanel").trigger("click");
        } else {
            alert("未选择城市");
        }
    });
    //提交表单
    $("#hospitalRegisterForm").submit(function () {
        $(this).ajaxSubmit({
            success: function (data) {
                $ModalBox.open(data.msg);
                if(data.ret === API_RET_SUCCESS){
                    setTimeout(function(){
                        window.location = data.redirectUrl;
                    },1000);
                }
            }
        });
        return false;
    })
</script>
</html>
