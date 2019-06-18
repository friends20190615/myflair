package com.myflair.common.pojo;

import java.util.Date;

/**
 * Created by user on 2018/4/26.
 */
public class IntegralInfo {

    private long id;
    private String mobile;
    private String title;
    private Long integralValue;
    private Date createTime;
    private String remark;
    private Long orderId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getIntegralValue() {
        return integralValue;
    }

    public void setIntegralValue(Long integralValue) {
        this.integralValue = integralValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
