package com.info.service.impl;

import com.info.entity.*;
import com.info.mapper.StudentMapper;
import com.info.mapper.TeacherMapper;
import com.info.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("查找到用户信息[]", username);
        //查询
        User info = userMapper.findById(Long.parseLong(username));
        UserInfo user =null;
        if (info != null) {
            user=new  UserInfo(info.getId(),info.getUsername(),info.getPassword(),info.getState(),info.getRole());
        }
        Student student = studentMapper.findById(Long.parseLong(username));
        if(student!=null)
        {
            user=new UserInfo(student.getId(),student.getUsername(),student.getPassword(),student.getState(),student.getRole());
        }
        Teacher teacher = teacherMapper.findById(Long.parseLong(username));
        if(teacher!=null)
        {
            user=new UserInfo(teacher.getId(),teacher.getUsername(),teacher.getPassword(),teacher.getState(),teacher.getRole());
        }
        return user;
    }

    /**
     * 授权管理
     *
     * @return
     */
    public List<SimpleGrantedAuthority> getAuthority(Role role) {
        String roleName = null;
        //获取用户权限
        roleName = role.getRoleName();

        List<SimpleGrantedAuthority> grantAuthority = new ArrayList<SimpleGrantedAuthority>();

        grantAuthority.add(new SimpleGrantedAuthority("ROLE_" + roleName));

        return grantAuthority;

    }
}
