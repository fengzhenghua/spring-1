package com.hua.entity;

import com.hua.base.BasePo;

import java.io.Serializable;

/**
 * Created by fengzhenghua on
 * 2017-08-19 18:24.
 */
public class User extends BasePo implements Serializable{

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String dept;
    private String phone;
    private String website;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
