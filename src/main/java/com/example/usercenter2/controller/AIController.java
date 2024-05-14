package com.example.usercenter2.controller;

import com.example.usercenter2.common.BaseRespone;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ai")
public class AIController {
    @Resource
    private YuCongMingClient client;


    /**
     * 方法名：doChat
     *
     * 请求参数：
     *
     *     modelId：使用的会话模型（助手）id
     *     message：要发送的消息，不超过 1024 字
     *
     * 响应结果：
     *
     *     code：响应状态码
     *     data：
     *         content：对话结果内容
     *     message：响应信息
     * @param message
     * @return
     */
    @GetMapping("/chat")
    public BaseRespone<Object>  chat(String message){
        return null;
    }
}
