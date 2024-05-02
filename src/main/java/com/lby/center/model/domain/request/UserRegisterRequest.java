package com.lby.center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -6561720576350874738L;
    /**
     * 用户账号
     */
    private String  userAccount;
    /**
     * 用户密码
     */
    private String  userPassword;
    /**
     * 用户校验
     */
    private String  checkPassword;

    /**
     * 星球编号
     */
    private String planetCode;


}
