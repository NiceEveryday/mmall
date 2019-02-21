package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseContent;
import com.mmall.common.ReturnCode;
import com.mmall.common.Role;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICaregoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/backend/category")
public class CategoryController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICaregoryService iCaregoryService;

    @RequestMapping(value = "addCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> addCategory(@RequestParam(value = "parentId",defaultValue = "0") int parentId, String categoryName, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能添加品类");
        }
        return iCaregoryService.addCategory(categoryName,parentId);
    }

    @RequestMapping(value = "updateCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> updateCategory(int categoryId, String categoryName, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能修改品类");
        }
        return iCaregoryService.updateCategory(categoryName,categoryId);
    }

    @RequestMapping(value = "getParallelChildrenCategory.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<List<Category>> getParallelChildrenCategory(@RequestParam(value="parentId",defaultValue = "0") int parentId, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能搜索品类");
        }
        return iCaregoryService.getParallelChildrenCategory(parentId);
    }

    @RequestMapping(value = "getAllChildrenCategory.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<List<Category>> getAllChildrenCategory(@RequestParam(value="parentId",defaultValue = "0") int parentId,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        if(user.getRole() != Role.ADMIN.getCode()){
            return ResponseContent.createByErrorWithMsg("不是管理员不能获取品类");
        }
        return iCaregoryService.getAllChildrenCategory(parentId);
    }
}
