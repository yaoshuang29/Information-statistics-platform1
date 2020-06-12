package com.info.service;

import com.info.advice.PageResult;
import com.info.entity.InfoTemplate;

public interface InfoTemplateService {
    //查询所有模版信息
    PageResult<InfoTemplate> findAll(Integer page, Integer size);

    //保存模版信息
    void saveTemplate(InfoTemplate infoTemplate);

    //根据id查询详细信息
    InfoTemplate findById(Long id);

    //根据id删除模版信息
    void deleteById(Long id);

    //根据id更新模版使用状态
    void useTemplate(Long id,Boolean flag);

    //填写上报信息
    InfoTemplate reportFillOut( Long mid);
}
