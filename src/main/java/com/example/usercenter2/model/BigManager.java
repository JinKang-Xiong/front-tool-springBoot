package com.example.usercenter2.model;


import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 大管理的实体类
 */
@TableName("u_big_manager")
public class BigManager {
    private Long id;
    private String bigManagerName;
    private String bigManagerAccount;
    private String avatarUrl;
    private Byte avatarStatus;
    private Byte gender;
    private Integer age;
    private String bigManagerPassword;
    private String email;
    private String phone;
    private Date createTime;
    private Date updateTime;
    private Byte managerRole;
    private Byte statusDelete;

    public BigManager() {
    }

    public BigManager(Long id, String bigManagerName, Byte gender, Integer age) {
        this.id = id;
        this.bigManagerName = bigManagerName;
        this.gender = gender;
        this.age = age;
    }

    public BigManager(Long id, String bigManagerName, String bigManagerAccount, String avatarUrl, Byte gender, Integer age, String bigManagerPassword, String email, String phone, Date createTime, Date updateTime, Byte manageRole, Byte statusDelete) {
        this.id = id;
        this.bigManagerName = bigManagerName;
        this.bigManagerAccount = bigManagerAccount;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.age = age;
        this.bigManagerPassword = bigManagerPassword;
        this.email = email;
        this.phone = phone;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.managerRole = manageRole;
        this.statusDelete = statusDelete;
    }

    @Override
    public String toString() {
        return "BigManager{" +
                "id=" + id +
                ", bigManagerName='" + bigManagerName + '\'' +
                ", bigManagerAccount='" + bigManagerAccount + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", avatarStatus=" + avatarStatus +
                ", gender=" + gender +
                ", age=" + age +
                ", bigManagerPassword='" + bigManagerPassword + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", managerRole=" + managerRole +
                ", statusDelete=" + statusDelete +
                '}';
    }

    public Byte getAvatarStatus() {
        return avatarStatus;
    }

    public void setAvatarStatus(Byte avatarStatus) {
        this.avatarStatus = avatarStatus;
    }

    public Byte getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(Byte managerRole) {
        this.managerRole = managerRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBigManagerName() {
        return bigManagerName;
    }

    public void setBigManagerName(String bigManagerName) {
        this.bigManagerName = bigManagerName;
    }

    public String getBigManagerAccount() {
        return bigManagerAccount;
    }

    public void setBigManagerAccount(String bigManagerAccount) {
        this.bigManagerAccount = bigManagerAccount;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBigManagerPassword() {
        return bigManagerPassword;
    }

    public void setBigManagerPassword(String bigManagerPassword) {
        this.bigManagerPassword = bigManagerPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getManageRole() {
        return managerRole;
    }

    public void setManageRole(Byte manageRole) {
        this.managerRole = manageRole;
    }

    public Byte getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(Byte statusDelete) {
        this.statusDelete = statusDelete;
    }

}
