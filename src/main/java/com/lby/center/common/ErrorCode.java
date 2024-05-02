package com.lby.center.common;

public enum ErrorCode {
    PARAMS_ERROR(40000,"请求参数错误！",""),
    NO_DATA_ERROR(40001,"请求数据为空！",""),
    NO_LOGIN_ERROR(40002,"无登录！",""),
    NO_AUTH_ERROR(40003,"无权限！",""),
    SYSTEM_ERROR(50000,"无权限！","");
    private final int code;

    private final String msg;

    private final String description;

    ErrorCode(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDescription() {
        return description;
    }
}
