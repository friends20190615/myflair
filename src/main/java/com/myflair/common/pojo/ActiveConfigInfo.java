package com.myflair.common.pojo;

import java.util.Date;

/**
 * Created by user on 2018/5/20.
 */
public class ActiveConfigInfo {

    private Long id;
    private String code;
    private String remark;
    private String title;
    private String hyImg;
    private String pyqImg;
    private String hyContent;
    private String pyqContent;
    private String shareUrl;
    private Long createEmp;
    private Long modifyEmp;
    private Date createTime;
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHyImg() {
        return hyImg;
    }

    public void setHyImg(String hyImg) {
        this.hyImg = hyImg;
    }

    public String getPyqImg() {
        return pyqImg;
    }

    public void setPyqImg(String pyqImg) {
        this.pyqImg = pyqImg;
    }

    public String getHyContent() {
        return hyContent;
    }

    public void setHyContent(String hyContent) {
        this.hyContent = hyContent;
    }

    public String getPyqContent() {
        return pyqContent;
    }

    public void setPyqContent(String pyqContent) {
        this.pyqContent = pyqContent;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
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

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}