package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selecyByAccount(String username);

    int selecyByEmail(String email);

    User selecyByNameAndPassword(@Param("username") String username, @Param("password") String password);
    String getQuestionByUsername(String username);
    int checkAnswer(@Param("username") String username,@Param("question") String qustion,@Param("answer") String answer);
    int resetPassword(@Param("username") String username,@Param("passwordNew") String passwordNew);

    int checkEmail(@Param("id") int id,@Param("email") String email);

}