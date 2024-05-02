package com.lby.center.common.Exception;

import com.lby.center.common.ErrorCode;

public class BusinessException extends  RuntimeException{

    private final int code;
    private final String descripiton;

    public int getCode() {
        return code;
    }

    public String getDescripiton() {
        return descripiton;
    }


    public BusinessException(String message, int code, String descripiton) {
        super(message);
        this.code = code;
        this.descripiton = descripiton;
    }


    public BusinessException(ErrorCode errorCode , String descripiton) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.descripiton = descripiton;
    }


    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.descripiton = errorCode.getDescription();
    }
}
