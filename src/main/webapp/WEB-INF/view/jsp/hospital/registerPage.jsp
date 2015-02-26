<%--
  User: Lu Weibiao
  Date: 2015/2/22 22:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="${applicationScope.jspIncPath}/headTagBody.jsp"/>
    <style>
        #jsCancelAreasPanel{
            font-size: 28px;
            padding: 5px 8px;
        }
        #jsOkAreasPanel{
            font-size: 20px;
            padding: 5px 8px;
            width: auto;
        }
    </style>
</head>
<body>
<!-- hospital/registerPage.jsp -->
<div id="screenMask" class="screen-mask"></div>
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
            <button type="button" class="btn btn-default" data-id="4400">广东</button>
            <button type="button" class="btn btn-default" data-id="4500">福建</button>
            <button type="button" class="btn btn-default" data-id="4200">海南</button>
            <button type="button" class="btn btn-default" data-id="4300">北京</button>
    </div>
    <div id="citiesPanel" style="display: none;">
        <div id="cities4400" style="display: none;"><!-- 某个省份对应的所有市 -->
            <button type="button" class="btn btn-default" data-id="4401">东莞</button>
            <button type="button" class="btn btn-default" data-id="4404">广州</button>
            <button type="button" class="btn btn-default" data-id="4403">深圳</button>
            <button type="button" class="btn btn-default" data-id="4402">韶关</button>
        </div>
        <div id="cities4500" style="display: none;">
            <button type="button" class="btn btn-default" data-id="4501">东莞2</button>
            <button type="button" class="btn btn-default" data-id="4504">广州2</button>
            <button type="button" class="btn btn-default" data-id="4503">深圳2</button>
            <button type="button" class="btn btn-default" data-id="4502">韶关2</button>
        </div>
    </div>
</div>
<form id="hospitalRegisterForm" class="form-signin" action="${applicationScope.contextPath}/hospital/register.json" method="post">
    <input name="areaCode" type="hidden" id="inputAreaCode" value="" required=""/>
    <h2 class="form-signin-heading">医院注册</h2>
    <div class="form-group">
        <label for="inputEmail">联系邮箱</label>
        <input name="email" type="email" id="inputEmail" class="form-control" placeholder="必填" required="" autofocus=""/>
    </div>
    <%--<div class="form-group">--%>
        <%--<label for="inputPassword">密码</label>--%>
        <%--<input name="password" type="password" id="inputPassword" class="form-control" placeholder="必填" required=""/>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label for="inputRePwd">确认密码</label>--%>
        <%--<input name="rePwd" type="password" id="inputRePwd" class="form-control" placeholder="重复输入密码" required=""/>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label for="inputName">医院名称</label>--%>
        <%--<input name="name" type="text" id="inputName" class="form-control" placeholder="必填" required=""/>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label for="inputAreaName">所在省市</label>--%>
        <%--<div class="input-group">--%>
            <%--<input type="text" id="inputAreaName" class="form-control" placeholder="选择所在省市" required=""/>--%>
          <%--<span class="input-group-btn">--%>
            <%--<button id="jsAreas" class="btn btn-default" type="button">选择</button>--%>
          <%--</span>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label for="inputAddress">详细地址</label>--%>
        <%--<input name="address" type="text" id="inputAddress" class="form-control" placeholder="必填" required=""/>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label for="inputLinkman">联系人</label>--%>
        <%--<input name="linkman" type="text" id="inputLinkman" class="form-control" placeholder="必填" required=""/>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label for="inputPhone">联系固定电话</label>--%>
        <%--<input name="phone" type="tel" id="inputPhone" class="form-control" placeholder="必填" required=""/>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label for="inputBrief">医院简介</label>--%>
        <%--<textarea name="brief" id="inputBrief" class="form-control" placeholder="必填" required=""></textarea>--%>
    <%--</div>--%>
    <input class="btn btn-lg btn-primary btn-block" type="submit" value="提交"/>
</form>
</body>
<script>
    //弹出选择城市侧边栏
    $("#jsAreas").click(function(){
        var $areasPanel = $("#areasPanel");
        $areasPanel.css("marginLeft",-$areasPanel.width());
        $areasPanel.show().animate({marginLeft:0},function(){
            $("#screenMask").show();
        });
    });
    //选择某个省份
    $("#provincesPanel button").click(function(){
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
    $("#citiesPanel").find("button").click(function() {
        var $this = $(this);
        var cityCode = $this.data("id");
        var cityName = $this.text();
        //设置选择的城市
        $("#selectedCity").text(cityName);
        var $jsOkAreasPanel = $("#jsOkAreasPanel");
        $jsOkAreasPanel.data("areaCode",cityCode);
        $jsOkAreasPanel.data("areaName",cityName);
    });
    //切换回省份导航标签
    $("#provinceNavTag").click(function() {
        $("#selectedCity").text("");
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
    $("#jsCancelAreasPanel").click(function(){
        $("#areasPanel").hide();
        $("#screenMask").hide();
        $("#provinceNavTag").trigger("click");
    });
    //确认已选择的城市并关闭侧边栏
    $("#jsOkAreasPanel").click(function(){
        var $this = $(this);
        $("#inputAreaCode").val($this.data("areaCode"));//设置选择的areaCode到表单
        $("#inputAreaName").val($this.data("areaName"));
        $("#jsCancelAreasPanel").trigger("click");
    });
    //提交表单
    $("#hospitalRegisterForm").submit(function(){
        $(this).ajaxSubmit({
            success:function(data){
                if(data.errMsg){
                    var errMsg;
                    if(data.errMsg.unfinished) errMsg = data.errMsg.unfinished;
                    else if(data.errMsg.phone){ errMsg = data.errMsg.phone}
                    else if(data.errMsg.rePwd) {errMsg = data.errMsg.rePwd}
                    alert(errMsg);
                } else{
//                    window.location = data.redirectUrl;
                    alert(data.redirectUrl);
                }
            }
        });
        return false;
    })
</script>
</html>
