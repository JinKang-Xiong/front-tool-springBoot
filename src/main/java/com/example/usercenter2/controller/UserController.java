package com.example.usercenter2.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenter2.common.BaseController;
import com.example.usercenter2.common.BaseRespone;
import com.example.usercenter2.common.ErrorCode;
import com.example.usercenter2.common.ResultUtils;
import com.example.usercenter2.constanr.UserConstant;
import com.example.usercenter2.exception.BusinessException;
import com.example.usercenter2.mapper.UserMapper;
import com.example.usercenter2.model.User;
import com.example.usercenter2.model.requestparam.UpdateUserPassword;
import com.example.usercenter2.model.requestparam.UserLoginRequest;
import com.example.usercenter2.model.requestparam.UserRegistRequest;
import com.example.usercenter2.model.requestparam.UserSearchRequest;
import com.example.usercenter2.service.UserService;
import com.example.usercenter2.utils.EmailUtils;
import com.example.usercenter2.utils.JwtTokenUtil;
import com.example.usercenter2.utils.SMSUtils;
import com.example.usercenter2.utils.ValidateCodeUtils;
import com.example.usercenter2.vo.UserManagerByIdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户控制类
 *
 * @Author jingkang
 */
@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private BaseController baseController;




    /**
     * 用户注册控制层
     * @param userRegistRequest
     * @return
     */
    @RequestMapping("/register")
    public BaseRespone<Long> userRegist(@RequestBody UserRegistRequest userRegistRequest){
            String userAcount=userRegistRequest.getUserAccount();
            String username=userRegistRequest.getUsername();
            String userPassword=userRegistRequest.getUserPassword();
            String checkPassword=userRegistRequest.getCheckPassword();
            String email=userRegistRequest.getEmail();
            String phone=userRegistRequest.getPhone();
            Integer gender=userRegistRequest.getGender();

            if(userAcount == null || userPassword == null || checkPassword == null ||username==null ||email==null || phone==null){
                return ResultUtils.error(ErrorCode.NULL_ERROR);
            }

        long l = userService.registByuser(username,userAcount,userPassword,checkPassword,gender,email,phone);
        return ResultUtils.succeess(l);
    }



    /**
     * 用户登录控制层
     * @param userLoginRequest 登录信息类
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public BaseRespone<String> userlogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response){
        String useraccount=userLoginRequest.getUseraccount();
        String userpassword=userLoginRequest.getUserpassword();
        boolean isRember=userLoginRequest.isRemerber();
        System.out.println("useraccount="+useraccount+"userpassword="+userpassword);

        if(useraccount == null || userpassword == null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }

        User loginuser = userService.loginuser(useraccount, userpassword, request);
        int loginTotal=loginuser.getLoginTotal();
        Long id1=loginuser.getId();
        int loginTotal1=loginTotal+1;
        User user = new User();
        user.setId(id1);
        user.setLoginTotal(loginTotal1);
        boolean b = userService.updateById(user);
        if(!b){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String id=String.valueOf(loginuser.getId());
        String token= JwtTokenUtil.createToken(id,loginuser.getUsername(),isRember);
        return ResultUtils.succeess(JwtTokenUtil.TOKEN_PREFIX+token);

    }

    /**
     * 查询登录用户的信息
     * @param request
     * @return
     */

    @RequestMapping("/current")
    public  BaseRespone<User> getcurrent(HttpServletRequest request){
        String userId1 = baseController.getUserId(request);
        long userId=Long.parseLong(userId1);
        if(userId == 0){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        User user = userService.getById(userId);
        if(user == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        System.out.println(user);
        return ResultUtils.succeess(user);

    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @RequestMapping("/finduserbyid")
    public BaseRespone<UserManagerByIdVo> findUserById(String id){
        if(id==null||id==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long l = Long.parseLong(id);
        UserManagerByIdVo userById = userService.findUserById(l);
        return ResultUtils.succeess(userById);
    }


    /**
     * 搜索功能
     * @param userSearchRequest
     * @param request
     * @return
     */
    @RequestMapping("/search")
    public BaseRespone<IPage<User>> searchUser(@RequestBody UserSearchRequest userSearchRequest, HttpServletRequest request){
        Integer current=userSearchRequest.getCurrent();
        Integer pagesize=userSearchRequest.getPageSize();
        User user=userSearchRequest.getUser();
        System.out.println("current="+current+"pagesize="+pagesize+"user="+user);
        
        Page<User> userPage=new Page<>(current,pagesize);
        IPage<User> userIPage = userService.searchUser(userPage, user);
        System.out.println(userIPage);
        return ResultUtils.succeess(userIPage);

    }


    /**
     * 删除功能
     * @param id
     * @param
     * @return
     */
    @RequestMapping("/delete")
    public BaseRespone<Boolean> deleteUser( String id){
        System.out.println("id="+id);
       if(id == null){
           throw new BusinessException(ErrorCode.NULL_ERROR);
       }
       Long id1=Long.parseLong(id);
       User user = new User();
       user.setId(id1);
       user.setStatusDelete(1);
       boolean b = userService.updateById(user);
       if(!b){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(b);
    }

    /**
     * 登出功能
     * @param request
     * @return
     */
    @RequestMapping("/loginout")
    public BaseRespone<Integer> loginOut(HttpServletRequest request){
        if(request == null){
            return ResultUtils.error(ErrorCode.NOT_LOGIN);
        }
        int i = userService.loginOut(request);
        return ResultUtils.succeess(i);
    }

    /**
     * 编辑功能
     * @param id
     * @param userStatus
     * @param request
     * @return
     */
    @RequestMapping("/edituser")
    public BaseRespone<Integer> editUser(String id,Integer userStatus,HttpServletRequest request){
        if(id==null){
            throw  new BusinessException(ErrorCode.NULL_ERROR);
        }
        long id1=Long.parseLong(id);
        int i = userService.editUser(id1, userStatus);
        return ResultUtils.succeess(i);
    }


    /**
     * 注册的用户数量
     * @return
     */
    @GetMapping("/total")
    public BaseRespone<List<User>> totaluser(String beginTime, String endTime){
        System.out.println("beginTime="+beginTime+",endTime="+endTime);
        List<User> userList = userService.UserTime(beginTime, endTime);
        return ResultUtils.succeess(userList);
    }

    /**
     * 所有的用户贡献的公共资源总数
     * @param
     * @return
     */
    @GetMapping("/sumpublic")
    public BaseRespone<Integer> sumpublic(){
        Integer integer = userMapper.querysumByPublicCon();
        return ResultUtils.succeess(integer);
    }

    /**
     * 查询异常用户总数
     * @return
     */
    @GetMapping("/abnormal")
    public BaseRespone<Integer> queryAbnormal(){
        Integer integer = userMapper.queryByUserStatus();
        return ResultUtils.succeess(integer);
    }

    /**
     * 获取排序前几得到数据
     * @param range
     * @return
     */
    @GetMapping("/queryRange")
    public BaseRespone<List<User>> queryRange(Integer range){
        List<User> userList = userMapper.queryByCreateTime(range);
        System.out.println(userList);
        return ResultUtils.succeess(userList);
    }

    /**
     * 修改用户头像
     */
    @Value("${file.upload-path}")
    private String pictureurl;
    @RequestMapping("/updateAvatar")
    public BaseRespone<Integer> updateAvatar(@RequestParam("id") String id,@RequestParam("avatar") MultipartFile file){
        String fileName=file.getOriginalFilename();
        File saveFile=new File(pictureurl);
        //拼接url，采用随机数，保证每个图片的url不一样
        UUID uuid=UUID.randomUUID();
        //重型拼接文件名，这里可以加if判断
        int index=fileName.indexOf(".");
        String newFileName="/images/avatar/"+fileName.replace(".","")+uuid+fileName.substring(index);
        Long id1=Long.parseLong(id);
        //测试插入失败的错误
        Long id2=Long.parseLong("4564");
        Integer integer = userService.updateUserAvatar(id1,newFileName);
        System.out.println(integer);
        if(integer <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        try {
            file.transferTo(new File(pictureurl + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("保持成功");
        return ResultUtils.succeess(integer);
    }


    /**
     * 修改用户基本信息
     * @param username
     * @param gender
     * @param id
     * @return
     */
    @GetMapping("/updatebasic")
    public  BaseRespone<Integer> updateBasic(String username,Integer gender,String id){
        if(id==null||id==""||username==null||username==""){
            throw new BusinessException((ErrorCode.NULL_ERROR));
        }
        long l = Long.parseLong(id);
        Integer integer = userService.updateBasic(username, gender, l);
        return ResultUtils.succeess(integer);
    }

    /**
     * 修改密码
     * @param updateUserPassword
     * @return
     */
    @PostMapping("/updatePassword")
    public  BaseRespone<Integer> updatePassword(@RequestBody UpdateUserPassword updateUserPassword){
        if(updateUserPassword.getId()==null||updateUserPassword.getId()==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long l = Long.parseLong(updateUserPassword.getId());
        Integer integer = userService.updateUserPasswordByPassword(l, updateUserPassword.getOldPassword(), updateUserPassword.getNewPassword(), updateUserPassword.getCheckNewPassword());
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    /**
     * 获取邮箱验证码
     * @param email
     * @return
     */
    @GetMapping("/emailvalidate")
    public BaseRespone<String> emailvalidate(String email){
        if(email==null || email==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String s = ValidateCodeUtils.generateValidateCode(4).toString();
        EmailUtils.sendAuthCodeEmail(email,s);
        return ResultUtils.succeess(s);
    }

    /**
     * 忘记密码使用邮箱验证码找回
     * @param email
     * @return
     */
    @GetMapping("/emailvalidateforget")
    public BaseRespone<String> emailvalidateForget(String email,HttpServletRequest request){
        if(email==null || email==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String s = ValidateCodeUtils.generateValidateCode(4).toString();
        HttpSession session = request.getSession();
        session.setAttribute("verifyforget",s);
        session.setMaxInactiveInterval(300); // 设置会话超时时间为30分钟
        EmailUtils.sendAuthCodeEmail(email,s);
        return ResultUtils.succeess(s);
    }

    /**
     * 根据邮箱修改密码
     * @param email
     * @param password
     * @param verfity
     * @param request
     * @return
     */
    @GetMapping("/updatepasswordbyemail")
    public BaseRespone<Integer> updatePasswordByEmail(String email,String password,String checkPassword,String verfity,HttpServletRequest request){
        if(email==null||email==""||password==null||password==""||verfity==null||verfity==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer integer = userService.updateUserPassword(email, password, checkPassword,verfity, request);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    /**
     * 修改用户的邮箱
     * @param id
     * @param email
     * @return
     */
    @GetMapping("/updateemail")
    public BaseRespone<Integer> updateEmail(String id,String email){
        if(id==null||id==""||email==""||email==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long l = Long.parseLong(id);
        Integer integer = userService.updateUserEmail(l, email);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    /**
     * 手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/validatecheck")
    public  BaseRespone<String> validatecheck(String phone){
        System.out.println("进啦 validatecheck");
        if(phone ==null || phone==""){
            throw new BusinessException(ErrorCode.NULL_ERROR,"手机号为空");
        }
        String s = ValidateCodeUtils.generateValidateCode(4).toString();
        SMSUtils.sendMessage("熊锦康德工具箱","SMS_461560275",phone,s);
        return ResultUtils.succeess(s);
    }

    @GetMapping("/updatephone")
    public BaseRespone<Integer> updatePhone(String id,String phone){
        if(id==null||id==""||phone==null||phone==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long l = Long.parseLong(id);
        Integer integer = userService.updateUserPhone(l, phone);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    /**
     * 发送反馈邮箱
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/sendtipoff")
    public  BaseRespone<String> SendTipOff(String id,String name,boolean isErr){
        if(id==null||id==""||name==null||name==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String s = userService.sendTipoff(id, name,isErr);
        return ResultUtils.succeess(s);
    }

    /**
     * 把Delete和search中公有的方法提出来，因为在公司的时候提交代码会检查，相同的代码有10行时，就会提交失败
     * 超过三个就必须考虑提出来，
     * 这是在对角色作信息验证
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request){
        Object userObject = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user=(User) userObject;
        if(user == null || user.getUserRole() != UserConstant.ADMIN_ROLE || user.getUserRole() != UserConstant.BIG_ADMIN_ROLE ){
            return false;
        }
        return true;
    }





}
