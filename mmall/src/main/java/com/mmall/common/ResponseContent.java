package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseContent<T> {
    private int status;
    private String msg;
    private T data;

    private ResponseContent(int status) {
        this.status = status;
    }

    private ResponseContent(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    private ResponseContent(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseContent<T> createBySuccess() {
        return new ResponseContent<T>(ReturnCode.SUCCESS.getCode());
    }

    public static <T> ResponseContent<T> createBySuccessWithMsg(String msg) {
        return new ResponseContent<T>(ReturnCode.SUCCESS.getCode(),msg);
    }

    public static <T> ResponseContent<T> createBySuccessWithData(T data) {
        return new ResponseContent<T>(ReturnCode.SUCCESS.getCode(),"",data);
    }

    public static <T> ResponseContent<T> createBySuccessWithMD(String msg, T data) {
        return new ResponseContent<T>(ReturnCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ResponseContent<T> createByErrorWithMsg(String msg) {
        return new ResponseContent<T>(ReturnCode.ERROR.getCode(),msg);
    }

    public static <T> ResponseContent<T> createByErrorWithData(String msg, T data) {
        return new ResponseContent<T>(ReturnCode.ERROR.getCode(),msg,data);
    }
    //指定Return,msg
    public static <T> ResponseContent<T> createByErrorWithCM(int code,String msg) {
        return new ResponseContent<T>(code,msg);
    }
    @JsonIgnore
    public boolean isSucess(){
        return this.status == ReturnCode.SUCCESS.getCode();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
