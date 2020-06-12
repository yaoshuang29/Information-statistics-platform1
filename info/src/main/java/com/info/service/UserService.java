package com.info.service;

import com.info.advice.PageResult;
import com.info.entity.User;
import com.info.entity.UserInfo;

import java.util.List;

public interface UserService {

    //保存
    void saveUser(User user);

    //查询
    User getUserInfo(Long id, String roleName);

    //修改用户信息
    void updateUser(User user,String roleName);

    //根据用户id查询用户
    User findUserById(Long uid, Integer roleFlag);
}
