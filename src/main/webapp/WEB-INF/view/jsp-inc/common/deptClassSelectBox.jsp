<%--
  User: Lu Weibiao
  Date: 2015/3/18 21:59
  科室类目选择侧边栏
  请另行在相关的表单中添加以下元素：
  <input id="inputDeptClassCode"/>
  <input id="inputDeptClassName"/>
  <button id="jsDeptClasses">
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="deptClassesPanel" class="popup-sidebar">
    <div style="position:relative;">
        <button id="jsSelectEmptyOption" type="button" class="close"><span>留空</span></button>
        <button id="jsCancelDeptClassesPanel" type="button" class="close"><span>&times;</span></button>
        <button id="jsOkDeptClassesPanel" type="button" class="close glyphicon glyphicon-ok"></button>
    </div>
    <h3>选择科室:<span id="selectedFirstDeptClass"></span><span id="selectedSecondDeptClass"></span></h3>
    <ul class="nav nav-tabs" style="margin-bottom: 1em;">
        <li id="firstDeptClassNavTag" role="presentation" class="active"><a href="#">一级科室</a></li>
        <li id="secondDeptClassNavTag" role="presentation" style="display: none;"><a href="#">二级科室</a></li>
    </ul>
    <div id="firstDeptClassesPanel">
        <c:forEach var="firstDeptClass" items="${deptClassList}">
            <button type="button" class="btn btn-default"
                    data-id="${firstDeptClass.code}">${firstDeptClass.name}</button>
        </c:forEach>
    </div>
    <div id="secondDeptClassesPanel" style="display: none;">
        <c:forEach var="firstDeptClass" items="${deptClassList}">
            <div id="secondDeptClasses${firstDeptClass.code}" style="display: none;"><!-- 某个一级科室对应的所有二级科室 Wrapper -->
                <c:forEach var="secondDeptClass" items="${firstDeptClass.subDeptClassList}">
                    <button type="button" class="btn btn-default"
                            data-id="${secondDeptClass.code}">${secondDeptClass.name}</button>
                    <!-- 某个一级科室对应的所有二级科室 -->
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
<script>
    //弹出选择科室侧边栏
    $("#jsDeptClasses").click(function () {
        var $deptClassesPanel = $("#deptClassesPanel");
        $deptClassesPanel.css("marginLeft", -$deptClassesPanel.width());
        $deptClassesPanel.show().animate({marginLeft: 0}, function () {
            $("#screenMask").show();
        });
    });
    //选择某个一级科室
    $("#firstDeptClassesPanel button").click(function () {
        var $province = $(this);
        var provinceCode = $province.data("id");
        var provinceName = $province.text();
        var $provinceNavTag = $("#firstDeptClassNavTag");
        var $cityNavTag = $("#secondDeptClassNavTag");
        var $provincesPanel = $("#firstDeptClassesPanel");
        var $citiesPanel = $("#secondDeptClassesPanel");
        //导航标签切换
        $provinceNavTag.removeClass("active");
        $cityNavTag.addClass("active");
        $cityNavTag.show();
        //面板切换
        $provincesPanel.hide();
        var $citiesContainer = $citiesPanel.children("#secondDeptClasses" + provinceCode);
        $citiesContainer.addClass("active");
        $citiesContainer.show();
        $citiesPanel.show()
        //显示选择的地区
        $("#selectedFirstDeptClass").text(provinceName);
    });
    //选择某个二级科室
    $("#secondDeptClassesPanel").find("button").click(function () {
        var $this = $(this);
        var cityCode = $this.data("id");
        var cityName = $this.text();
        //设置选择的城市
        $("#selectedSecondDeptClass").text(cityName);
        var $jsOkAreasPanel = $("#jsOkDeptClassesPanel");
        $jsOkAreasPanel.data("areaCode", cityCode);
    });
    //切换回一级科室导航标签
    $("#firstDeptClassNavTag").click(function () {
        $("#selectedFirstDeptClass").text("");
        $("#selectedSecondDeptClass").text("");
        $("#jsOkDeptClassPanel").data("areaCode", "");
        //导航标签切换
        $("#secondDeptClassNavTag").removeClass("active").hide();
        $("#firstDeptClassNavTag").addClass("active").show();
        //面板切换
        var $provincesPanel = $("#firstDeptClassesPanel");
        var $citiesPanel = $("#secondDeptClassesPanel");
        $citiesPanel.children("div .active").removeClass("active").hide();
        $citiesPanel.hide();
        $provincesPanel.show();
    });
    //取消并关闭选择城市侧边栏
    $("#jsCancelDeptClassesPanel").click(function () {
        $("#deptClassesPanel").hide();
        $("#screenMask").hide();
        $("#firstDeptClassNavTag").trigger("click");
    });
    //确认已选择的城市并关闭侧边栏
    $("#jsOkDeptClassesPanel").click(function () {
        var areaCode = $(this).data("areaCode");
        if (areaCode) {
            $("#inputDeptClassCode").val(areaCode);//设置选择的areaCode到表单
            $("#inputDeptClassName").val($("#selectedFirstDeptClass").text() + $("#selectedSecondDeptClass").text());
            $("#jsCancelDeptClassesPanel").trigger("click");
        } else {
            alert("未选择二级科室");
        }
    });
    //选择空选项并关闭侧边栏
    $("#jsSelectEmptyOption").click(function(){
        $("#inputDeptClassCode").val("");
        $("#inputDeptClassName").val("");
        $("#jsCancelDeptClassesPanel").trigger("click");
    });
</script>
</html>
