package com.info.mapper;

import com.info.entity.Report;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReportMapper extends Mapper<Report>, DeleteByIdListMapper<Report,Long> {

    List<Report> findAll(@Param("keyword") String keyword, @Param("date")String date, @Param("className")String className);
}
