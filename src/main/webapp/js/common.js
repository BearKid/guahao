/**
 * Created by Lu Weibiao on 2015/3/15.
 */
var API_RET_SUCCESS = 1;
var API_RET_FAIL = 0;
//通用模态框
var $ModalBox = new Object();
$ModalBox.open = function(content,title){
    var box = new jBox('Modal',{
        content:content,
        title:title,
        onCloseComplete:function(){
            box.destroy();
        }
    });
    $ModalBox._box = box;
    $ModalBox._box.open();
};
$ModalBox.close = function(){
    $ModalBox._box.close();
}

function showLoadingGif() {
    $("#loadingGif").show();
}
function hideLoadingGif() {
    $("#loadingGif").hide();
}
/**
 * 直接根据url发起异步请求加载html内容到指定元素中
 * @param targetId 加载内容的元素的id
 * @param url 请求url
 */
function loadHtmlByUrl(targetId, url) {
    var $loadingGif = $("#loadingGif");
    $loadingGif.show();
    console.log("loading");
    $.ajax({
            url: url,
            dataType: "html",
            type: "GET",
            success: function (data) {
                if(data.ret && data.ret == API_RET_FAIL && data.msg){
                    $ModalBox.open(data.msg);
                } else{
                    $(targetId).html(data);
                }
                $loadingGif.hide();
                console.log("loading end");
            },
            error: function (xhr, errMsg, exception) {
                console.log(exception);
                $ModalBox.open("服务器请求失败");
            }
        }
    );
}
/**
 * 通过提交表单信息来请求加载html内容
 * @param targetId 加载内容的元素的id
 * @param formId 提交数据的表单
 */
function loadHtmlByForm(targetId,formId){
    var $loadingGif = $("#loadingGif");
    $loadingGif.show();
    console.log("loading");
    $(formId).ajaxSubmit({
        dataType:"html",
        success:function(data){
            $(targetId).html(data);
            $loadingGif.hide();
            console.log("loading end");
        },
        error: function (xhr, errMsg, exception) {
            console.log(exception);
            $ModalBox.open("服务器请求失败");
        }
    });
}
/**
 * 直接根据url发起异步请求,返回json格式数据以供后续处理
 * @param url 请求url
 * @param callBack 请求成功后获取json数据进行后续处理
 */
function ajaxByUrl(url,callBack) {
    var $loadingGif = $("#loadingGif");
    $loadingGif.show();
    console.log("loading");
    $.ajax({
        url: url,
        dataType: "json",
        type: "GET",
        success: function (data) {
            $loadingGif.hide();
            console.log("loading end");
            callBack(data);
        },
        error: function (xhr, errMsg, exception) {
            console.log(exception);
            $ModalBox.open("服务器请求失败");
        }
    });
}
/**
 * 异步提交表单请求,返回json格式数据以供后续处理
 * @param formId 要提交的表单
 * @param callBack 请求成功后获取json数据进行后续处理
 */
function ajaxByForm(formId,callBack){
    var $loadingGif = $("#loadingGif");
    $loadingGif.show();
    console.log("loading");
    $(formId).ajaxSubmit({
        dataType:"json",
        success:function(data){
            $loadingGif.hide();
            console.log("loading end");
            callBack(data);
        },
        error: function (xhr, errMsg, exception) {
            console.log(exception);
            $ModalBox.open("服务器请求失败");
        }
    })
}
/**
 * 医院账号模块
 * @type {Object}
 */
var Hospital = new Object;
/**
 * 医院账号模块 - 根据url加载主区域html
 * @param url
 * @param navMap
 */
Hospital.loadHtmlByUrl = function (url, navMap) {
    Hospital._beforeLoadHtml(navMap);
    loadHtmlByUrl("#main", url);
};
/**
 * 医院账号模块 - 根据表单加载主区域html
 * @param formId
 * @param navMap
 */
Hospital.loadHtmlByForm = function(formId,navMap){
    Hospital._beforeLoadHtml(navMap);
    loadHtmlByForm("#main", formId);
};
/**
 * 医院账号模块 - 根据表单异步请求并回调
 * @param formId
 * @param callBack
 */
Hospital.ajaxByForm = function(formId,callBack){
    ajaxByForm(formId,callBack);
}
/**
 * 医院账号模块 - 加载主区域html前执行的其他处理
 * 目前作用为调整顶部导航栏导航条目
 * @param navMap
 * @private
 */
Hospital._beforeLoadHtml = function(navMap){
    if(!navMap) return;
    var $stepBar = $("#stepBar");
    $stepBar.html("");
    var nameArr = navMap.keySet();
    for (i in nameArr) {
        $stepBar.append('<a href="' + navMap.get(nameArr[i]) + '">' + nameArr[i] + '</a>');
        $stepBar.append(' >> ');
    }
};
