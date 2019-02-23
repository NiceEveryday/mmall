package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> list();
    List<Product> selectByProductIdAndProductName(@Param("productId") Integer productId,@Param("productName") String productName);
    List<Product>  selectByCategoryIdAndKeyword(@Param("keyword") String keyword,
                                                @Param("list") List<Integer> list,@Param("status") Integer status);
}