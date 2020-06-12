package com.info.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.advice.InfoException;
import com.info.advice.PageResult;
import com.info.entity.*;
import com.info.enums.ExceptionEnum;
import com.info.mapper.*;
import com.info.service.UserService;
import com.info.utils.CodecUtils;
import com.info.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.beans.Transient;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ClassesMapper classesMapper;


    /**
     * 注册用户
     *
     * @param user
     */
    @Override
    @Transactional
    public void saveUser(User user) {
        user.setCreateTime(DateUtils.getCurrentTime()); //保存创建时间
        user.setState(1); //保存用户状态
        //取出密码
        String password = user.getPassword();
        //进行加密
        String newPassword = CodecUtils.passwordBCryptEncode(password);
        //重新保存
        user.setPassword(newPassword);
        if (user.getRoleFlag() == 1) {

            //查询当前班级辅导员
            Classes classes2=classesMapper.findStudentByStudent(user.getClassName());
            //学生
            Student student = new Student();
            BeanUtils.copyProperties(user, student);
            student.setTid(classes2.getTid());
            student.setCid(classes2.getId());
            int i = studentMapper.insert(student);
            if (i != 1) {
                throw new InfoException(ExceptionEnum.USER_SAVE_ERROR);
            }
            //学生身份 保存
            saveRoleByUser(student.getId(), "STUDENT");

        }
        if (user.getRoleFlag() == 0) {
            //老师
            Teacher teacher = new Teacher();
            BeanUtils.copyProperties(user, teacher);
            int i = teacherMapper.insert(teacher);
            if (i != 1) {
                throw new InfoException(ExceptionEnum.USER_SAVE_ERROR);
            }
            //教师身份
            saveRoleByUser(teacher.getId(), "TEACHER");
            //保存班级
            Classes classes = new Classes();
            classes.setId(System.currentTimeMillis());
            classes.setClassName(user.getClassName());
            classes.setTid(teacher.getId());
            classesMapper.insert(classes);
        }
    }

  /**
     * 查询用户的个人信息
     * @param id
     * @return
     */
    @Override
    public User getUserInfo(Long id,String roleName) {

        if(roleName.equals("STUDENT"))
        {
            //学生身份
            Student student = studentMapper.selectByPrimaryKey(id);
            if(student==null)
            {
                throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
            }
            User user = new User();
            BeanUtils.copyProperties(student,user);
            return user;

        }
        if(roleName.equals("TEACHER"))
        {
            //教师身份
            Teacher teacher = teacherMapper.selectByPrimaryKey(id);
            if(teacher==null)
            {
                throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
            }
            User user = new User();
            BeanUtils.copyProperties(teacher,user);
            return user;
        }
        if(roleName.equals("ADMIN"))
        {
            //管理员
            User user = userMapper.selectByPrimaryKey(id);
            if(user==null)
            {
                throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
            }
            return user;
        }
        return null;
    }

    @Override
    public void updateUser(User user,String roleName) {

        if(roleName.equals("STUDENT"))
        {
            Student student=new Student();
            BeanUtils.copyProperties(user,student);
            int i = studentMapper.updateByPrimaryKeySelective(student);
            if(i!=1)
            {
                throw new InfoException(ExceptionEnum.UPDATE_USER_ERROR);
            }
        }
        if(roleName.equals("TEACHER"))
        {
            Teacher teacher=new Teacher();
            BeanUtils.copyProperties(user,teacher);
            int i = teacherMapper.updateByPrimaryKeySelective(teacher);
            if(i!=1)
            {
                throw new InfoException(ExceptionEnum.UPDATE_USER_ERROR);
            }
        }
        if(roleName.equals("ADMIN"))
        {
            int i = userMapper.updateByPrimaryKeySelective(user);
            if(i!=1)
            {
                throw new InfoException(ExceptionEnum.UPDATE_USER_ERROR);
            }
        }

    }




    @Override
    public User findUserById(Long uid, Integer roleFlag) {
        Student student = studentMapper.selectByPrimaryKey(uid);
        Teacher teacher = teacherMapper.selectByPrimaryKey(uid);
        if (teacher == null && student == null) {
            throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
        }
        User user = new User();
        user.setId(System.currentTimeMillis());
        return user;
    }

    /*

        @Override
        @Transactional
        public void deleteById(Long uid) {
            //删除中间表
            userMapper.deleteUserOrRole(uid);
            //删除用户
            int i = userMapper.deleteByPrimaryKey(uid);
            if(i!=1)
            {
                throw new InfoException(ExceptionEnum.DELETE_USER_ERROR);
            }

        }
    */
    private void saveRoleByUser(Long uid, String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        Role one = roleMapper.selectOne(role);
        if (one == null) {
            throw new InfoException(ExceptionEnum.ROLE_NOT_FOUND);
        }
        //对中间表进行保存
        userMapper.saveUserAndRole(uid, one.getId());
    }
}
