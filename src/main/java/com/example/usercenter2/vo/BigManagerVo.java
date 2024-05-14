package com.example.usercenter2.vo;

import java.util.Date;

public class BigManagerVo {
    private Long id;
    private String bigManagerName;
    private String bigManagerAccount;
    private String avatarUrl;
    private Byte avatarStatus;
    private Byte gender;
    private Integer age;
    private String email;
    private String phone;
    private Byte managerRole;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "BigManagerVo{" +
                "id=" + id +
                ", bigManagerName='" + bigManagerName + '\'' +
                ", bigManagerAccount='" + bigManagerAccount + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", avatarStatus=" + avatarStatus +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", managerRole=" + managerRole +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
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

    public BigManagerVo() {
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

}
