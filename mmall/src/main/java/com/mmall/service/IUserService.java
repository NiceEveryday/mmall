package com.mmall.service;

import com.mmall.common.ResponseContent;
import com.mmall.pojo.User;


public interface IUserService {
    ResponseContent<User> login(String username, String password);
    ResponseContent<String> checkValid(String infomation,int flag);
    ResponseContent<String> register(User user);
    ResponseContent<String> getQuestionByUsername(String username);
    ResponseContent<String>  checkAnswer(String username,String question,String answer);
    ResponseContent<String> reset_password(String username,String password,String passwordNew,String token);
    ResponseContent<String> reset_password_loginin(User user,String passwordNew);
    ResponseContent<User> update_information(User user);

}
