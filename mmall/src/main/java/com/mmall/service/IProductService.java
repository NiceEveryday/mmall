package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseContent;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVO;

public interface IProductService {
    ResponseContent<String> saveOrUpdateProduct(Product product);
    ResponseContent<String> updateProductStatus(int productId,int status);
    ResponseContent<ProductDetailVO> detail(Integer productId);
    ResponseContent<PageInfo> list(Integer pageNum, Integer pageSize);
    ResponseContent<PageInfo>  search(String productName,Integer productId,int pageNum,int pageSize);
}
