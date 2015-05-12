package com.lwb.guahao.common.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/5/9 15:07
 */
@Entity
public class PerUserBan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer perUserId;//被禁的用户id

    @Column(nullable = false)
    private Date expireDateTime;//过期时间

    @Column(nullable = false)
    private Date createDateTime;//记录创建时间

    @Column(nullable = false)
    private Integer reasonCode;//标识被禁原因的编码 参见Constants.PerUserBanReasonCode

    @Column
    private String remark;//备注

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perUserId", insertable = false, updatable = false)
    private PerUser perUser;

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(Date expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PerUser getPerUser() {
        return perUser;
    }

    public void setPerUser(PerUser perUser) {
        this.perUser = perUser;
    }

    public Integer getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(Integer perUserId) {
        this.perUserId = perUserId;
    }

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
