package com.example.usercenter2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter2.common.ErrorCode;
import com.example.usercenter2.common.ResultUtils;
import com.example.usercenter2.constanr.UserConstant;
import com.example.usercenter2.exception.BusinessException;
import com.example.usercenter2.mapper.UserMapper;
import com.example.usercenter2.model.User;
import com.example.usercenter2.service.UserService;
import com.example.usercenter2.utils.EmailUtils;
import com.example.usercenter2.vo.UserManagerByIdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceimpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 定义加盐数
     */
    private static final String SALT = "xinyu";


    @Override
    public long registByuser(String username,String userAccount,String userPassword,String checkPassword,Integer gender,String email,String phone) {
        //非空,如果数据过多可以考虑使用框架 Apach commons Lang
        if (userAccount == null || userPassword == null || checkPassword == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //账号长度不小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度小于4位");
        }
        //密码就不小于8位
        if (userPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度小于8位");
        }
        //账户不能包含特殊字符
        String strPartter = ".*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“’。，、？\\\\\\\\]+.*";
        Matcher matcher = Pattern.compile(strPartter).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号有特殊字符");
        }
        //密码和效验码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和效验码相同");
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> usercondicter = queryWrapper.eq("user_account", userAccount).eq("username",username);
        Long aLong = userMapper.selectCount(usercondicter);
        if (aLong > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号已经注册");
        }

        //密码进行加密
        String encryptpassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        String deafultavatar="https://images.pexels.com/photos/12705664/pexels-photo-12705664.jpeg?auto=compress&cs=tinysrgb&w=600";
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptpassword);
        user.setUsername(username);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAvatarUrl(deafultavatar);
        System.out.println(user);
        int insert = userMapper.insert(user);
        if (insert <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return user.getId();
    }

    @Override
    public User loginuser(String useraccount, String userpassword, HttpServletRequest request) {
        //非空,如果数据过多可以考虑使用框架 Apach commons Lang
        if (useraccount == null || userpassword == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //账号长度不小于4位
        if (useraccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //密码就不小于8位
        if (userpassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //账户不能包含特殊字符
        String strPartter = ".*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“’。，、？\\\\\\\\]+.*";
        Matcher matcher = Pattern.compile(strPartter).matcher(useraccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //密码进行加密
        String encryptpassword = DigestUtils.md5DigestAsHex((SALT + userpassword).getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account",useraccount);
        userQueryWrapper.eq("user_password",encryptpassword);
        userQueryWrapper.eq("user_status",0);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            log.info("=====user login faile====");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //用户信息脱敏
        User user1 = handldUser(user);

        //记录用户登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user1);

        return user1;
    }

    /**
     * 用户脱敏
     * @param objuser
     * @return
     */
    public User handldUser(User objuser){
        User user = new User();
        user.setUserRole(objuser.getUserRole());
        user.setId(objuser.getId());
        user.setUsername(objuser.getUsername());
        user.setUserAccount(objuser.getUserAccount());
        user.setAvatarUrl(objuser.getAvatarUrl());
        user.setGender(objuser.getGender());
        user.setEmail(objuser.getEmail());
        user.setUserStatus(objuser.getUserStatus());
        user.setPhone(objuser.getPhone());
        user.setCreateTime(objuser.getCreateTime());
        user.setUpdateTime(objuser.getUpdateTime());
        user.setLoginTotal(objuser.getLoginTotal());
        user.setPublicContribution(objuser.getPublicContribution());
        return user;
    }

    @Override
    public int loginOut(HttpServletRequest request) {
        request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 搜索分页查询
     * @param userPage
     * @param user
     * @return
     */
    @Override
    public IPage<User> searchUser(Page<User> userPage, User user) {
        IPage<User> userIPage = userMapper.searchUser(userPage, user);
        System.out.println(userIPage);
        return userIPage;
    }



    /**
     * 编辑用户
     * @param id1
     * @param userStatus
     * @return
     */
    @Override
    public Integer editUser(long id1, Integer userStatus) {
        Integer integer = userMapper.updateUserStatusById(id1, userStatus);
        return integer;
    }

    @Override
    public List<User> UserTime(String beginTime, String endTime) {
        System.out.println("beginTime="+beginTime+", endTime="+endTime);
        List<User> userList = userMapper.queryUserByTime(beginTime, endTime);
        return userList;
    }

    @Override
    public Integer updateUserAvatar(Long id, String avatarUrl) {
        if(id==null){
            throw  new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer integer = userMapper.updateUserAvatar(id, avatarUrl);
        return integer;
    }

    @Override
    public Integer updateBasic(String username, Integer gender,Long id) {
        if(id==null||username==null||username==""){
            throw new BusinessException((ErrorCode.NULL_ERROR));
        }
        Integer integer = userMapper.updateBasic(id, username, gender);
        return integer;
    }

    @Override
    public UserManagerByIdVo findUserById(Long id) {
        User userById = userMapper.findUserById(id);
        UserManagerByIdVo userManagerByIdVo = new UserManagerByIdVo(
                userById.getUsername(),
                userById.getUserAccount(),
                userById.getAvatarUrl(),
                userById.getGender(),
                userById.getEmail(),
                userById.getUserStatus(),
                userById.getPhone(),
                userById.getCreateTime(),
                userById.getUpdateTime(),
                userById.getStatusDelete(),
                userById.getUserRole(),
                userById.getLoginTotal(),
                userById.getPublicContribution());
        return userManagerByIdVo;
    }

    @Override
    public Integer updateUserPasswordByPassword(Long id, String oldPassword, String newPassword, String checkNewPassword) {
        if(id==null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "id为空");
        }
        if(newPassword.length()<6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度小于6");
        }
        if(checkNewPassword.length()<6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"新密码密码长度小于6");
        }
        //密码和效验码相同
        if (!newPassword.equals(checkNewPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和效验码相同");
        }
        String encryptpassword1 = DigestUtils.md5DigestAsHex((SALT + oldPassword).getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",id);
        userQueryWrapper.eq("user_password",encryptpassword1);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            log.info("=====user update faile====");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //密码进行加密
        String encryptpassword = DigestUtils.md5DigestAsHex((SALT + newPassword).getBytes());
        Integer integer = userMapper.updateUserPassword(id, encryptpassword);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"修改密码失败");
        }
        return integer;

    }

    @Override
    public Integer updateUserEmail(Long id, String email) {
        //邮箱格式要匹配正确
        String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern compile = Pattern.compile(emailRegex);
        Matcher matcher = compile.matcher(email);
        if(!matcher.matches()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱格式不匹配");
        }
        Integer integer = userMapper.updateUserEmail(id, email);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return integer;
    }

    @Override
    public Integer updateUserPhone(Long id, String phone) {
        if(id==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        /**
         * 正则表达式，用于匹配手机号码的格式
         * 1、以1开头
         * 2、第二位数字为3，4，5，6，7，8，9中一个
         * 3、第三位到十一位数字为0到9的任意一个数字
         * 5. 总位数是11位
         */
        String phoneRegex = "^1[3-9]\\d{9}$";
        Pattern pattern=Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        boolean b = matcher.find();
        if(!b){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"手机号码格式不匹配");
        }
        Integer integer = userMapper.updateUserPhone(id, phone);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return integer;
    }

    /**
     * 根据邮箱修改密码
     * @param email
     * @param password
     * @param verify
     * @param request
     * @return
     */
    @Override
    public Integer updateUserPassword(String email, String password,String checkPassword,String verify,HttpServletRequest request) {
        if(email==null||email==""||password==null||password==""||verify==null||verify=="") {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if(!password.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不一致");
        }
        HttpSession session = request.getSession();
        String verifyforget = (String) session.getAttribute("verifyforget");
        if(!verifyforget.equals(verify)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码不匹配");
        }
        //密码进行加密
        String encryptpassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        Integer integer = userMapper.updateUserPasswordByEmail(email, encryptpassword);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return integer;

    }

    /**
     * 发送反馈消息
     * @param id
     * @param name
     * @return
     */
    @Override
    public String sendTipoff(String id, String name,Boolean isErr) {
        if(id==null||id==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User userById = userMapper.findUserById(Long.parseLong(id));
        if(userById==null){
            return UserConstant.EMAILERR;
        }
        String email = userById.getEmail();
        if(isErr){
            EmailUtils.sendTipoffEmailSuccess(email,name);
        }else {
            EmailUtils.sendTipoffEmail(email,name);
        }
        return UserConstant.EMAILSUCCESS;
    }


}
