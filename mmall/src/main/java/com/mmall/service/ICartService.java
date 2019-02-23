package com.mmall.service;

import com.mmall.common.ResponseContent;
import com.mmall.vo.CartVO;

public interface ICartService {
    ResponseContent<CartVO> add(Integer productId, Integer count, Integer userId);
    ResponseContent<CartVO> deleteCart(String productIds,Integer userId);
    ResponseContent<CartVO> updateCart(Integer productId,Integer count,Integer userId);
    ResponseContent<CartVO> list(Integer userId);
    public ResponseContent<CartVO> setCheckedByUserIdAndProductId(Integer userId,Integer productId,Integer checked);
}
