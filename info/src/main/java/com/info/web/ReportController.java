package com.info.web;

import com.info.advice.PageResult;
import com.info.entity.Report;
import com.info.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("list")
    public ResponseEntity<PageResult<Report>> findAll(@RequestParam("page")Integer page,
                                                      @RequestParam("size")Integer size,
                                                      @RequestParam("keyword")String keyword,
                                                      @RequestParam("date")String date,
                                                      @RequestParam("className")String className
                                                      ){
        return ResponseEntity.ok(reportService.findAll(page,size,keyword,date,className));

    }
}
