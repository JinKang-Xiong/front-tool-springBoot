package com.example.usercenter2.model.requestparam;

import com.example.usercenter2.model.BigManager;
import com.example.usercenter2.model.User;

/**
 * 大管理员的搜索请求实体类
 */
public class BigManagerSearchRequest {
    private BigManager bigManager;
    private Integer current;
    private Integer pageSize;

    public BigManagerSearchRequest(BigManager bigManager, Integer current, Integer pageSize) {
        this.bigManager = bigManager;
        this.current = current;
        this.pageSize = pageSize;
    }

    public BigManager getBigManager() {
        return bigManager;
    }

    public void setBigManager(BigManager bigManager) {
        this.bigManager = bigManager;
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

    public BigManagerSearchRequest() {
    }

    @Override
    public String toString() {
        return "BigManagerSearchRequest{" +
                "bigManager=" + bigManager +
                ", current=" + current +
                ", pageSize=" + pageSize +
                '}';
    }
}
