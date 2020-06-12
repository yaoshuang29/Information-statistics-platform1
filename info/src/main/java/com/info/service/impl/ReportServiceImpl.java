package com.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.advice.InfoException;
import com.info.advice.PageResult;
import com.info.entity.Report;
import com.info.enums.ExceptionEnum;
import com.info.mapper.ReportMapper;
import com.info.service.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public PageResult<Report> findAll(Integer page, Integer size, String keyword, String date, String className) {
        //分页助手
        PageHelper.startPage(page,size);
        if (StringUtils.isEmpty(keyword)) {
                keyword="%%";
        } else {
            keyword="%"+keyword+"%";
        }
        if (StringUtils.isEmpty(date)) {
            date="%%";
        } else {
            date="%"+date+"%";
        }
        if (StringUtils.isEmpty(className)) {
            className="%%";
        } else {
            className="%"+className+"%";
        }
        //查询
        List<Report> reportList= reportMapper.findAll(keyword,date,className);
        if(CollectionUtils.isEmpty(reportList))
        {
            throw new InfoException(ExceptionEnum.SUCCESS_INFO_NOT_FOUND);
        }
        //封装分页结果,并返回
        PageInfo<Report> pageInfo=new PageInfo<Report>(reportList);
        return new PageResult<Report>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
    }
}
