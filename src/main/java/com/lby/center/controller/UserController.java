package com.lby.center.controller;

import com.lby.center.common.BaseResponse;
import com.lby.center.common.ErrorCode;
import com.lby.center.common.Exception.BusinessException;
import com.lby.center.common.ResultUtils;
import com.lby.center.model.domain.User;
import com.lby.center.model.domain.request.UserLoginRequest;
import com.lby.center.model.domain.request.UserRegisterRequest;
import com.lby.center.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.lby.center.model.constants.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest registerRequest){
        if (registerRequest == null){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();
        String checkPassword = registerRequest.getCheckPassword();
        String planetCode = registerRequest.getPlanetCode();

        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode));

        long l = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);

        return ResultUtils.success(l);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest , HttpServletRequest httpServletRequest){
        if (userLoginRequest == null){
           throw new  BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUsrPassword();

        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new  BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, httpServletRequest);

        return ResultUtils.success(user) ;
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> userSearch(String userName , HttpServletRequest httpServletRequest){
        List<User> list = userService.userList(userName, httpServletRequest);
        return ResultUtils.success(list);
    }
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> userDelete(long id , HttpServletRequest httpServletRequest) {
        boolean b = userService.userDelete(id, httpServletRequest);
        return ResultUtils.success(b);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser  = (User) attribute;
        if (currentUser == null){
            throw new  BusinessException(ErrorCode.NO_LOGIN_ERROR,"未登录");
        }
        Long userId = currentUser.getId();
        //TODO 校验用户是否合法
        User user = userService.getById(userId);
        User filterRole = userService.filterRole(user);
        return ResultUtils.success(filterRole);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
      if (request == null){
          throw new  BusinessException(ErrorCode.PARAMS_ERROR);
      }
        int i = userService.userLogout(request);
        return  ResultUtils.success(i);
    }
}
