package com.info.entity;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@Table(name = "tb_student")
public class Student {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username; // 姓名
    private String password; //密码
    private Integer state; //状态
    private String phone; //手机号
    private Integer gender; //性别
    private Long cardId; //身份证号
    private String createTime;//创建时间
    private Long tid; //教师id
    private Long cid; //班级id

    @Transient
    private Integer roleFlag; //角色标记

    @Transient
    private Role role;

    @Transient
    private Classes classes;

}
