package com.example.usercenter2.model.requestparam;

import com.example.usercenter2.model.User;

public class UserSearchRequest {
    private User user;
    private Integer current;
    private Integer pageSize;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "UserSearchRequest{" +
                "user=" + user +
                ", current=" + current +
                ", pagesize=" + pageSize +
                '}';
    }
}
