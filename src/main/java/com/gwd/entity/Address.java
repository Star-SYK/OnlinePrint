package com.gwd.entity;

import java.util.Date;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public class Address {

    private Integer id;
    private Integer userId;
    private String address;
    private boolean hasDefault;
    private Date createTime;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId=" + userId +
                ", address='" + address + '\'' +
                ", hasDefault=" + hasDefault +
                ", createTime=" + createTime +
                '}';
    }

    public Address() {
    }

    public Address(Integer userId, String address, boolean hasDefault, Date createTime) {
        this.userId = userId;
        this.address = address;
        this.hasDefault = hasDefault;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isHasDefault() {
        return hasDefault;
    }

    public void setHasDefault(boolean hasDefault) {
        this.hasDefault = hasDefault;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
