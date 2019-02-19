package com.mmall.common;

public enum Role {
    ADMIN(1,"admin"),
    USER(0,"USER");

    private int code;
    private String name;


    Role(int code,String name){
       this.code = code;
       this.name = name;
    }

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
