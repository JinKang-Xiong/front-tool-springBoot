package com.example.usercenter2.common;

/**
 * 返回结果的工具类
 *
 * @author jinkang
 */
public class ResultUtils {


    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseRespone<T> succeess(T data){
        return new BaseRespone<>(0,data,"OK",null);
    }

    public static <T> BaseRespone<T> succeess(T data ,String decription){
        return new BaseRespone<>(0,data,"OK",decription);
    }

    /**
     * 失败
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> BaseRespone<T> error(ErrorCode errorCode){
        return new BaseRespone<>(errorCode);
    }


    public static <T> BaseRespone<T> error(ErrorCode errorCode,String message,String descript){
        return new BaseRespone(errorCode.getCode(),null,message,descript);
    }

    public static <T> BaseRespone<T> error(ErrorCode errorCode,String descript){
        return new BaseRespone(errorCode.getCode(),null,errorCode.getMessage(),descript);
    }

    public static <T> BaseRespone<T> error(int code,String message,String descript){
        return new BaseRespone(code,null,message,descript);
    }


}
