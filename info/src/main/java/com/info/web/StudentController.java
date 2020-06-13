package com.info.web;

import com.info.advice.PageResult;
import com.info.entity.Classes;
import com.info.entity.Student;
import com.info.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    /**
     * 分页以及过滤查询学生信息
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<PageResult<Student>> findAll(@RequestParam("page")Integer page,
                                                       @RequestParam("size")Integer size,
                                                       @RequestParam("keyword")String keyword
                                                       ){
        return ResponseEntity.ok(studentService.findAll(page,size,keyword));
    }


/**
 * 切换用户状态
 * @param flag
 * @return
 */
    @PutMapping("switch/{flag}/{uid}")
    public ResponseEntity<Void> stateSwitch(@PathVariable("flag")String flag,
                                            @PathVariable("uid")Long uid
                                            ){
        studentService.stateSwitch(flag,uid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


/**
 * 删除用户
 * @param sid
 * @return
 */
    @DeleteMapping("{uid}")
    public ResponseEntity<Void> deleteById(@PathVariable("uid")Long sid){
        studentService.deleteById(sid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
