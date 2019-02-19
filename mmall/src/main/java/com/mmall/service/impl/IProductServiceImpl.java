package com.mmall.service.impl;

import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Service("iProductService")
public class IProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    public ResponseContent<String> saveOrUpdateProduct(Product product){
          if(product == null){
              return ResponseContent.createByErrorWithMsg(ReturnCode.PARAM_ERROR.getDesc());
          }
          if(product.getId() == null){
              int rowCount = productMapper.insert(product);
              if(rowCount > 0){
                  return ResponseContent.createBySuccessWithMsg("添加品类成功");
              }
              return ResponseContent.createByErrorWithMsg("添加品类失败，服务器内部错误请重试");
          }else{
              Product productDB = productMapper.selectByPrimaryKey(product.getId());
              if(productDB == null){
                  return ResponseContent.createByErrorWithMsg("更新的品类不存在");
              }
              int rowCount = productMapper.updateByPrimaryKeySelective(product);
              if(rowCount > 0){
                  return ResponseContent.createBySuccessWithMsg("更新品类成功");
              }
              return ResponseContent.createByErrorWithMsg("更新品类失败，服务器内部错误请重试");
          }
    }

    public ResponseContent<String> updateProductStatus(int productId,int status){
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ResponseContent.createByErrorWithMsg("商品不存在");
        }
        Product productUpdate = new Product();
        productUpdate.setId(product.getId());
        productUpdate.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(productUpdate);
        if(rowCount > 0){
            return ResponseContent.createBySuccessWithMsg("更新商品状态成功");
        }
        return ResponseContent.createByErrorWithMsg("更新商品状态失败，服务器内部错误请重试");

    }
}
