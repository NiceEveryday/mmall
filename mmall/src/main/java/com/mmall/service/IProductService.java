package com.mmall.service;

import com.mmall.common.ResponseContent;
import com.mmall.pojo.Product;

public interface IProductService {
    ResponseContent<String> saveOrUpdateProduct(Product product);
    ResponseContent<String> updateProductStatus(int productId,int status);
}
