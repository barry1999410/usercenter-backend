package com.lby.center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 5314821761770246209L;
    /**
     * 用户账号
     */
    private String  userAccount;
    /**
     * 用户密码
     */
    private String  usrPassword;



}
