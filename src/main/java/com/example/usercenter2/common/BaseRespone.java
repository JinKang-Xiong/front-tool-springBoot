package com.example.usercenter2.common;

import java.io.Serializable;

/**
 * 返回结果的封装
 * @param <T>
 * @author jinakng
 */
public class  BaseRespone<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String decription;

    public BaseRespone(int code, T data, String message,String decription) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.decription=decription;
    }

    public BaseRespone(ErrorCode errorCode){
       this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
}
