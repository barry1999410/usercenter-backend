package com.lby.center.common;

public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse(200 , data , "success");
    }
    public static <T> BaseResponse<T> error(T data){
        return new BaseResponse(500 , data , "success");
    }

    public static  BaseResponse error(ErrorCode errorCode){
        return new BaseResponse(errorCode);
    }
    public static  BaseResponse error(int code,String message,String description){
        return new BaseResponse(code,null,message,description);
    }

    public static  BaseResponse error(ErrorCode errorCode,String message , String description){
        return new BaseResponse(errorCode.getCode(),null,message,description);
    }

}
