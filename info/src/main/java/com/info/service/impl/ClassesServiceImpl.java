package com.info.service.impl;

import com.info.advice.InfoException;
import com.info.entity.Classes;
import com.info.enums.ExceptionEnum;
import com.info.mapper.ClassesMapper;
import com.info.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Classes findClassesByClassName(String className) {
        Classes classes=new Classes();
        classes.setClassName(className);
        Classes classes1 = classesMapper.selectOne(classes);
        if(classes1==null)
        {
            throw new InfoException(ExceptionEnum.CLASSES_NOT_FOUND);
        }
        return classes1;
    }

    @Override
    public List<Classes> findAll() {
        List<Classes> classes = classesMapper.selectAll();
        if(CollectionUtils.isEmpty(classes))
        {
            throw new InfoException(ExceptionEnum.CLASSES_NOT_FOUND);
        }
        return classes;
    }
}
