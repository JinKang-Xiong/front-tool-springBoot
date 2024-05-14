package com.example.usercenter2.model.requestparam;

public class UpdatePasswordRequest {
    private String id;
    private String oldPassword;
    private String newPassword;
    private String checkPassword;

    @Override
    public String toString() {
        return "UpdatePasswordRequest{" +
                "id=" + id +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

}
