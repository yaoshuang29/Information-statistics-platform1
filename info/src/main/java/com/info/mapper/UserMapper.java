package com.info.mapper;

import com.info.entity.User;
import com.info.entity.UserInfo;
import com.info.utils.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    //根据用户名查询用户
    User findById(Long  uid);
    //保存用户的角色信息
    void  saveUserAndRole(@Param("uid") Long uid,@Param("rid") Long rid);

    //根据用户id删除用户与角色表的中间值
    @Delete("delete from tb_user_role where uid=#{uid}")
    void deleteUserOrRole(Long uid);
}
