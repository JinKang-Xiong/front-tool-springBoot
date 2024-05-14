package com.example.usercenter2.utils;

import com.example.usercenter2.common.ErrorCode;
import com.example.usercenter2.common.ResultUtils;
import com.example.usercenter2.exception.BusinessException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token验证拦截器
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenHeader=request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        System.out.println("tokenHeader="+tokenHeader);
        System.out.println("拦截器进来啦");
        if(tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)){
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        tokenHeader=tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX,"");
        JwtTokenUtil.getTokenBody(tokenHeader);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
