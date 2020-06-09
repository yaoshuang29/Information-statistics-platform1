package com.info.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_role")
public class Role {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String roleName;  //角色名
    private String roleDesc; //角色权限

    public void setRoleName(String teacher) {
    }

    public void setRoleDesc(String 教师) {
    }

    public void setId(long currentTimeMillis) {
    }
}
