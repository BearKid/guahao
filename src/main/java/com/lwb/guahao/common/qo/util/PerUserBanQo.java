package com.lwb.guahao.common.qo.util;

import com.lwb.guahao.common.qo.BaseQo;

import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/5/12 17:04
 */
public class PerUserBanQo extends BaseQo {

    private Integer id;

    private Integer perUserId;//被禁的用户id

    private Date expireDateTimeStart;//>=过期时间

    private Date expireDateTimeEnd;//<=过期时间

    private Date createDateTimeStart;//<=记录创建时间

    private Date createDateTimeEnd;//<=记录创建时间

    private Integer reasonCode;//标识被禁原因的编码 参见Constants.PerUserBanReasonCode

    private String remark;//备注

    public Date getCreateDateTimeEnd() {
        return createDateTimeEnd;
    }

    public void setCreateDateTimeEnd(Date createDateTimeEnd) {
        this.createDateTimeEnd = createDateTimeEnd;
    }

    public Date getCreateDateTimeStart() {
        return createDateTimeStart;
    }

    public void setCreateDateTimeStart(Date createDateTimeStart) {
        this.createDateTimeStart = createDateTimeStart;
    }

    public Date getExpireDateTimeEnd() {
        return expireDateTimeEnd;
    }

    public void setExpireDateTimeEnd(Date expireDateTimeEnd) {
        this.expireDateTimeEnd = expireDateTimeEnd;
    }

    public Date getExpireDateTimeStart() {
        return expireDateTimeStart;
    }

    public void setExpireDateTimeStart(Date expireDateTimeStart) {
        this.expireDateTimeStart = expireDateTimeStart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
