package com.myflair.common.pojo;

import java.util.Date;

/**
 * Created by user on 2018/3/24.
 */
public class PullNewMember {

    private Long id;

    private  String beInviteMobile;

    private String inviteMobile;

    private Long orderId;

    private Date inviteTime;

    private Integer inviteStatus;

    private Date firstOrderTime;

    private Integer returnTicketStatus;

    private Date returnTicketTime;

    private Integer beInviteReturnTicketStatus;

    private Date beInviteReturnTicketTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeInviteMobile() {
        return beInviteMobile;
    }

    public void setBeInviteMobile(String beInviteMobile) {
        this.beInviteMobile = beInviteMobile;
    }

    public String getInviteMobile() {
        return inviteMobile;
    }

    public void setInviteMobile(String inviteMobile) {
        this.inviteMobile = inviteMobile;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    public Date getFirstOrderTime() {
        return firstOrderTime;
    }

    public void setFirstOrderTime(Date firstOrderTime) {
        this.firstOrderTime = firstOrderTime;
    }

    public Integer getReturnTicketStatus() {
        return returnTicketStatus;
    }

    public void setReturnTicketStatus(Integer returnTicketStatus) {
        this.returnTicketStatus = returnTicketStatus;
    }

    public Date getReturnTicketTime() {
        return returnTicketTime;
    }

    public void setReturnTicketTime(Date returnTicketTime) {
        this.returnTicketTime = returnTicketTime;
    }

    public Integer getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Integer inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public Integer getBeInviteReturnTicketStatus() {
        return beInviteReturnTicketStatus;
    }

    public void setBeInviteReturnTicketStatus(Integer beInviteReturnTicketStatus) {
        this.beInviteReturnTicketStatus = beInviteReturnTicketStatus;
    }

    public Date getBeInviteReturnTicketTime() {
        return beInviteReturnTicketTime;
    }

    public void setBeInviteReturnTicketTime(Date beInviteReturnTicketTime) {
        this.beInviteReturnTicketTime = beInviteReturnTicketTime;
    }

}
