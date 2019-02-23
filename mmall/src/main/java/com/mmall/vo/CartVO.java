package com.mmall.vo;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartVO {

    private List<CartProductVO> list = new ArrayList<>();
    private BigDecimal totalPrice = new BigDecimal("0");
    private boolean allChecked;
    private String hostName;
    private String overLimit;

    public List<CartProductVO> getList() {
        return list;
    }

    public void setList(List<CartProductVO> list) {
        this.list = list;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getOverLimit() {
        return overLimit;
    }

    public void setOverLimit(String overLimit) {
        this.overLimit = overLimit;
    }
}
