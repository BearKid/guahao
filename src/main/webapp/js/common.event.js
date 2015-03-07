/**
 * Created by Lu Weibiao on 2015/2/14.
 */
$(function(){
    /*toggle 总登录面板*/
    $("#jsShowLoginFormsWrapper").click(function(){
        $("#loginFormsWrapper").slideToggle();
    })
    /* 切换三个登录类型的面板*/
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
                if (data.errMsg){
                    var msg = "";
                    if(data.errMsg.accountName) msg += data.errMsg.accountName + "\n";
                    if(data.errMsg.password) msg += data.errMsg.password + "\n";
                    if(data.errMsg.login) msg += data.errMsg.login + "\n";
                } else window.location = data.redirectUrl;
            }
        });
        return false;
    });
    /*显示、关闭总注册下拉菜单*/
    $("#jsToggleRegisterMenu").click(function(){
        $("#registerMenu").toggle();
    })
});
