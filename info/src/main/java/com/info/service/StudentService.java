package com.info.service;

import com.info.advice.PageResult;
import com.info.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentService {
    //分页以及过滤查询学生信息
    PageResult<Student> findAll(Integer page, Integer size, String keyword);

    //修改用户的状态
    void stateSwitch(@Param("flag") String flag, @Param("uid") Long uid);

    //根据id删除学生用户
    void deleteById(Long sid);
}
