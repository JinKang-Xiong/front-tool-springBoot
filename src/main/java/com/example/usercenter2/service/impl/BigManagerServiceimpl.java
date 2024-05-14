package com.example.usercenter2.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter2.common.ErrorCode;
import com.example.usercenter2.constanr.UserConstant;
import com.example.usercenter2.exception.BusinessException;
import com.example.usercenter2.mapper.BigManagerMapper;
import com.example.usercenter2.model.BigManager;
import com.example.usercenter2.service.BigManagerService;
import com.example.usercenter2.vo.BigManagerVo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 大管理员的服务类
 */
@Service
public class BigManagerServiceimpl extends ServiceImpl<BigManagerMapper,BigManager> implements BigManagerService {
        @Resource
        BigManagerMapper bigManagerMapper;

        /**
         * 定义加盐数
         */
        private static final String SALT = "jkman";


        /**
         * 登录大管理员——密码没有加密
         * @param bigManagerAccount
         * @param bigManagerPassword
         * @return
         */
        @Override
        public BigManager loginBigManager(String bigManagerAccount, String bigManagerPassword) {
                if(bigManagerAccount ==null || bigManagerPassword == null){
                        throw  new BusinessException(ErrorCode.NULL_ERROR);
                }

                if(bigManagerAccount.length() < 6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"长度小于6");
                }
                if(bigManagerPassword.length() < 6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"长度小于6");
                }
                String encyBigManagerPassword1 = DigestUtils.md5DigestAsHex((SALT + bigManagerPassword).getBytes());
                QueryWrapper<BigManager> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("big_manager_account",bigManagerAccount);
                queryWrapper.eq("big_manager_password",encyBigManagerPassword1);
                queryWrapper.eq("status_delete",0);
                BigManager bigManager = bigManagerMapper.selectOne(queryWrapper);
                if(bigManager == null){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }

                BigManager bigManager1 = handerBigManager(bigManager);

                return bigManager1;
        }

        /**
         * 获取用户的登录态
         * @param id
         * @return
         */
        @Override
        public BigManager queryById(Long id) {
                BigManager bigManager = bigManagerMapper.selectById(id);
                BigManager bigManager1 = handerBigManager(bigManager);
                return bigManager1;
        }

        /**
         * 修改头像
         * @param id
         * @param newFileName
         * @return
         */
        @Override
        public Integer updateAvatar(Long id, String newFileName) {
                Integer integer = bigManagerMapper.updateAvatarUrl(id, newFileName);
                return  integer;
        }

        @Override
        public Integer updateBasic(Long id, String bigManagerName, Integer age, Byte gender) {
                if(bigManagerName ==null || bigManagerName.isEmpty()){
                        throw new BusinessException(ErrorCode.NULL_ERROR);
                }
                if(age <0 || age>120){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"年龄过大和过小");
                }
                if(gender != UserConstant.WOMAN && gender!=UserConstant.MAN){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                BigManager bigManager = new BigManager(id, bigManagerName, gender, age);
                Integer integer = bigManagerMapper.updateBasic(bigManager);
                if(integer <=0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }

                return integer;
        }

        @Override
        public Integer updatePassword(Long id, String oldPassword, String newPasswrd,String checknewPassword) {
                if(oldPassword.length() <6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"原密码小于6");
                }
                if(newPasswrd.length() <6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"新密码长度小于6");
                }
                if(!newPasswrd.equals(checknewPassword)){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不相等");
                }
                String encyBigManagerPassword1 = DigestUtils.md5DigestAsHex((SALT + oldPassword).getBytes());
                String encyBigManagerPassword2 = DigestUtils.md5DigestAsHex((SALT + newPasswrd).getBytes());
                Integer integer = bigManagerMapper.updatePasswordById(id, encyBigManagerPassword1, encyBigManagerPassword2);
                if(integer <=0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                return integer;


        }

        @Override
        public Integer addBigManager(BigManager bigManager) {
                String bigManagerName = bigManager.getBigManagerName();
                String bigManagerAccount = bigManager.getBigManagerAccount();
                Byte gender = bigManager.getGender();
                Integer age = bigManager.getAge();
                String bigManagerPassword = bigManager.getBigManagerPassword();
                String email = bigManager.getEmail();
                String phone = bigManager.getPhone();
                Boolean aBoolean = checkIntoBigManager(bigManagerName, bigManagerAccount, bigManagerPassword, email, phone, age, gender);
                if(!aBoolean){
                     throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                //密码加密
                String encyBigManagerPassword = DigestUtils.md5DigestAsHex((SALT + bigManagerPassword).getBytes());
                BigManager bigManager1 = new BigManager();
                bigManager1.setBigManagerName(bigManagerName);
                bigManager1.setBigManagerAccount(bigManagerAccount);
                bigManager1.setAvatarUrl("https://images.pexels.com/photos/1548110/pexels-photo-1548110.jpeg?auto=compress&cs=tinysrgb&w=600");
                bigManager1.setBigManagerPassword(encyBigManagerPassword);
                bigManager1.setGender(gender);
                bigManager1.setAge(age);
                bigManager1.setEmail(email);
                bigManager1.setPhone(phone);
                Integer integer = bigManagerMapper.insertBigManager(bigManager1);
                if(integer <=0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                return integer;

        }


        @Override
        public IPage<BigManagerVo> searchBigManager(Page<BigManagerVo> bigManagerVoPage, BigManager bigManager) {
                IPage<BigManagerVo> bigManagerVoIPage = bigManagerMapper.searchBigManager(bigManagerVoPage, bigManager);
                System.out.println(bigManagerVoIPage);
                return bigManagerVoIPage;
        }


        @Override
        public Integer updateBigManager(BigManager bigManager) {
                Long id = bigManager.getId();
                String bigManagerName = bigManager.getBigManagerName();
                String bigManagerAccount = bigManager.getBigManagerAccount();
                Byte gender = bigManager.getGender();
                Integer age = bigManager.getAge();
                String email = bigManager.getEmail();
                String phone = bigManager.getPhone();
                Byte avatarStatus = bigManager.getAvatarStatus();
                if(bigManagerName ==null || bigManagerAccount==null || email==null ||phone==null){
                        throw new BusinessException(ErrorCode.NULL_ERROR);
                }
                if(bigManagerAccount.length() <6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度小于6");

                }
                //邮箱格式要匹配正确
                String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
                Pattern compile = Pattern.compile(emailRegex);
                Matcher matcher = compile.matcher(email);
                if(!matcher.matches()){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱格式不匹配");

                }

                //验证手机号的格式
                String phoneRegex = "^1[3-9]\\d{9}$";
                Pattern compile1 = Pattern.compile(phoneRegex);
                Matcher matcher1 = compile1.matcher(phone);
                if(!matcher1.matches()){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"手机格式不匹配");

                }

                if(age<0 || age>120){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"年龄过大或者过小");

                }
                if(gender !=UserConstant.MAN && gender !=UserConstant.WOMAN) {
                        throw new BusinessException(ErrorCode.PARAMS_ERROR, "性别不符合");

                }
                if(avatarStatus !=UserConstant.EXIST && avatarStatus != UserConstant.VANISH){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }

                BigManager bigManager1 = new BigManager();
                bigManager1.setId(id);
                bigManager1.setBigManagerName(bigManagerName);
                bigManager1.setBigManagerAccount(bigManagerAccount);
                bigManager1.setAvatarStatus(avatarStatus);
                bigManager1.setGender(gender);
                bigManager1.setAge(age);
                bigManager1.setEmail(email);
                bigManager1.setPhone(phone);

                Integer integer = bigManagerMapper.updateBigManagerById(bigManager1);
                if(integer <=0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                return integer;

        }


        @Override
        public Integer deleteBigManager(Long id, Byte statusDelete) {
                if(statusDelete != UserConstant.EXIST && statusDelete != UserConstant.VANISH){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                Integer integer = bigManagerMapper.deleteBigManagerById(id, statusDelete);
                if(integer <=0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }

                return integer;
        }

        @Override
        public Integer UpdatePasswordByIdAndPhone(String id, String newPassword, String checkNewPassword) {
                long l = Long.parseLong(id);
                if(newPassword.length() <6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"新密码长度小于6");
                }
                if(!newPassword.equals(checkNewPassword)){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不相等");
                }

                String encyBigManagerPassword = DigestUtils.md5DigestAsHex((SALT + newPassword).getBytes());

                Integer integer = bigManagerMapper.UpdatePasswordByIdAndPhone(l, encyBigManagerPassword);
                if(integer <=0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }

                return integer;

        }

        @Override
        public Integer updateEmailByIdAndEmail(String id, String email) {
                //邮箱格式要匹配正确
                String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
                Pattern compile = Pattern.compile(emailRegex);
                Matcher matcher = compile.matcher(email);
                if(!matcher.matches()){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱格式不匹配");

                }

                long l = Long.parseLong(id);
                Integer integer = bigManagerMapper.updateEmailById(l, email);
                if(integer<=0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }

                return integer;
        }

        @Override
        public Integer updatePhone(Long id, String phone) {
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
                Integer integer = bigManagerMapper.updatePhone(id, phone);
                if(integer<0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                return integer;
        }

        @Override
        public Integer updatePasswordByEmail(String email, String password, String checkPassword, String verify, HttpServletRequest request) {
                if(email==null||email==""||password==null||password==""||verify==null||verify=="") {
                        throw new BusinessException(ErrorCode.NULL_ERROR);
                }
                if(!password.equals(checkPassword)){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不一致");
                }
                HttpSession session = request.getSession();
                String verifyforget = (String) session.getAttribute("verifyforgetbig");
                if(!verifyforget.equals(verify)){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码不匹配");
                }
                //密码进行加密
                String encryptpassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
                Integer integer = bigManagerMapper.updatePasswordByEmail(email, encryptpassword);
                if(integer<0){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR);
                }
                return integer;
        }


        /**
         * 大管理员脱敏
         * @param bigManager
         * @return
         */
        public BigManager handerBigManager(BigManager bigManager){
                BigManager bigManager1 = new BigManager();
                bigManager1.setId(bigManager.getId());
                bigManager1.setBigManagerName(bigManager.getBigManagerName());
                bigManager1.setBigManagerAccount(bigManager.getBigManagerAccount());
                bigManager1.setAvatarUrl(bigManager.getAvatarUrl());
                bigManager1.setGender(bigManager.getGender());
                bigManager1.setAge(bigManager.getAge());
                bigManager1.setEmail(bigManager.getEmail());
                bigManager1.setPhone(bigManager.getPhone());
                bigManager1.setCreateTime(bigManager.getCreateTime());
                bigManager1.setUpdateTime(bigManager.getUpdateTime());
                bigManager1.setManageRole(bigManager.getManageRole());
                bigManager1.setAvatarStatus(bigManager.getAvatarStatus());
                return bigManager1;
        }


        /**
         * 对数据进行合理的验证
         * @param bigManagerName
         * @param bigManagerAccount
         * @param bigManagerPassword
         * @param email
         * @param phone
         * @param age
         * @param gender
         * @return
         */
        public Boolean checkIntoBigManager(String bigManagerName,String bigManagerAccount,String bigManagerPassword,String email,String phone,Integer age,Byte gender){
                if(bigManagerName ==null || bigManagerAccount==null||bigManagerPassword==null || email==null ||phone==null){
                        throw new BusinessException(ErrorCode.NULL_ERROR);
                }
                if(bigManagerAccount.length() <6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度小于6");

                }
                if(bigManagerPassword.length()<6){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度小于6");

                }
                //邮箱格式要匹配正确
                String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
                Pattern compile = Pattern.compile(emailRegex);
                Matcher matcher = compile.matcher(email);
                if(!matcher.matches()){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱格式不匹配");

                }

                //验证手机号的格式
                String phoneRegex = "^1[3-9]\\d{9}$";
                Pattern compile1 = Pattern.compile(phoneRegex);
                Matcher matcher1 = compile1.matcher(phone);
                if(!matcher1.matches()){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"手机格式不匹配");

                }

                if(age<0 || age>120){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"年龄过大或者过小");

                }
                if(gender !=UserConstant.MAN && gender !=UserConstant.WOMAN){
                        throw new BusinessException(ErrorCode.PARAMS_ERROR,"性别不符合");

                }

                return true;
        }
}
