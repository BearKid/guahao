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
        /*#areasPanel{}*/
    </style>
</head>
<body>
hospital/registerPage.jsp
<div id="areasPanel" class="container popup-sidebar">
    <h3>选择城市:<span id="selectedProvince"></span><span id="selectedCity"></span></h3>
    <%--<div class="row">--%>
        <%--<div class="col-xs-6">省份</div>--%>
        <%--<div class="col-xs-6">市</div>--%>
    <%--</div>--%>
    <ul class="nav nav-tabs" style="margin-bottom: 1em;">
        <li id="provinceNavTag" role="presentation" class="active"><a href="#">省份</a></li>
        <li id="cityNavTag" role="presentation" style="display: none;"><a href="#">市</a></li>
    </ul>
    <div id="provincesPanel">
        <%--<div class="row">--%>
            <%--<div class="col-xs-2 col-xs-offset-1">广东</div>--%>
            <%--<div class="col-xs-2 col-xs-offset-1">福建</div>--%>
            <%--<div class="col-xs-2 col-xs-offset-1">海南</div>--%>
        <%--</div>--%>
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
<form class="form-signin">
    <input name="areaCode" type="hidden" id="inputAreaCode" required=""/>
    <h2 class="form-signin-heading">医院注册</h2>
    <div class="form-group">
        <label for="inputEmail">联系邮箱</label>
        <input name="email" type="email" id="inputEmail" class="form-control" placeholder="必填" required="" autofocus=""/>
    </div>
    <div class="form-group">
        <label for="inputPassword">密码</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="必填" required=""/>
    </div>
    <div class="form-group">
        <label for="inputRePwd">确认密码</label>
        <input name="rePwd" type="password" id="inputRePwd" class="form-control" placeholder="重复输入密码" required=""/>
    </div>
    <div class="form-group">
        <label for="inputName">医院名称</label>
        <input name="name" type="text" id="inputName" class="form-control" placeholder="必填" required=""/>
    </div>
    <div class="form-group">
        <label for="inputAreaCode">所在省市</label>
        <div class="input-group">
            <input type="text" id="inputArea" class="form-control" placeholder="选择所在省市" required=""/>
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
        <label for="inputPhone">联系固定电话</label>
        <input name="phone" type="tel" id="inputPhone" class="form-control" placeholder="必填" required=""/>
    </div>
    <div class="form-group">
        <label for="inputBrief">医院简介</label>
        <textarea name="brief" id="inputBrief" class="form-control" placeholder="必填" required=""></textarea>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
</form>
</body>
<script>
    //弹出选择城市侧边栏
    $("#jsAreas").click(function(){
        $("#areasPanel").show();
    });
    //选择某个省份
    $("#provincesPanel button").click(function(){
        var $provice = $(this);
        var provinceCode = $provice.data("id");
        var provinceName = $provice.text();
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
        $("#inputAreaCode").val(cityCode);
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
</script>
</html>
