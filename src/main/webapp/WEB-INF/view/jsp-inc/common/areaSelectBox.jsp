<%--
  User: Lu Weibiao
  Date: 2015/5/4 15:13
   地区选择侧边栏
  请另行在相关的表单中添加以下元素：
  <input id="inputAreaCode"/>
  <input id="inputAreaName"/>
  <button id="jsAreas">
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 地区选择面板 -->
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
            <button type="button" class="btn btn-default item" data-id="${province.code}">${province.name}</button>
        </c:forEach>
    </div>
    <div id="citiesPanel" style="display: none;">
        <c:forEach var="province" items="${areaList}">
            <div id="cities${province.code}" style="display: none;"><!-- 某个省份对应的所有市 Wrapper -->
                <c:forEach var="city" items="${province.subAreaList}">
                    <button type="button" class="btn btn-default item" data-id="${city.code}">${city.name}</button>
                    <!-- 某个省份对应的所有市 -->
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
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
            var $inputAreaCode = $("#inputAreaCode");
            $inputAreaCode.val(areaCode);//设置选择的areaCode到表单
            $("#inputAreaName").val($("#selectedProvince").text() + $("#selectedCity").text());
            $("#jsCancelAreasPanel").trigger("click");
            $inputAreaCode.trigger("change");
        } else {
            alert("未选择城市");
        }
    });
</script>
</html>
