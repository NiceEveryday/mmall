package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.common.Role;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/backend/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> save(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能新增商品");
        }
        return  iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping(value = "update_product_status.do",method = RequestMethod.POST)
    @ResponseBody
    
    public ResponseContent<String> update_product_status(HttpSession session, int productId,int status){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能更新商品状态");
        }
        return  iProductService.updateProductStatus(productId,status);
    }
}
