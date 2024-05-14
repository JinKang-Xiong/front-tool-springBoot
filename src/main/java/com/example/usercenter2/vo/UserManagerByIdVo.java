package com.example.usercenter2.vo;

import java.util.Date;

/**
 * 根据id查询用户时返回的用户数据
 */
public class UserManagerByIdVo {
    private String username;
    private String userAccount;
    private String avatarUrl;
    private Integer gender;
    private String email;
    private Integer userStatus;
    private String phone;
    private Date createTime;
    private Date updateTime;
    private Integer statusDelete;
    private Integer userRole;
    private Integer loginTotal;
    private Integer publicContribution;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
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

    public Integer getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(Integer statusDelete) {
        this.statusDelete = statusDelete;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getLoginTotal() {
        return loginTotal;
    }

    public void setLoginTotal(Integer loginTotal) {
        this.loginTotal = loginTotal;
    }

    public Integer getPublicContribution() {
        return publicContribution;
    }

    public void setPublicContribution(Integer publicContribution) {
        this.publicContribution = publicContribution;
    }

    public UserManagerByIdVo() {
    }

    public UserManagerByIdVo(String username, String userAccount, String avatarUrl, Integer gender, String email, Integer userStatus, String phone, Date createTime, Date updateTime, Integer statusDelete, Integer userRole, Integer loginTotal, Integer publicContribution) {
        this.username = username;
        this.userAccount = userAccount;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.email = email;
        this.userStatus = userStatus;
        this.phone = phone;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.statusDelete = statusDelete;
        this.userRole = userRole;
        this.loginTotal = loginTotal;
        this.publicContribution = publicContribution;
    }

    @Override
    public String toString() {
        return "UserManagerByIdVo{" +
                "username='" + username + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", userStatus=" + userStatus +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", statusDelete=" + statusDelete +
                ", userRole=" + userRole +
                ", loginTotal=" + loginTotal +
                ", publicContribution=" + publicContribution +
                '}';
    }
}
