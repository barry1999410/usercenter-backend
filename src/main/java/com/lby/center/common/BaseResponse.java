package com.lby.center.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -2649390111774761428L;
    private Integer code;

    private T data;

    private String msg;

    private String description;

    public BaseResponse(Integer code, T data, String msg, String description) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.description=description;
    }

    public BaseResponse(Integer code, T data, String msg) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public BaseResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMsg(), errorCode.getDescription());
    }
}
