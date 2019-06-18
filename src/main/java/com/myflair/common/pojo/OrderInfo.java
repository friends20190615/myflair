package com.myflair.common.pojo;

/**
 * Created by user on 2018/3/24.
 */
public class OrderInfo {

    private Integer id;

    private Long orderId;

    private Long fansiId;

    private Integer stats;

    private String mobile;

    private String createTime;

    private String orderNo;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getFansiId() {
        return fansiId;
    }

    public void setFansiId(Long fansiId) {
        this.fansiId = fansiId;
    }

    public Integer getStats() {
        return stats;
    }

    public void setStats(Integer stats) {
        this.stats = stats;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
