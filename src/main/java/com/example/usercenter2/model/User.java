package com.example.usercenter2.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

@TableName("u_user")
public class User {

    /**
     * 这个的作用是？
     * 因为发送给前端的id的数据过长18位，而前端最大接受17位，所以后面的会变成0，导致后面几个数据的Id全部都是相同的。
     * 加这个代码会把数据给序列化，转换为字符串格式的
     */
    @JsonSerialize(using= ToStringSerializer.class)//做序列化处理
    @TableId
    private Long id;

    private String username;
    private String userAccount;
    private String avatarUrl;
    private Integer gender;
    private String userPassword;
    private String email;
    private Integer userStatus;
    private String phone;
    private Date createTime;
    private Date updateTime;
    private Integer statusDelete;
    private Integer userRole;
    private Integer loginTotal;
    private Integer publicContribution;



    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public User() {
    }

    public User(Long id, String username, String userAccount, String avatarUrl, Integer gender, String userPassword, String email, Integer userStatus, String phone, Date createTime, Date updateTime, Integer statusDelete) {
        this.id = id;
        this.username = username;
        this.userAccount = userAccount;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.userPassword = userPassword;
        this.email = email;
        this.userStatus = userStatus;
        this.phone = phone;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.statusDelete = statusDelete;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender=" + gender +
                ", userPassword='" + userPassword + '\'' +
                ", email='" + email + '\'' +
                ", userStatus=" + userStatus +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", statusDelete=" + statusDelete +
                ", userRole=" + userRole +
                '}';
    }
}
