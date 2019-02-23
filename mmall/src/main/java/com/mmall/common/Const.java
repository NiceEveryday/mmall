package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

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

    public interface ProductChecked{
        Integer Checked = 1;
        Integer  UnChecked = 0;
    }

    public interface CartVO{
        String IN_LIMIT = "in_limit";
        String  OUT_LIMIT = "out_limit";
    }
    public enum ProductStatus{
        ON_SALE(1,"on_sale"),
        OFF_SAKE(1,"off_sale");
        private int status;
        private String desc;

        ProductStatus(int status,String desc){
            this.status = status;
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public interface OrderBy{
          Set<String> setOrderBy = Sets.newHashSet("price_desc","price_asc");
    }
}
