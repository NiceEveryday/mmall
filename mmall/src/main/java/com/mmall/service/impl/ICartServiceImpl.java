package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.dao.CartMapper;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVO;
import com.mmall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("iCartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

     @Transactional
     public ResponseContent<CartVO> add(Integer productId,Integer count,Integer userId){
         if(productId == null || count == null || userId == null){
             return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
         }
         Cart cart = cartMapper.selectByProductIdAndUserId(productId,userId);
         if(cart == null){
             //商品没有被放入购物车
             Cart cartTmp = new Cart();
             cartTmp.setUserId(userId);
             cartTmp.setProductId(productId);
             cartTmp.setQuantity(count);
             cartTmp.setChecked(1);
             int countRow = cartMapper.insert(cartTmp);
             if(countRow == 0){
                 return ResponseContent.createByErrorWithMsg("服务器内部错误,请重试");
             }
         }else {
             Cart cartTmp = new Cart();
             cartTmp.setQuantity(cart.getQuantity() + count);
             cartTmp.setId(cart.getId());
             cartMapper.updateByPrimaryKeySelective(cartTmp);
         }
         return this.checkAndGetData(userId);
     }

     private ResponseContent<CartVO> checkAndGetData(Integer userId){
         if(userId == null){
             return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
         }
         CartVO cartVO = new CartVO();
         cartVO.setHostName(PropertiesUtil.getProperty("ftp.server.http.prefix"));
         cartVO.setAllChecked(true);
         cartVO.setOverLimit(Const.CartVO.IN_LIMIT);
         List<Cart> carts = cartMapper.selectByUserId(userId);
         for(Cart cart : carts){
             Product product = productMapper.selectByPrimaryKey(cart.getProductId());
             if(product == null){
                 return ResponseContent.createByErrorWithMsg("服务器内部错误,请重试");
             }
             CartProductVO cartToCartProductVO;
             if(product.getStock() >= cart.getQuantity()){
                 cartToCartProductVO = cartToCartProductVO(cart,product);
             }else{
                 cartToCartProductVO = cartToCartProductVO(cart,product);
                 cartToCartProductVO.setQuantity(product.getStock());
                 cartVO.setOverLimit(Const.CartVO.OUT_LIMIT);
                 Cart cartTmp = new Cart();
                 cartTmp.setId(cart.getId());
                 cartTmp.setQuantity(product.getStock());
                 int countRow = cartMapper.updateByPrimaryKeySelective(cartTmp);
                 if(countRow == 0){
                     return ResponseContent.createByErrorWithMsg("服务器内部错误,请重试");
                 }
             }
             if(cartToCartProductVO.getChecked() == Const.ProductChecked.Checked){
                   BigDecimal productPrice = BigDecimalUtil.mul(product.getPrice().doubleValue(),cartToCartProductVO.getQuantity().doubleValue());
                   cartVO.setTotalPrice(BigDecimalUtil.add(cartVO.getTotalPrice().doubleValue(),productPrice.doubleValue()));
             }
             if(cartToCartProductVO.getChecked() == Const.ProductChecked.UnChecked){
                  cartVO.setAllChecked(false);
             }
             cartVO.getList().add(cartToCartProductVO);
         }
         return ResponseContent.createBySuccessWithData(cartVO);
     }

     private CartProductVO cartToCartProductVO(Cart cart,Product product){
         if(cart == null || product == null){
             return null;
         }
         CartProductVO cartProductVO = new CartProductVO();
         cartProductVO.setProductId(product.getId());
         cartProductVO.setQuantity(cart.getQuantity());
         cartProductVO.setChecked(cart.getChecked());
         cartProductVO.setName(product.getName());
         cartProductVO.setSubtitle(product.getSubtitle());
         cartProductVO.setMainImage(product.getMainImage());
         cartProductVO.setPrice(product.getPrice());
         cartProductVO.setStatus(product.getStatus());
         return cartProductVO;
     }

     @Transactional
     public ResponseContent<CartVO> updateCart(Integer productId,Integer count,Integer userId){
         if(productId == null || count == null || userId == null){
             return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
         }
         Cart cart = cartMapper.selectByProductIdAndUserId(productId,userId);
         if(cart != null){
             cart.setQuantity(count);
             cartMapper.updateByPrimaryKeySelective(cart);
         }

         return checkAndGetData(userId);
     }

    @Transactional
    public ResponseContent<CartVO> deleteCart(String productIds,Integer userId){
        if(productIds == null || userId == null){
            return ResponseContent.createByErrorWithCM(ReturnCode.PARAM_ERROR.getCode(),ReturnCode.PARAM_ERROR.getDesc());
        }
        List<String> stringList = Splitter.on(",").splitToList(productIds);
        cartMapper.deleteByProductIdsAnduserId(stringList,userId);
        return checkAndGetData(userId);
    }

    public ResponseContent<CartVO> list(Integer userId){
        return checkAndGetData(userId);
    }


    public ResponseContent<CartVO> setCheckedByUserIdAndProductId(Integer userId,Integer productId,Integer checked){
        cartMapper.setCheckedByUserIdAndProductId(userId,productId,checked);
        return checkAndGetData(userId);
    }


}
