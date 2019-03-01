package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.ShipService;
import com.mmall.vo.ProductDetailVO;
import com.mmall.vo.ShippingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/portal/ship/")
public class ShipController {

    @Autowired
    private ShipService shipService;
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<Map> add(HttpSession session, Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return  shipService.add(user.getId(),shipping);
    }


    @RequestMapping(value = "delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> delete(HttpSession session, Integer shipId){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return  shipService.delete(user.getId(),shipId);
    }

    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> delete(HttpSession session, Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return  shipService.update(user.getId(),shipping);
    }

    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<ShippingVO> detail(HttpSession session, Integer shipId){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return  shipService.detail(user.getId(),shipId);
    }

    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<PageInfo> list(HttpSession session, Integer shipId,
                                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return  shipService.list(user.getId(),pageNum,pageSize);
    }
}
