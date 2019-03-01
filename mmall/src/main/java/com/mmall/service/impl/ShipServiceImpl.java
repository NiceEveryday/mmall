package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.ShipService;
import com.mmall.vo.ShippingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("shipService")
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShippingMapper shippingMapper;
    public ResponseContent<Map> add(Integer userId, Shipping shipping){
        if(userId == null || shipping == null){
            return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
        }
        shipping.setUserId(userId);
        int countRow = shippingMapper.insert(shipping);
        if(countRow == 0){
            return ResponseContent.createByErrorWithMsg("内部错误请重试");
        }
        Map map = new HashMap();
        map.put("id",shipping.getId());
        return ResponseContent.createBySuccessWithData(map);
    }

    public ResponseContent<String> delete(Integer userId, Integer shipId){
        if(userId == null || shipId == null){
            return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
        }
        int countRow = shippingMapper.deleyeByUserIdAndId(userId,shipId);
        if(countRow == 0){
            return ResponseContent.createByErrorWithMsg("内部错误请重试");
        }
        return ResponseContent.createBySuccessWithMsg("删除成功");
    }

    public ResponseContent<String> update(Integer userId, Shipping shipping){
        if(userId == null || shipping == null){
            return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
        }
        shipping.setUserId(userId);
        int countRow = shippingMapper.updateByUserIdAndId(shipping);
        if(countRow == 0){
            return ResponseContent.createByErrorWithMsg("内部错误请重试");
        }
        return ResponseContent.createBySuccessWithMsg("更新成功");
    }

    public ResponseContent<ShippingVO> detail(Integer userId, Integer shipId){
        if(userId == null || shipId == null){
            return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
        }
        Shipping shipping = shippingMapper.detailByUserIdAndId(userId,shipId);
        if(shipping == null){
            return ResponseContent.createByErrorWithMsg("内部错误请重试");
        }
        return ResponseContent.createBySuccessWithData(shippingToshippingVO(shipping));
    }

    public ResponseContent<PageInfo> list(Integer userId,Integer pageNum,Integer pageSize){
        if(userId == null  || pageNum == null || pageSize == null){
            return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        if(CollectionUtils.isEmpty(shippingList)){
            return ResponseContent.createByErrorWithMsg("内部错误请重试");
        }
        List<ShippingVO> shippingVOList = new ArrayList<>();
        for(Shipping shipping:shippingList){
               shippingVOList.add(shippingToshippingVO(shipping));
        }
        PageInfo pageInfo = new PageInfo(shippingVOList);
        return ResponseContent.createBySuccessWithData(pageInfo);
    }

    private ShippingVO shippingToshippingVO(Shipping shipping){
        if(shipping == null){
            return null;
        }
        ShippingVO shippingVO = new ShippingVO();
        shippingVO.setId(shipping.getId());
        shippingVO.setReceiverName(shipping.getReceiverName());
        shippingVO.setReceiverPhone(shipping.getReceiverPhone());
        shippingVO.setReceiverMobile(shipping.getReceiverMobile());
        shippingVO.setReceiverProvince(shipping.getReceiverProvince());
        shippingVO.setReceiverCity(shipping.getReceiverCity());
        shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVO.setReceiverAddress(shipping.getReceiverAddress());
        return shippingVO;
    }




}
