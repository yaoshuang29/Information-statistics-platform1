package com.info.service;

import com.info.advice.PageResult;
import com.info.entity.Teacher;

public interface TeacherService {
    //分页模糊查询教师信息
    PageResult<Teacher> findAll(Integer page, Integer size, String keyword);

    //切换用户状态
    void stateSwitch(String flag, Long uid);

    //根据id删除用户
    void deleteById(Long tid);
}
