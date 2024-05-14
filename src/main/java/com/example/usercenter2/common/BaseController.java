package com.example.usercenter2.common;

import com.example.usercenter2.utils.JwtTokenUtil;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础公共控制类
 */

@Controller
public class BaseController {

    /**
     * 根据token获取用户id
     * @param request
     * @return
     */
    public String getUserId(HttpServletRequest request){

        String token=request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        token = token.replace(JwtTokenUtil.TOKEN_PREFIX, "");
        return JwtTokenUtil.getObjectID(token);
    }
}
