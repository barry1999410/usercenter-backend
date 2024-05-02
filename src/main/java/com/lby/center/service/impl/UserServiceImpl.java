package com.lby.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lby.center.common.ErrorCode;
import com.lby.center.common.Exception.BusinessException;
import com.lby.center.mapper.UserMapper;
import com.lby.center.model.constants.UserConstant;
import com.lby.center.model.domain.User;
import com.lby.center.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.lby.center.model.constants.UserConstant.ADMIN_ROLE;
import static com.lby.center.model.constants.UserConstant.USER_LOGIN_STATE;

/**
* @author liu
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-04-25 19:17:47
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    UserMapper userMapper;
    // 混淆盐值
    private static final String salt = "liuboyang";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        // 非空校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户或密码为空！");
        }
        //账户校验 不小于4位
        if (userAccount.length() < 4){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"账户名长度过短");
        }
        // 密码不小于8位
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"密码长度过短");
        }
        if (planetCode.length()>5){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }
        // 账户不能包含特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }
        // 账户不能重复
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userAccount" , userAccount);
        long userCount = this.count(wrapper);
        if (userCount > 0){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"账户已存在");
        }
        // 星球编号不能重复
        wrapper = new QueryWrapper<>();
        wrapper.eq("planetCode" , planetCode);
        long planetCount = this.count(wrapper);
        if (planetCount > 0){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复！");
        }

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"密码和校验密码不相同");
        }

        // 加密
        String salt = "liuboyang";
        String handlePwd = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUsrPassword(handlePwd);
        user.setPlanetCode(planetCode);
        int i = userMapper.insert(user);
        return i;
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 账户密码是否合法 非空 账户，密码长度，特殊字符

        // 非空校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        //账户校验 不小于4位
        if (userAccount.length() < 4){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"账号长度请小于4位");
        }
        // 密码不小于8位
        if (userPassword.length() < 8 ){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"密码长度请小于于8位");
        }

        // 账户不能包含特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }
        // 加密
        String handlePwd = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
        // 查询用户是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserAccount,userAccount).eq(User::getUsrPassword,handlePwd);
        User user = userMapper.selectOne(wrapper);
        if (user == null){
           throw  new BusinessException(ErrorCode.NO_DATA_ERROR,"用户不存在！");
        }
        // 用户态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user);
        // 用户数据脱敏
        return this.filterRole(user);
    }


    @Override
    public List<User> userList(String userName , HttpServletRequest httpServletRequest) {
        if (!checkUserRole(httpServletRequest)){
            return new ArrayList<>();
        }
        //全查询 or username
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userName)){
            //模糊查询
            queryWrapper.like(User::getUsername,userName);
        }
        List<User> users = this.list(queryWrapper);
        return users.stream().map(user -> filterRole(user)).collect(Collectors.toList());
    }


    @Override
        public boolean userDelete(long id,HttpServletRequest httpServletRequest) {
        if (!checkUserRole(httpServletRequest)){
            return false;
        }
        if (id <= 0){
            return false;
        }
       return this.removeById(id);
    }

    /**
     * 用户信息脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User filterRole(User originUser) {
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setUsrPassword(originUser.getUsrPassword());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(new Date());
        safetyUser.setUpdateTime(new Date());
        safetyUser.setIsDelete(originUser.getIsDelete());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


    /**
     * 判断用户是否有权限crud
     */
    public boolean checkUserRole(HttpServletRequest request){
        // 获取用户登录态
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) attribute;
       return user!=null && user.getUserRole() == ADMIN_ROLE;
    }


}




