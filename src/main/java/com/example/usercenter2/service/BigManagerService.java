package com.example.usercenter2.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter2.model.BigManager;
import com.example.usercenter2.vo.BigManagerVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * 大管理员的服务接口
 */
@Service
public interface BigManagerService extends IService<BigManager> {

    /**
     * 大管理员登录
     * @param bigManagerAccount
     * @param bigManagerPassword
     * @return
     */
    BigManager loginBigManager(String bigManagerAccount,String bigManagerPassword);

    /**
     * 根据Id获取用户的登录态
     * @param id
     * @return
     */
    BigManager queryById(Long id);


    /**
     * 更新头像
     * @param id
     * @param newFileName
     * @return
     */
    Integer updateAvatar(Long id,String newFileName);


    /**
     * 修改用户的基础信息
     * @param id
     * @param bigManagerName
     * @param age
     * @param gender
     * @return
     */
    Integer updateBasic(Long id,String bigManagerName,Integer age,Byte gender);

    /**
     * 修改密码
     * @param id
     * @param oldPassword
     * @param newPasswrd
     * @return
     */
    Integer updatePassword(Long id,String oldPassword,String newPasswrd,String checknewPassword);

    /**
     * 新增加管理员
     * @param bigManager
     * @return
     */
    Integer addBigManager(BigManager bigManager);

    /**
     * 搜索分页功能
     * @param bigManagerVoPage
     * @param bigManager
     * @return
     */
    IPage<BigManagerVo> searchBigManager(Page<BigManagerVo> bigManagerVoPage, BigManager bigManager);

    /**
     * 修改用户信息
     * @param bigManager
     * @return
     */
    Integer updateBigManager(BigManager bigManager);

    /**
     * 注销管理员
     * @param id
     * @param statusDelete
     * @return
     */
    Integer deleteBigManager(Long id,Byte statusDelete);

    /**
     * 修改密码
     * @param id
     * @param newPassword
     * @param checkNewPassword
     * @return
     */
    Integer UpdatePasswordByIdAndPhone(String id,String newPassword,String checkNewPassword );

    /**
     * 修改邮箱
     * 根据邮箱验证码的
     * @param id
     * @param email
     * @return
     */
    Integer updateEmailByIdAndEmail(String id,String email);

    /**
     * 修改手机号
     * @param id
     * @param phone
     * @return
     */
    Integer updatePhone(Long id,String phone);

    Integer updatePasswordByEmail(String email, String password, String checkPassword, String verify, HttpServletRequest request);
}
