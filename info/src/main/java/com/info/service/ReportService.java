package com.info.service;

import com.info.advice.PageResult;
import com.info.entity.Report;

public interface ReportService {

    //模糊查询
    PageResult<Report> findAll(Integer page, Integer size, String keyword, String date, String className);
}
