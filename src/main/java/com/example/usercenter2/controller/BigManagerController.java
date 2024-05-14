package com.example.usercenter2.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenter2.common.BaseController;
import com.example.usercenter2.common.BaseRespone;
import com.example.usercenter2.common.ErrorCode;
import com.example.usercenter2.common.ResultUtils;
import com.example.usercenter2.exception.BusinessException;
import com.example.usercenter2.mapper.BigManagerMapper;
import com.example.usercenter2.model.BigManager;
import com.example.usercenter2.model.requestparam.BigManagerSearchRequest;
import com.example.usercenter2.model.requestparam.UpdatePasswordRequest;
import com.example.usercenter2.service.BigManagerService;
import com.example.usercenter2.utils.EmailUtils;
import com.example.usercenter2.utils.JwtTokenUtil;
import com.example.usercenter2.utils.SMSUtils;
import com.example.usercenter2.utils.ValidateCodeUtils;
import com.example.usercenter2.vo.BigManagerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 大管理员的控制类
 */
@RestController
@RequestMapping("/bigmanager")
public class BigManagerController {

    @Autowired
    BigManagerService bigManagerService;

    @Resource
    BigManagerMapper bigManagerMapper;

    @Autowired
    BaseController baseController;

    /**
     * 登录功能
     * @param map
     * @return
     */
    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public BaseRespone<String> bigmanagerlogin(@RequestBody Map<String,Object> map){
        String bigManagerAccount=(String)map.get("bigManagerAccount");
        String bigManagerPassword=(String) map.get("bigManagerPassword");
        boolean remenber=(boolean) map.get("remenber");
        if(bigManagerAccount ==null || bigManagerPassword ==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        System.out.println(bigManagerAccount+bigManagerPassword);
        BigManager bigManager = bigManagerService.loginBigManager(bigManagerAccount, bigManagerPassword);
        System.out.println(bigManager);
        String id=String.valueOf(bigManager.getId());
        String token= JwtTokenUtil.createToken(id,bigManager.getBigManagerName(),remenber);
        System.out.println(token);
        return ResultUtils.succeess(JwtTokenUtil.TOKEN_PREFIX+token);
    }

    /**
     * 获取用户登录态的功能
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseRespone<BigManager> bigmanagercurrent(HttpServletRequest request){
        String userId1 = baseController.getUserId(request);
        if(userId1 == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long id=Long.parseLong(userId1);
        BigManager bigManager = bigManagerService.queryById(id);
        return ResultUtils.succeess(bigManager);

    }

    /**
     * 修改基础项
     * @param bigManager
     * @return
     */
    @PostMapping("/updatebasic")
    public BaseRespone<Integer> updatebasic(@RequestBody BigManager bigManager){
        Long id = bigManager.getId();
        String bigManagerName=bigManager.getBigManagerName();
        Integer age = bigManager.getAge();
        Byte gender = bigManager.getGender();
        System.out.println("id="+id+bigManagerName+age+gender);

        Integer integer = bigManagerService.updateBasic(id, bigManagerName, age, gender);

        return ResultUtils.succeess(integer);

    }

    /**
     * 修改密码
     * @param updatePasswordRequest
     * @return
     */
    @PostMapping("/updatePassword")
    public BaseRespone<Integer> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        String id =updatePasswordRequest.getId();
        String oldPassword = updatePasswordRequest.getOldPassword();
        String newPassword = updatePasswordRequest.getNewPassword();
        String checkPassword = updatePasswordRequest.getCheckPassword();
        if(oldPassword ==null || newPassword==null || checkPassword==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long id1=Long.parseLong(id);
        Integer integer = bigManagerService.updatePassword(id1, oldPassword, newPassword, checkPassword);
        return ResultUtils.succeess(integer);
    }


    /**
     * 修改头像
     * @param id
     * @param file 文件
     * @return
     */
    @Value("${file.upload-path}")
    private String pictureurl;
    @RequestMapping("/updateAvatar")
    public BaseRespone<Integer> updateAvatar(@RequestParam("id") String id,@RequestParam("avatar") MultipartFile file){
        System.out.println("id="+id);
        System.out.println(file);
        String fileName=file.getOriginalFilename();
        System.out.println("fileName="+fileName);
        System.out.println("size="+file.getSize());
        File saveFile=new File(pictureurl);
        //拼接url，采用随机数，保证每个图片的url不一样
        UUID uuid=UUID.randomUUID();
        //重型拼接文件名，这里可以加if判断
        int index=fileName.indexOf(".");
        String newFileName="/images/avatar/"+fileName.replace(".","")+uuid+fileName.substring(index);
        System.out.println("newFileName="+newFileName);
        System.out.println(new File(pictureurl + newFileName));
        Long id1=Long.parseLong(id);
        //测试插入失败的错误
        Long id2=Long.parseLong("4564");
        Integer integer = bigManagerService.updateAvatar(id1, newFileName);
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
     * 新增加管理员
     * @param bigManager
     * @return
     */
    @PostMapping("/addbigmanager")
    public BaseRespone<Integer> addBigManager(@RequestBody BigManager bigManager){
        if(bigManager == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = bigManagerService.addBigManager(bigManager);
        if(integer <=0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    /**
     * 搜索分页的功能
     * @param bigManagerSearchRequest
     * @return
     */
    @PostMapping("/search")
    public BaseRespone<IPage<BigManagerVo>> searchBigManager(@RequestBody BigManagerSearchRequest bigManagerSearchRequest){
        BigManager bigManager = bigManagerSearchRequest.getBigManager();
        Integer current = bigManagerSearchRequest.getCurrent();
        Integer pageSize = bigManagerSearchRequest.getPageSize();
        Page<BigManagerVo> page = new Page<>(current, pageSize);
        IPage<BigManagerVo> bigManagerVoIPage = bigManagerService.searchBigManager(page, bigManager);
        return ResultUtils.succeess(bigManagerVoIPage);


    }

    /**
     * 修改管理员信息
     * @param bigManager
     * @return
     */
    @PostMapping("/updateBigManager")
    public BaseRespone<Integer> updatebigmanager(@RequestBody BigManager bigManager){
        Integer integer = bigManagerService.updateBigManager(bigManager);
        if(integer <=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    /**
     * 注销用户
     * @param id
     * @param statusDelete
     * @return
     */
    @GetMapping("/deleteBigManager")
    public BaseRespone<Integer> deleteBigManager(String id,Byte statusDelete){
        Long id1=Long.parseLong(id);
        Integer integer = bigManagerService.deleteBigManager(id1, statusDelete);
        if(integer <=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.succeess(integer);

    }

    /**
     *查询管理员总数
     * @return
     */
    @GetMapping("/total")
    public BaseRespone<Integer> totalBigManager(){
        Integer integer = bigManagerMapper.querycountBigManager();
        return ResultUtils.succeess(integer);
    }


    /**
     * 验证码发送
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

    /**
     * 修改手机号
     * @param id
     * @param phone
     * @return
     */
    @GetMapping("/updatephone")
    public BaseRespone<Integer> updatePhone(String id,String phone){
        if(id==null||id==""||phone==null||phone==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long l = Long.parseLong(id);
        Integer integer = bigManagerService.updatePhone(l, phone);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    /**
     * 手机验证码通过验证
     * @param map
     * @return
     */
    @PostMapping("/updatePasswordByPhone")
    public  BaseRespone<Integer> updatePassword(@RequestBody Map<String,String> map){
        String password = map.get("newPassword");
        String checkpassword = map.get("checkNewPassword");
        String id = map.get("id");
        if(password ==null || password=="" || checkpassword==null || checkpassword=="" || id==null || id==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer integer = bigManagerService.UpdatePasswordByIdAndPhone(id, password, checkpassword);
        if(integer <=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return  ResultUtils.succeess(integer);
    }

    /**
     * 修改邮箱
     * @param map
     * @return
     */
    @PostMapping("/updateemail")
    public BaseRespone<Integer> updateemail(@RequestBody Map<String,String> map){
        String id = map.get("id");
        String email = map.get("email");
        System.out.println("id="+id+",email="+email);
        if (id == null || email==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Integer integer = bigManagerService.updateEmailByIdAndEmail(id, email);
        if(integer<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.succeess(integer);
    }

    /**
     * 邮箱验证码
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
        session.setAttribute("verifyforgetbig",s);
        session.setMaxInactiveInterval(300); // 设置会话超时时间为5分钟
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
        Integer integer = bigManagerService.updatePasswordByEmail(email, password, checkPassword,verfity, request);
        if(integer<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.succeess(integer);
    }

    @GetMapping("/querybyid")
    public BaseRespone<BigManager> querybyid(String id){
        if(id==null || id==""){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long l = Long.parseLong(id);
        BigManager bigManager = bigManagerMapper.queryBigManagerById(l);
        if(bigManager == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.succeess(bigManager);
    }




}
