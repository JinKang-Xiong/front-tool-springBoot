package com.example.usercenter2.model.requestparam;

public class UpdateUserPassword {
    private String id;
    private String oldPassword;
    private String newPassword;
    private String checkNewPassword;

    public UpdateUserPassword() {
    }

    public UpdateUserPassword(String id, String oldPassword, String newPassword, String checkNewPassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.checkNewPassword = checkNewPassword;
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

    public String getCheckNewPassword() {
        return checkNewPassword;
    }

    public void setCheckNewPassword(String checkNewPassword) {
        this.checkNewPassword = checkNewPassword;
    }

    @Override
    public String toString() {
        return "UpdateUserPassword{" +
                "id='" + id + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", checkNewPassword='" + checkNewPassword + '\'' +
                '}';
    }
}
