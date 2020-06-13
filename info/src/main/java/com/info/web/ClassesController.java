package com.info.web;

import com.info.entity.Classes;
import com.info.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("classes")
public class ClassesController {

    @Autowired
    private ClassesService classesService;

    /**
     * 查询所有班级
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Classes>> findAll(){
            return ResponseEntity.ok(classesService.findAll());
    }


    @GetMapping("one/{className}")
    public ResponseEntity<Classes> findClassesByClassName(@PathVariable("className")String className){
        return ResponseEntity.ok(classesService.findClassesByClassName(className));
    }

}
