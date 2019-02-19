package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseContent;
import com.mmall.common.Role;
import com.mmall.common.TokenCache;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/backend/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<User> login(String username, String password, HttpSession session){
        ResponseContent responseContent = iUserService.login(username,password);
        if(responseContent.isSucess()){
            User user = (User) responseContent.getData();
            if(user == null){
                return ResponseContent.createByErrorWithMsg("服务器内部错误,请重试");
            }
            if(user.getRole() != Role.ADMIN.getCode()){
                return ResponseContent.createByErrorWithMsg("不是管理员不能登录");
            }
            user.setPassword("");
            session.setAttribute(Const.CURRENT_USER.getName(),user);
            return ResponseContent.createBySuccessWithMsg("登陆成功");
        }
        return responseContent;
    }

}
