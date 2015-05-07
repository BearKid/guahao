package com.lwb.guahao.webapp.vo;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 16:38
 */

import com.lwb.guahao.common.option.OptionMap;
import com.lwb.guahao.common.option.util.AreaUtil;
import com.lwb.guahao.common.util.lang.DateUtils;
import com.lwb.guahao.common.model.PerUser;
import org.springframework.beans.BeanUtils;

/**
 * 登录态下个人相关组合信息
 */
public class PerUserVo {
    private String id;

    private String password; //账户密码-密文

    private String accountStatusCode; //账户状态 参见：Constants.AccountStatus

    private String createDateTime; //账户创建日期时间

    private String latestLoginDateTime; //最近一次登录的日期时间

    private String idCard; //身份证号码

    private String name; //真实姓名

    private String email;  //联系邮箱

    private String mobilePhone;  //联系手机

    private String avatarPath; //头像物理存储路径

    /* ---------相对于 PerUser 新增的字段 -----------*/
    private String accountStatusName; //账户状态 参见：Constants.AccountStatus

    private String age; //年龄

    private String sex; //性别

    private String areaCode; //常住户口所在县（市、旗、区）6位数字

    private String areaName; //常住户口所在县（市、旗、区）全限定名

    public static PerUserVo parse(PerUser perUser){
        if(perUser == null) {return null;}

        PerUserVo perUserVo = new PerUserVo();
        BeanUtils.copyProperties(perUser, perUserVo);

        perUserVo.setId(perUser.getId() == null ? null : perUser.getId().toString());

        perUserVo.setAccountStatusCode(perUser.getAccountStatusCode() == null ? null : perUser.getAccountStatusCode().toString());
        perUserVo.setAccountStatusName(OptionMap.accountStatusMap.get(perUser.getAccountStatusCode()));//账号状态-名称
        /*
         *从身份证号码提前信息。身份证号码共18位，从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
         */
        String areaCodeStr = perUserVo.getIdCard().substring(0, 6);//地址码-身份证号码第1~6位
        perUserVo.setAreaCode(areaCodeStr);

        int areaCode = Integer.parseInt(areaCodeStr);
        String cityName = AreaUtil.getAreaName(areaCode);
        String provName = AreaUtil.getAreaName(AreaUtil.getProvinceCode(areaCode));
        perUserVo.setAreaName(provName.concat(cityName));//设置地区全限定名

        String birthDateStr = perUserVo.getIdCard().substring(6,14);//出生日期-身份证号码第7~14位
        perUserVo.setAge(DateUtils.getAge(birthDateStr, "yyyyMMdd") + "");

        String sexCodeStr = perUserVo.getIdCard().substring(14,17);//性别-身份证号码第15~17位
        int sexCode = Integer.parseInt(sexCodeStr);
        perUserVo.setSex((sexCode%2 == 0) ? "女":"男");//顺序码的奇数分配给男性，偶数分配给女性。

        //日期时间
        perUserVo.setCreateDateTime(perUser.getCreateDateTime() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(perUser.getCreateDateTime()));
        perUserVo.setLatestLoginDateTime(perUser.getLatestLoginDate() == null ? "未知" : DateUtils.yearMonthDayTimeFormatter.format(perUser.getLatestLoginDate()));

        return perUserVo;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAccountStatusName() {
        return accountStatusName;
    }

    public void setAccountStatusName(String accountStatusName) {
        this.accountStatusName = accountStatusName;
    }

    public String getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(String accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getLatestLoginDateTime() {
        return latestLoginDateTime;
    }

    public void setLatestLoginDateTime(String latestLoginDateTime) {
        this.latestLoginDateTime = latestLoginDateTime;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
