package com.mmall.common;

/**
 * 标记信息属于User对象的哪一个字段
 */
public enum FlagInfomation {
     USERNAME("username",1),
     EMAIL("email",2);

     FlagInfomation(String name,int code){
         this.code = code;
         this.name = name;
     }
    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
