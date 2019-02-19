package com.mmall.controller.portal;

import com.mmall.common.*;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserCotroller {

    @Autowired
    private IUserService iUserService;

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<User> login(String username, String password, HttpSession session){
        ResponseContent responseContent = iUserService.login(username,password);
        if(responseContent.isSucess()){
              session.setAttribute(Const.CURRENT_USER.getName(),responseContent.getData());
        }
        return responseContent;
    }

    @RequestMapping(value = "loginOut.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<String> loginOut(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER.getName());
        return ResponseContent.createByErrorWithMsg("登出成功");
    }

    @RequestMapping(value = "checkValid.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<String> checkValid(String infomation,int flag){
        return iUserService.checkValid(infomation,flag);
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<User> get_user_info(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithMsg("用户未登陆");
        }
        return ResponseContent.createBySuccessWithData(user);
    }

    @RequestMapping(value = "get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<String> get_question(String username){
         return iUserService.getQuestionByUsername(username);
    }

    @RequestMapping(value = "get_answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<String> get_answer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> reset_password(String username,String password,String passwordNew,String  token){
        return iUserService.reset_password(username,password,passwordNew,token);
    }

    @RequestMapping(value = "reset_password_loginin.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<String> reset_password_loginin(HttpSession session,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithMsg("用户未登陆");
        }
        return iUserService.reset_password_loginin(user,passwordNew);
    }

    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ResponseContent<User> update_information(HttpSession session,User user){
        User userSession = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(userSession == null){
            return  ResponseContent.createByErrorWithMsg("用户未登陆");
        }
        user.setUsername(userSession.getUsername());
        user.setId(userSession.getId());
        user.setRole(userSession.getRole());
        ResponseContent<User> responseContent =  iUserService.update_information(user);
        if(responseContent.isSucess()){
            return  ResponseContent.createBySuccessWithMD("修改信息成功",responseContent.getData());
        }
        return responseContent;
    }

    @RequestMapping(value = "get_information.do",method = RequestMethod.GET)
    @ResponseBody
    public ResponseContent<User> get_information(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER.getName());
        if(user == null){
            return  ResponseContent.createByErrorWithCM(ReturnCode.LOGIN.getCode(),"用户未登陆");
        }
        return ResponseContent.createBySuccessWithData(user);
    }













}
