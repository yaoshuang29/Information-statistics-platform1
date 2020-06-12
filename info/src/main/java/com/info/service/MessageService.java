package com.info.service;

import com.info.entity.InfoTemplate;
import com.info.entity.Message;

import java.util.List;

public interface MessageService {
    //保存发布的信息提示
    void releaseInfo(String msg,Long id);

    //查询当前学生的提示信息
    List<Message> findAllMessageById(Long id);

    //查询用户当前使用的模版信息
    InfoTemplate getInfoByUid(Long uid);
}
