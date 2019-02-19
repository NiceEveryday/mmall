package com.mmall.service.impl;

import com.mmall.common.*;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service("iUserService")
public class IUserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseContent<User> login(String username, String password) {
        int countAccount = userMapper.selecyByAccount(username);
        if(countAccount == 0){
            return ResponseContent.createByErrorWithMsg("用户名不存在");
        }
        User user = userMapper.selecyByNameAndPassword(username,MD5Util.MD5EncodeUtf8(password));
        if(user == null){
            return ResponseContent.createByErrorWithMsg("密码不匹配");
        }
        user.setPassword("");
        return ResponseContent.createBySuccessWithMD("登录成功",user);
    }


    @Override
    public ResponseContent<String> checkValid(String infomation, int flag) {
        if(StringUtils.isBlank(infomation)){
            return ResponseContent.createByErrorWithMsg("参数为空");
        }
        int count = 0;
        if(flag == FlagInfomation.USERNAME.getCode()){
            count = userMapper.selecyByAccount(infomation);
        }else{
            count = userMapper.selecyByAccount(infomation);
        }
        if(count > 0){
            return  ResponseContent.createByErrorWithMsg("用户名或email已存在");
        }

        return ResponseContent.createBySuccess();
    }

    @Override
    public ResponseContent<String> register(User user) {
        if(user == null){
            return ResponseContent.createByErrorWithMsg("注册的信息为空");
        }
        ResponseContent<String> responseContentUsername = this.checkValid(user.getUsername(),FlagInfomation.USERNAME.getCode());
        if(!responseContentUsername.isSucess()){
            return ResponseContent.createByErrorWithMsg("用户名已存在");
        }
        ResponseContent<String> responseContentEmail = this.checkValid(user.getEmail(),FlagInfomation.EMAIL.getCode());
        if(!responseContentEmail.isSucess()){
            return ResponseContent.createByErrorWithMsg("email已存在");
        }
        //todo
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        userMapper.insert(user);
        return ResponseContent.createBySuccessWithMsg("注册成功");
    }

    @Override
    public ResponseContent<String> getQuestionByUsername(String username) {
        if(StringUtils.isBlank(username)){
            return ResponseContent.createByErrorWithMsg("用户名为空");
        }
        ResponseContent responseContent = this.checkValid(username, FlagInfomation.USERNAME.getCode());
        if(responseContent.isSucess()){
            return ResponseContent.createByErrorWithMsg("用户名不存在");
        }
        String question = userMapper.getQuestionByUsername(username);
        if(StringUtils.isBlank(question)){
            return ResponseContent.createByErrorWithMsg("提示问题不存在");
        }

        return ResponseContent.createBySuccessWithData(question);
    }

    @Override
    public ResponseContent<String> checkAnswer(String username, String question, String answer) {
        ResponseContent responseContent = checkValid(username,FlagInfomation.USERNAME.getCode());
        if(responseContent.isSucess()){
            return ResponseContent.createByErrorWithMsg("用户名不存在");
        }
        int count = userMapper.checkAnswer(username,question,answer);
        if(count <= 0){
            return ResponseContent.createByErrorWithMsg("答案和问题不匹配,请检查");
        }
        //放回token
        String token = UUID.randomUUID().toString();
        TokenCache.setToken(TokenCache.TOKEN_PREFIX+username, token);
        return ResponseContent.createBySuccessWithData(token);
    }

    @Override
    public ResponseContent<String> reset_password(String username, String password, String passwordNew,String token) {
        ResponseContent responseContent = checkValid(username,FlagInfomation.USERNAME.getCode());
        if(responseContent.isSucess()){
            return ResponseContent.createByErrorWithMsg("用户名不存在");
        }

        String tokenServer = TokenCache.getToken(TokenCache.TOKEN_PREFIX+username);
        if(!StringUtils.equals(token,tokenServer)){
            return ResponseContent.createByErrorWithMsg("token错误，请重新获取密保问题");
        }
        User user = userMapper.selecyByNameAndPassword(username,MD5Util.MD5EncodeUtf8(password));
        if(user == null){
            return ResponseContent.createByErrorWithMsg("密码不匹配");
        }
        int countRow = userMapper.resetPassword(username,MD5Util.MD5EncodeUtf8(passwordNew));
        if(countRow > 0){
            return ResponseContent.createBySuccessWithMsg("修改密码成功");
        }
        return ResponseContent.createByErrorWithMsg("修改密码失败");
    }

    @Override
    public ResponseContent<String> reset_password_loginin(User user, String passwordNew) {
        if(user == null){
            return ResponseContent.createByErrorWithMsg("用户不存在");
        }
        User userDB = userMapper.selectByPrimaryKey(user.getId());
        if(userDB == null){
            return ResponseContent.createByErrorWithMsg("用户不存在");
        }
        User userNew = userMapper.selecyByNameAndPassword(userDB.getUsername(),userDB.getPassword());
        if(userNew == null){
            return ResponseContent.createByErrorWithMsg("密码不匹配");
        }
        userNew.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateRow = userMapper.updateByPrimaryKeySelective(userNew);
        if(updateRow > 0){
            return ResponseContent.createBySuccessWithMsg("密码修改成功");
        }
        return ResponseContent.createByErrorWithMsg("密码修改失败");
    }

    @Override
    public ResponseContent<User> update_information(User user) {
        if(user == null){
            return ResponseContent.createByErrorWithMsg("用户不存在");
        }
        int countRow = userMapper.checkEmail(user.getId(),user.getEmail());
        if(countRow > 0){
            return ResponseContent.createByErrorWithMsg("email已存在");
        }
        User userUpdate = new User();
        userUpdate.setId((user.getId()));
        userUpdate.setQuestion(user.getQuestion());
        userUpdate.setAnswer(user.getAnswer());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPhone(user.getPhone());
        userUpdate.setRole(user.getRole());
        int countRowUpdate =  userMapper.updateByPrimaryKeySelective(userUpdate);
        if(countRowUpdate > 0){
            User userReturn = userMapper.selectByPrimaryKey(user.getId());
            userReturn.setPassword("");
            return  ResponseContent.createBySuccessWithMD("用户信息更新成功",userReturn);
        }
        return ResponseContent.createByErrorWithMsg("用户信息更新失败");
    }
}
