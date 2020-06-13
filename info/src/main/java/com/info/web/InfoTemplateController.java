package com.info.web;

import com.info.advice.PageResult;
import com.info.entity.InfoTemplate;
import com.info.service.InfoTemplateService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/info_template")
public class InfoTemplateController {

    @Autowired
    private InfoTemplateService infoTemplateService;

    /**
     * 查询所有模版信息
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<PageResult<InfoTemplate>> findAll(@RequestParam("page")Integer page,
                                                              @RequestParam("size")Integer size){

        return ResponseEntity.ok(infoTemplateService.findAll(page,size));

    }

    /**
     * 保存模版信息
     * @param template
     * @return
     */
    @PostMapping("/saveTemplate")
    public ResponseEntity<Void> saveTemplate(@RequestBody InfoTemplate template){
            infoTemplateService.saveTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据id查询模版信息
     * @param id
     * @param mv
     * @return
     */
    @GetMapping("/findById/{id}")
    public ModelAndView  findById(@PathVariable("id")Long id, ModelAndView mv){
        InfoTemplate infoTemplate=infoTemplateService.findById(id);
        mv.getModel().put("info",infoTemplate);
        mv.setViewName("info-detail");
        return mv;
    }

    /**
     * 根据id删除模版信息
     * @param id
     * @return
     */
    @DeleteMapping("/deleteTemplate/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id")Long id){
        infoTemplateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据id更新模版使用状态
     * @param id
     * @return
     */
    @PutMapping("/useTemplate")
    public ResponseEntity<Void> useTemplate(@RequestParam("id") Long id,@RequestParam("flag") Boolean flag)
    {
        infoTemplateService.useTemplate(id,flag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping("/reportFillOut/{mid}")
    public ModelAndView reportFillOut(@PathVariable("mid")Long mid,ModelAndView mv,HttpSession session){
        Long sid = (Long) session.getAttribute("id");
        session.setAttribute("mid",mid);
        InfoTemplate infoTemplate = infoTemplateService.reportFillOut(mid);
        mv.addObject("info",infoTemplate);
        mv.setViewName("report-info-fill-out");
        return mv;
    }

}
