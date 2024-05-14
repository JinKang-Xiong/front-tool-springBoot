package com.example.usercenter2;

import com.example.usercenter2.mapper.UserMapper;
import com.example.usercenter2.model.User;
import com.example.usercenter2.service.BigManagerService;
import com.example.usercenter2.service.UserService;

import com.example.usercenter2.utils.EmailUtils;
import com.example.usercenter2.utils.SMSUtils;
import com.example.usercenter2.utils.ValidateCodeUtils;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class UserCenter2ApplicationTests {

    @Autowired
   private UserService userService;
    @Resource
    private UserMapper userMapper;

    @Autowired
    private BigManagerService bigManagerService;

    @Test
    void contextLoads() {
    }

    @Test
    void test1(){
        User user=new User(null,"悟空","wuko","https://www.baomidou.com/img/logo.svg",0,"123456","123456@qq.com",1,"123456789",null,null,1);
        boolean save = userService.save(user);
        System.out.println(save);
    }

    @Test
    void test2() {
        String username="王五";
        String userAccount="yundao";
        String userPassword="987654321";
        String checkPassword="9876543210";
        Integer gender=0;
        String email="123456789@qq.com";
        String phone="123456789";
        long l = userService.registByuser( username,userAccount, userPassword, checkPassword,gender,email,phone);
        Assertions.assertEquals(-1,l);
    }

    @Test
    void test3(){
        String strPartter=".*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“’。，、？\\\\\\\\]+.*";
        Matcher matcher = Pattern.compile(strPartter).matcher("123#");
        System.out.println(matcher);
        System.out.println("===============");
        System.out.println(matcher.find());

    }


    @Test
    void test5() {
        String username="王五";
        String userAccount="userxjk";
        String userPassword="123456789";
        String checkPassword="123456789";
        Integer gender=0;
        String email="123456789@qq.com";
        String phone="123456789";
        long l = userService.registByuser( username,userAccount, userPassword, checkPassword,gender,email,phone);
        System.out.println("l="+l);
    }

    @Test
    void test7() {
        String username="小王五";
        String userAccount="userxjk1";
        String userPassword="123456789";
        String checkPassword="123456789";
        Integer gender=0;
        String email="123456789@qq.com";
        String phone="123456789";
        long l = userService.registByuser( username,userAccount, userPassword, checkPassword,gender,email,phone);
        System.out.println("l="+l);
    }


    @Resource
    private YuCongMingClient client;

    @Test
    void test8(){
        // 构造请求
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1654785040361893889L);
        devChatRequest.setMessage("给我推荐5本好书");

// 获取响应
        BaseResponse<DevChatResponse> response = client.doChat(devChatRequest);
        System.out.println(response.getData().getContent());
    }


    @Test
    void  test9(){
        String phone="17379187962";
        String s = ValidateCodeUtils.generateValidateCode(4).toString();
        System.out.println(s);
//        SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,s);
    }


    @Test
    void test10(){
        String email="122374497@qq.com";
        String s = ValidateCodeUtils.generateValidateCode(4).toString();
        System.out.println(s);
        EmailUtils.sendAuthCodeEmail(email,s);
    }

    @Test
    void  test11(){
        Integer integer = bigManagerService.updatePassword(3L, "123456789", "123456789", "123456789");
        System.out.println(integer);

    }




}
