package com.mmall.dao;

import com.mmall.pojo.Shipping;
import com.mmall.vo.ShippingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleyeByUserIdAndId(@Param("userId") Integer userId,@Param("id") Integer id);

    int updateByUserIdAndId(Shipping shipping);

    Shipping detailByUserIdAndId(@Param("userId") Integer userId,@Param("id") Integer id);

    List<Shipping> selectByUserId(Integer userId);


}