package com.lby.center.service;

import com.lby.center.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author liu
* @description 针对表【user】的数据库操作Service
* @createDate 2024-04-25 19:17:47
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount , String userPassword, String checkPassword,String planetCode);

    /**
     *  用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount , String userPassword , HttpServletRequest request);

    /**
     * 用户查询
     * @return
     */
    List<User> userList(String userName , HttpServletRequest httpServletRequest);


    /**
     * 用户删除
     * @param id
     * @param httpServletRequest
     * @return
     */
    boolean userDelete(long id,HttpServletRequest httpServletRequest);

    /**
     * 用户信息脱敏
     * @param origin
     * @return
     */
    public User filterRole(User origin);

    /**
     * 用户注销
     * @param request
     * @return
     */
    public int userLogout(HttpServletRequest request);
}

