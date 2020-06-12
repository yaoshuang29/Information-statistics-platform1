package com.info.service;

import com.info.entity.Classes;

import java.util.List;

public interface ClassesService {
    //根据班级名称查询班级
     Classes findClassesByClassName(String className);

    //查询所有班级
    List<Classes> findAll();
}
