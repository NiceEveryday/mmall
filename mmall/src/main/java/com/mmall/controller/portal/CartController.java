package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/portal/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent add(Integer productId, Integer count, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.add(productId,count,user.getId());
    }

    @RequestMapping(value = "update_cart.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent updateCart(Integer productId, Integer count, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.updateCart(productId,count,user.getId());
    }

    @RequestMapping(value = "delete_cart.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent deleteCart(String productIds,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.deleteCart(productIds,user.getId());
    }

    @RequestMapping(value = "selected_check_all.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent selectedCheckAll(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.setCheckedByUserIdAndProductId(user.getId(),null,Const.ProductChecked.Checked);
    }

    @RequestMapping(value = "unselected_check_all.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent unSelectedCheckAll(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.setCheckedByUserIdAndProductId(user.getId(),null,Const.ProductChecked.UnChecked);
    }
    @RequestMapping(value = "checked_by_product.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent checkedByProduct(Integer productId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.setCheckedByUserIdAndProductId(user.getId(),productId,Const.ProductChecked.Checked);
    }

    @RequestMapping(value = "unchecked_by_product.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent uncheckedByProduct(Integer productId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.setCheckedByUserIdAndProductId(user.getId(),productId,Const.ProductChecked.UnChecked);
    }

    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent list(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return iCartService.list(user.getId());
    }


}
