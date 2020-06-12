package com.info.service.impl;

import com.info.advice.InfoException;
import com.info.entity.Role;
import com.info.enums.ExceptionEnum;
import com.info.mapper.RoleMapper;
import com.info.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void saveRole(Role role) {
        int i = roleMapper.insert(role);
        if(i!=1){
            throw new InfoException(ExceptionEnum.ROLE_SAVE_ERROR);
        }
    }
}
