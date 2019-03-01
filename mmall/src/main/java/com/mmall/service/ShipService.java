package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseContent;
import com.mmall.pojo.Shipping;
import com.mmall.vo.ShippingVO;

import java.util.Map;

public interface ShipService {

    ResponseContent<Map> add(Integer userId, Shipping shipping);
    ResponseContent<String> delete(Integer userId, Integer shipId);
    ResponseContent<String> update(Integer userId, Shipping shipping);
    ResponseContent<ShippingVO> detail(Integer userId, Integer shipId);
    ResponseContent<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}
