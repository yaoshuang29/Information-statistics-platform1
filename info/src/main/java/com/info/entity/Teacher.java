package com.info.entity;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@Table(name = "tb_teacher")
public class Teacher {

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

    @Transient
    private Integer roleFlag; //角色标记

    @Transient
    private Classes classes; //班级

    @Transient
    private Role role;

}
