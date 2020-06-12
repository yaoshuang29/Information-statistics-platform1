package com.info.service;

import com.info.advice.PageResult;
import com.info.entity.Report;
import com.info.entity.SuccessInfo;

public interface SuccessInfoService {
    //保存提交的信息
    void addSuccessInfo(SuccessInfo info,Long id,Long mid);

    //分页查询
    PageResult<SuccessInfo> findAll(Integer page, Integer size, String keyword, Long tid, String date);

    //查询填写信息详情
    SuccessInfo findDetailById(Long suid, Long sid);

    //打回
    void taskIntoStudent(Long sid, Long mid, Long info_id);

    //确认学生填写的信息
    void confirmInfo(Long info_id, Long sid);
}
