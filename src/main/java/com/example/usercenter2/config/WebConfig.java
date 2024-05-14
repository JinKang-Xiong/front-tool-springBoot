package com.example.usercenter2.config;

import com.example.usercenter2.utils.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String filePath = "D:/MyCompletionProject/BackEndGitStore/UserCenter2/src/images/";

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/test/istoken","/user/current","/bigmanager/current");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+filePath);
        System.out.println("静态资源获取");
    }
}
