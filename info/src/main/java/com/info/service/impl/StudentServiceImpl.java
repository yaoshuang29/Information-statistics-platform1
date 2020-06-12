package com.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.advice.InfoException;
import com.info.advice.PageResult;
import com.info.entity.Student;
import com.info.enums.ExceptionEnum;
import com.info.mapper.StudentMapper;
import com.info.mapper.UserMapper;
import com.info.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult<Student> findAll(Integer page, Integer size, String keyword) {
        //分页
        PageHelper.startPage(page,size);

        if(StringUtils.isNotBlank(keyword))
        {
            keyword="%"+keyword+"%";
        }
        if(StringUtils.isBlank(keyword))
        {
            keyword="%";
        }
        //查询
        List<Student> userList= studentMapper.findStudent(keyword);
        if(CollectionUtils.isEmpty(userList))
        {
            return null;
        }
        PageInfo<Student> pageInfo=new PageInfo<Student>(userList);
        return new PageResult<Student>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
    }

    @Override
    @Transactional
    public void stateSwitch(String flag,Long uid) {
        studentMapper.stateSwitch(flag,uid);
    }

    @Override
    @Transactional
    public void deleteById(Long sid) {
        int i = studentMapper.deleteByPrimaryKey(sid);
        if(i!=1)
        {
            throw new InfoException(ExceptionEnum.DELETE_USER_ERROR);
        }
        //删除中间表信息
        userMapper.deleteUserOrRole(sid);
    }
}
