package com.mmall.common;

public enum Const {
    CURRENT_USER("current_user");
    private String name;
    Const(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
