package com.mmall.common;

public enum ReturnCode {
    SUCCESS(0,"success"),
    LOGIN(10,"login"),
    ERROR(20,"error"),
    PARAM_ERROR(2,"param_error");


    private int code;
    private String desc;

    ReturnCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
