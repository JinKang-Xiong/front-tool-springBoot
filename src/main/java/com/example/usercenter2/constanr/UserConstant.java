package com.example.usercenter2.constanr;

public interface UserConstant {

    /**
     * 用户态
     */
    String USER_LOGIN_STATE="handleuser";


    //------权限-----

    /**
     * 定义的是默认用户
     */
    int DEFAULT_ROLE=2;

    /**
     * 定义的是管理员用户
     */
    int ADMIN_ROLE=1;

    /**
     * 定义大大管理员
     */
    int BIG_ADMIN_ROLE=0;

    /**
     * 男生
     */
    Byte MAN=1;

    /**
     * 女生
     */
    Byte WOMAN=0;

    /**
     * 存在 || 可用
     */
    Byte EXIST=0;

    /**
     * 消失 || 不能用
     */
    Byte VANISH=1;

    /**
     * 邮箱发送失败
     */
    String EMAILERR="发送失败，邮箱不存在";

    /**
     * 邮箱发送成功
     */
    String EMAILSUCCESS="发送成功";
}
