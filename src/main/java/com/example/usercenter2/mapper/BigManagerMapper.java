package com.example.usercenter2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.usercenter2.model.BigManager;
import com.example.usercenter2.model.User;
import com.example.usercenter2.vo.BigManagerVo;
import org.apache.ibatis.annotations.Param;

public interface BigManagerMapper extends BaseMapper<BigManager> {

    /**
     * 修改头像
     * @param id
     * @param newFileName
     * @return
     */
    Integer updateAvatarUrl(@Param("id") Long id, @Param("newFileName") String newFileName);


    /**
     * 修改用户的基础信息
     * @param bigManager
     * @return
     */
    Integer updateBasic(@Param("bigmanager") BigManager bigManager);


    /**
     * 修改用户密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Integer updatePasswordById(@Param("id") Long id,@Param("oldPassword") String oldPassword,@Param("newPassword") String newPassword);

    /**
     * 新增加管理员
     * @param bigManager
     * @return
     */
    Integer insertBigManager(@Param("bigManager") BigManager bigManager);

    /**
     * 分页查询
     * @param bigManagerIPage
     * @param bigManager
     * @return
     */
    IPage<BigManagerVo> searchBigManager(IPage<BigManagerVo> bigManagerIPage, @Param("bigManager") BigManager bigManager);

    /**
     * 修改管理员
     * @param bigManager
     * @return
     */
    Integer updateBigManagerById(@Param("bigManager") BigManager bigManager);


    /**
     * 注销用户
     * @param id
     * @param statusDelete
     * @return
     */
    Integer deleteBigManagerById(@Param("id") Long id,@Param("statusDelete") Byte statusDelete);

    /**
     * 查询管理员的数量
     * @return
     */
    Integer querycountBigManager();

    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    Integer UpdatePasswordByIdAndPhone(@Param("id") Long id,@Param("password") String password);

    /**
     * 修改邮箱
     * @param id
     * @param email
     * @return
     */
    Integer updateEmailById(@Param("id") Long id,@Param("email") String email);


    /**
     * 根据id查询管理员
     * @param id
     * @return
     */
    BigManager queryBigManagerById(@Param("id") Long id);

    /**
     * 修改手机号
     * @param id
     * @param phone
     * @return
     */
    Integer updatePhone(@Param("id") Long id,@Param("phone") String phone);


    /**
     * 根据邮箱修改密码
     * 忘记密码找回密码
     * @param email
     * @param password
     * @return
     */
    Integer updatePasswordByEmail(@Param("email") String email,@Param("password") String password);
}
