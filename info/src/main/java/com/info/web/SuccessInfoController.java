package com.info.web;

import com.info.advice.PageResult;
import com.info.entity.InfoTemplate;
import com.info.entity.Report;
import com.info.entity.SuccessInfo;
import com.info.service.ReportService;
import com.info.service.SuccessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("success")
public class SuccessInfoController {

    @Autowired
    private SuccessInfoService successInfoService;

    @Autowired
    private ReportService reportService;

    @PostMapping("/successInfo")
    public ResponseEntity<Void> addSuccessInfo(@RequestBody SuccessInfo info, HttpSession session) {

        Long id = (Long) session.getAttribute("id");
        Long mid = (Long) session.getAttribute("mid");
        successInfoService.addSuccessInfo(info, id, mid);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 分页过滤 学生上报信息
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<SuccessInfo>> findAll(@RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size,
                                                           @RequestParam("keyword") String keyword,
                                                           HttpSession session,
                                                           @RequestParam("date")String date
    ) {

        Long tid = (Long) session.getAttribute("id");

        return ResponseEntity.ok(successInfoService.findAll(page, size, keyword, tid,date));

    }

    /**
     * 查询填写信息详情
     * @param suid
     * @param sid
     * @param mv
     * @return
     */
    @RequestMapping("/detailSuccess")
    public ModelAndView detail(@RequestParam("suid") Long suid,
                               @RequestParam("sid") Long sid,
                               ModelAndView mv
    ) {
        SuccessInfo successInfo = successInfoService.findDetailById(suid, sid);
        mv.addObject("success", successInfo);
        mv.setViewName("info-statistical-detail");
        return mv;
    }


    /**
     * 打回学生填写的信息
     * @param sid
     * @param mid
     * @param info_id
     * @return
     */
    @DeleteMapping("/taskTo/{sid}/{mid}/{info_id}")
    public ResponseEntity<Void> taskIntoStudent(@PathVariable("sid")Long sid,
                                                @PathVariable("mid")Long mid,
                                                @PathVariable("info_id")Long info_id
    ){
         successInfoService.taskIntoStudent(sid,mid,info_id);
         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    /**
     * 确定学生填写的信息
     * @param info_id
     * @param sid
     * @return
     */
    @RequestMapping("confirm")
    public ResponseEntity<Void> confirmInfo(@RequestParam("info_id")Long info_id,
                                    @RequestParam("sid")Long sid
                                    ){
       successInfoService.confirmInfo(info_id, sid);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
