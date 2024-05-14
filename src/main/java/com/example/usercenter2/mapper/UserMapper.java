package com.example.usercenter2.mapper;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenter2.model.User;
import com.example.usercenter2.vo.UserManagerByIdVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {


    /**
     * 分页搜索查询
     * @param userIPage
     * @param user
     * @return
     */
    IPage<User> searchUser(IPage<User> userIPage,@Param("user") User user);

    /**
     * 编辑用户信息
     * @param id
     * @return
     */
    Integer updateUserStatusById(@Param("id") Long id ,@Param("userStatus") Integer userStatus);

    /**
     * 查询全部用户
     * @return
     */
    List<User> queryUserByTime(@Param("beginTime") String beginTime,@Param("endTime") String endTime);

    /**
     * 查询用户的总贡献量
     */
    Integer querysumByPublicCon();

    /**
     * 查询异常账号的数量
     * userStatus=1
     * @return
     */
    Integer queryByUserStatus();

    /**
     * 查询登录次数最多的前10条数据
     * @return
     */
    List<User> queryByCreateTime(@Param("range") Integer range);

    /**
     * 修改用户头像
     * @param id
     * @param avatarUrl
     * @return
     */
    Integer updateUserAvatar(@Param("id") Long id,@Param("avatarUrl") String avatarUrl);

    /**
     * 修改基础信息
     * @param id
     * @param username
     * @param gender
     * @return
     */
    Integer updateBasic(@Param("id") Long id,@Param("username") String username,@Param("gender") Integer gender);

    /**
     * 根据id查询用户的数据
     * @param id
     * @return
     */
    User findUserById(@Param("id") Long id);

    /**
     * 修改密码
     * @param id
     * @param
     * @param newPassword
     * @param
     * @return
     */
    Integer updateUserPassword(@Param("id") Long id,@Param("newPassword") String newPassword);

    /**
     * 修改邮箱
     * @param id
     * @param email
     * @return
     */
    Integer updateUserEmail(@Param("id")Long id,@Param("email") String email);

    /**
     * 修改手机号
     * @param id
     * @param phone
     * @return
     */
    Integer updateUserPhone(@Param("id") Long id,@Param("phone") String phone);

    /**
     * 根据邮箱修改密码
     * @param email
     * @param password
     * @return
     */
    Integer updateUserPasswordByEmail(@Param("email") String email,@Param("password") String password);

}
