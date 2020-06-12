package com.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.advice.InfoException;
import com.info.advice.PageResult;
import com.info.entity.Teacher;
import com.info.enums.ExceptionEnum;
import com.info.mapper.ClassesMapper;
import com.info.mapper.TeacherMapper;
import com.info.mapper.UserMapper;
import com.info.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.awt.event.KeyEvent;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClassesMapper classesMapper;


    @Override
    public PageResult<Teacher> findAll(Integer page, Integer size, String keyword) {
        //分页助手
        PageHelper.startPage(page,size);
        //对参数过滤
        Example example= new Example(Teacher.class);
        if(StringUtils.isEmpty(keyword)){
            keyword="%%";
        }
        else{
            keyword="%"+keyword+"%";
        }
        //查询
        List<Teacher> teachers = teacherMapper.findTeacherByKeyword(keyword);
        if(CollectionUtils.isEmpty(teachers)){
            throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
        }
        PageInfo<Teacher> info = new PageInfo<>(teachers);
        return new PageResult<Teacher>(info.getTotal(),info.getPages(),info.getList());
    }

    @Override
    public void stateSwitch(String flag, Long uid) {
        teacherMapper.stateSwitch(flag,uid);
    }

    @Override
    public void deleteById(Long tid) {

        //删除班级
        classesMapper.deleteByTid(tid);

        //删除用户
        int i = teacherMapper.deleteByPrimaryKey(tid);
        if(i!=1)
        {
            throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
        }
        //删除中间表
        userMapper.deleteUserOrRole(tid);
    }
}
