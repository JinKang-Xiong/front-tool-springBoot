package com.example.usercenter2.controller;


import com.example.usercenter2.common.BaseRespone;
import com.example.usercenter2.common.ErrorCode;
import com.example.usercenter2.common.ResultUtils;
import com.example.usercenter2.model.User;
import com.example.usercenter2.service.UserService;
import com.example.usercenter2.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test")
public class TestToken {

    @Autowired
    UserService userService;

    @RequestMapping("/token")
    public BaseRespone<User> adminlogin(HttpServletRequest request,HttpServletResponse response) {
        String useraccount=request.getParameter("useraccount");
        String password=request.getParameter("password");

        User loginuser = userService.loginuser(useraccount, password, request);
        String id=String.valueOf(loginuser.getId());
        String token= JwtTokenUtil.createToken(id,loginuser.getUsername(),false);
        response.setHeader(JwtTokenUtil.TOKEN_HEADER,JwtTokenUtil.TOKEN_PREFIX+token);
        return ResultUtils.succeess(loginuser);
    }

    @RequestMapping("/istoken")
    public BaseRespone<User> adminout(){
        System.out.println("拦截器没有成功");
        return ResultUtils.error(ErrorCode.NO_AUTH);
    }


    }
