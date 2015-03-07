package com.lwb.guahao.webapp.vo;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 16:38
 */

import com.lwb.guahao.common.*;
import com.lwb.guahao.model.PerUser;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 登录态下个人相关组合信息
 */
public class LoginedPerUser {
    private Integer id;

    private String password; //账户密码-密文

    private Integer accountStatusCode; //账户状态 参见：Constants.AccountStatus

    private Date createDate; //账户创建日期时间

    private Date latestLoginDate; //最近一次登录的日期时间

    private String idCard; //身份证号码

    private String name; //真实姓名

    private String email;  //联系邮箱

    private String mobilePhone;  //联系手机

    private String avatarPath; //头像物理存储路径

    /* ---------相对于 PerUser 新增的字段 -----------*/
    private String accountStatusName; //账户状态 参见：Constants.AccountStatus

    private Integer age; //年龄

    private String sex; //性别

    private Integer areaCode; //常住户口所在县（市、旗、区）6位数字

    private String areaName; //常住户口所在县（市、旗、区）全限定名

    public static LoginedPerUser parse(PerUser perUser){
        LoginedPerUser loginedPerUser = new LoginedPerUser();
        BeanUtils.copyProperties(perUser,loginedPerUser);

        loginedPerUser.setAccountStatusName(ConstantsMap.accountStatusMap.get(loginedPerUser.getAccountStatusCode()));//账号状态-名称
        /*
         *从身份证号码提前信息。身份证号码共18位，从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
         */
        String areaCodeStr = loginedPerUser.getIdCard().substring(0,6);//地址码-身份证号码第1~6位
        int areaCode = Integer.parseInt(areaCodeStr);
        loginedPerUser.setAreaCode(areaCode);

        String cityName = AreaUtil.getAreaName(areaCode);
        String provName = AreaUtil.getAreaName(AreaUtil.getProvinceCode(areaCode));
        loginedPerUser.setAreaName(provName.concat(cityName));//设置地区全限定名

        String birthDateStr = loginedPerUser.getIdCard().substring(6,14);//出生日期-身份证号码第7~14位
        loginedPerUser.setAge(DateUtils.getAge(birthDateStr,"yyyyMMdd"));

        String sexCodeStr = loginedPerUser.getIdCard().substring(14,17);//性别-身份证号码第15~17位
        int sexCode = Integer.parseInt(sexCodeStr);
        loginedPerUser.setSex((sexCode%2 == 0) ? "女":"男");//顺序码的奇数分配给男性，偶数分配给女性。

        return loginedPerUser;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
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

    public Integer getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(Integer accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(Date latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
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
