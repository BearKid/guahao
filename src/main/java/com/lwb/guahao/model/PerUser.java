package com.lwb.guahao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/13 23:19.
 */

/**
 * 个人账户
 * email 或 mobilePhone至少有一个非空，用于登录
 */
@Entity
public class PerUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String password; //账户密码-密文

    @Column(nullable = false)
    private Integer accountStatusCode; //账户状态 参见：Constants.AccountStatus

    @Column(nullable = false)
    private Date createDate; //账户创建日期时间

    @Column
    private Date latestLoginDate; //最近一次登录的日期时间

    @Column(nullable = false, length = 18)
    private String idCard; //身份证号码

    @Column(nullable = false, length = 10)
    private String name; //真实姓名

    @Column(length = 255)
    private String email;  //联系邮箱

    @Column(length = 11)
    private String mobilePhone;  //联系手机

    @Column(length = 1000)
    private String avatarPath; //头像物理存储路径


    //根据身份证号码可以得出
    //private Integer age; //年龄
    //private String sex; //性别

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Integer getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(Integer accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLatestLoginDate() {
        return latestLoginDate;
    }

    public void setLatestLoginDate(Date latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
