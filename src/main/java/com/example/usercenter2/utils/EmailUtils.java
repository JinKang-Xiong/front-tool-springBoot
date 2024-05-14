package com.example.usercenter2.utils;


import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * 邮件发送工具类
 */
public class EmailUtils {
        public static void sendAuthCodeEmail(String email, String authCode) {
            try {
                SimpleEmail mail = new SimpleEmail();
                mail.setHostName("smtp.qq.com");//发送邮件的服务器,这个是qq邮箱的，不用修改
                mail.setAuthentication("2385472291@qq.com", "xxxxxxxjink");//第一个参数是对应的邮箱用户名一般就是自己的邮箱第二个参数就是SMTP的密码,我们上面获取过了
                mail.setFrom("2385472291@qq.com","前端工具箱");  //发送邮件的邮箱和发件人
                mail.setSSLOnConnect(true); //使用安全链接
                mail.addTo(email);//接收的邮箱
                mail.setSubject("验证码");//设置邮件的主题
                mail.setMsg("尊敬的用户:您好!\n 您正在修改前端工具箱的安全信息，请在验证码输入框中输入:" + authCode+"\n"+"     (有效期为一分钟)");//设置邮件的内容
                mail.send();//发送
                System.out.println("邮件发送成功");
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }
    public static void sendTipoffEmail(String email, String name) {
        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器,这个是qq邮箱的，不用修改
            mail.setAuthentication("2385472291@qq.com", "alkfiqgsqztvdjib");//第一个参数是对应的邮箱用户名一般就是自己的邮箱第二个参数就是SMTP的密码,我们上面获取过了
            mail.setFrom("2385472291@qq.com", "前端工具箱");  //发送邮件的邮箱和发件人
            mail.setSSLOnConnect(true); //使用安全链接
            mail.addTo(email);//接收的邮箱
            mail.setSubject("系统通知");//设置邮件的主题
            mail.setMsg("您好，您的公共资源" + name + ",被反馈给我们审核了，审核不通过，资源的公共性被封禁，调整为私有性，请您根据公共资源规范修改后，再次发布我们会继续审核，审核通过会解除封禁");//设置邮件的内容
            mail.send();//发送
            System.out.println("邮件发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

    public static void sendTipoffEmailSuccess(String email, String name) {
        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器,这个是qq邮箱的，不用修改
            mail.setAuthentication("2385472291@qq.com", "alkfiqgsqztvdjib");//第一个参数是对应的邮箱用户名一般就是自己的邮箱第二个参数就是SMTP的密码,我们上面获取过了
            mail.setFrom("2385472291@qq.com","前端工具箱");  //发送邮件的邮箱和发件人
            mail.setSSLOnConnect(true); //使用安全链接
            mail.addTo(email);//接收的邮箱
            mail.setSubject("系统通知");//设置邮件的主题
            mail.setMsg("您好，您的公共资源"+name+",被反馈给我们审核了，审核通过，暂时没有问题，不过建议您还是按照公共资源规范再次检查一下，谢谢您对公共库的贡献");//设置邮件的内容
            mail.send();//发送
            System.out.println("邮件发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    }
