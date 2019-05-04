package com.gwd.entity;

import java.util.Date;

public class File {
    private Integer id;
    private Integer  userId;
    private String  size;
    private String  md5;
    private String  name;
    private Integer page;
    private Date createTime;

    public File() {
    }

    public File(Integer userId, String size, String md5, String name,Integer page,Date createTime) {
        this.userId = userId;
        this.size = size;
        this.md5 = md5;
        this.name = name;
        this.page = page;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", userId=" + userId +
                ", size='" + size + '\'' +
                ", md5='" + md5 + '\'' +
                ", name='" + name + '\'' +
                ", page=" + page +
                ", createTime=" + createTime +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
