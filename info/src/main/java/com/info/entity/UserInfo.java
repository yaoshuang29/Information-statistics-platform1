package com.info.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;


@Setter
@Getter
@NoArgsConstructor

public class UserInfo implements UserDetails {

    private Long id;  //id
    private String username; // 姓名
    private String password; //密码
    private Integer state; //状态
    private Role role; //角色信息


    public UserInfo(Long id,String username,String password,Integer state,Role role){
        this.id=id;
        this.username=username;
        this.password=password;
        this.state=state;
        this.role=role;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = null;
        //获取用户权限
        roleName = role.getRoleName();

        List<SimpleGrantedAuthority> grantAuthority = new ArrayList<SimpleGrantedAuthority>();

        grantAuthority.add(new SimpleGrantedAuthority("ROLE_" + roleName));

        return grantAuthority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(this.state==0)
        {
            return false;
        }
        return true;
    }
}
