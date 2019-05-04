package com.gwd.entity;

import java.util.Date;

public class OrderInfo {
    private Integer id;
    private Integer fileId;
    private Integer userId;
    private String name;
    private String phone;
    private String takeTime;
    private String college;
    private String shopAddress;
    private Integer propertyId;
    private String myAddress;
    private Integer pageNum;
    private Integer num;
    private boolean hasDelivery;
    private String note;
    private Date createTime;
    private Double price;
    private boolean accomplish;
    private boolean haspay;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public boolean isHaspay() {
        return haspay;
    }

    public void setHaspay(boolean haspay) {
        this.haspay = haspay;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", fileId=" + fileId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", takeTime='" + takeTime + '\'' +
                ", college='" + college + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", propertyId=" + propertyId +
                ", myAddress='" + myAddress + '\'' +
                ", pageNum=" + pageNum +
                ", num=" + num +
                ", hasDelivery=" + hasDelivery +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", accomplish=" + accomplish +
                ", haspay=" + haspay +
                '}';
    }

    public boolean isAccomplish() {
        return accomplish;
    }

    public void setAccomplish(boolean accomplish) {
        this.accomplish = accomplish;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public boolean isHasDelivery() {
        return hasDelivery;
    }

    public void setHasDelivery(boolean hasDelivery) {
        this.hasDelivery = hasDelivery;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
