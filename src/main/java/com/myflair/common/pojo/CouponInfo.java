package com.myflair.common.pojo;


import java.util.Date;

/**
 * Created by user on 2018/4/6.
 */
public class CouponInfo {

    private Long id;
    private Long couponId;
    private String mobile;
    private String title;
    private String couponCondition;
    private Float reduceValue;
    private Date useStartTime;
    private Date useEndTime;
    private Integer status;
    private Long mobileFansId;
    private Long createEmp = 1L;
    private Date createTime;
    private Date modifyTime;
    private Long modifyEmp = 1L;
    private String remark;
    private Long useOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(String couponCondition) {
        this.couponCondition = couponCondition;
    }

    public Float getReduceValue() {
        return reduceValue;
    }

    public void setReduceValue(Float reduceValue) {
        this.reduceValue = reduceValue;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMobileFansId() {
        return mobileFansId;
    }

    public void setMobileFansId(Long mobileFansId) {
        this.mobileFansId = mobileFansId;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getUseOrderId() {
        return useOrderId;
    }

    public void setUseOrderId(Long useOrderId) {
        this.useOrderId = useOrderId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
