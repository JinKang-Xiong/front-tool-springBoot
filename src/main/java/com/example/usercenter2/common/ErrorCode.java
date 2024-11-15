package com.example.usercenter2.common;


/**
 *自定义错误编码格式
 */
public enum ErrorCode {

    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部错误",""),
    TOKEN_EXPIRED(30100,"token过期啦","请重新登录"),
    INVALID_REQUEST(30110,"token无效","错误token");
    /**
     * 状态码信息
     */
    private final int code;
    /**
     * 错误信息
     */
    private final String message;
    /**
     * 错误详细信息
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
