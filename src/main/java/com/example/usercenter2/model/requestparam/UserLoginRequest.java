package com.example.usercenter2.model.requestparam;


import java.io.Serializable;//继承序列化

public class UserLoginRequest implements Serializable {
    private String useraccount;
    private String userpassword;
    private boolean isRemerber;

    public boolean isRemerber() {
        return isRemerber;
    }

    public void setRemerber(boolean remerber) {
        isRemerber = remerber;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "useraccount='" + useraccount + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", isRemerber=" + isRemerber +
                '}';
    }
}
