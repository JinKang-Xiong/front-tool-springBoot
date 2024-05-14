package com.example.usercenter2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter2.model.User;
import com.example.usercenter2.vo.UserManagerByIdVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;

/**
 * 用户的服务
 *
 * @Author  jinkang
 */
@Service
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param username 用户名
     * @param useraccount 账号
     * @param userpassword  用户密码
     * @param checkpassword 确认密码
     * @param gender    性别
     * @param email     邮箱
     * @param phone     电话
     * @return
     */
    long registByuser(String username,String userAccount,String userPassword,String checkPassword,Integer gender,String email,String phone);

    /**
     * 用户登录
     * @param useraccount 账号
     * @param userpassword  密码
     * @param request
     * @return user
     */
    User loginuser(String useraccount, String userpassword, HttpServletRequest request);


    /**
     * 用户脱敏
     * @param objuser
     * @return
     */
    User handldUser(User objuser);

    /**
     * 用户登出
     * @return
     */
    int loginOut(HttpServletRequest request);


    /**
     * 搜索查询
     * @param userPage
     * @param user
     * @return
     */
    IPage<User> searchUser(Page<User> userPage,User user);


    /**
     * 编辑用户
     * @param id1
     * @param userStatus
     * @return
     */
    Integer editUser(long id1, Integer userStatus);


    /**
     * 根据时间来查询用户
     * @param beginTime
     * @param endTime
     * @return
     */
    List<User> UserTime(String beginTime, String endTime);


    /**
     * 更新用户头像
     * @param id
     * @param avatarUrl
     * @return
     */
    Integer updateUserAvatar(Long id,String avatarUrl);

    /**
     * 更新用户的基本信息
     * @param username
     * @param gender
     * @return
     */
    Integer updateBasic(String username,Integer gender,Long id);

    /**
     * 根据id去查询用户
     * @param id
     * @return
     */
    UserManagerByIdVo findUserById(Long id);

    /**
     * 根据原密码修改密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @param checkNewPassword
     * @return
     */
    Integer updateUserPasswordByPassword(Long id,String oldPassword,String newPassword,String checkNewPassword);

    /**
     * 修改用户邮箱
     * @param id
     * @param email
     * @return
     */
    Integer updateUserEmail(Long id,String email);

    /**
     * 修改手机号
     * @param id
     * @param phone
     * @return
     */
    Integer updateUserPhone(Long id,String phone);

    /**
     * 根据邮箱修改密码
     * @param email
     * @param password
     * @return
     */
    Integer updateUserPassword(String email,String password,String checkPassword,String verify,HttpServletRequest request);

    String sendTipoff(String id,String name,Boolean isErr);
}
