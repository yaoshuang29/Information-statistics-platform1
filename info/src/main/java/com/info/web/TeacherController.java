package com.info.web;


import com.info.advice.PageResult;
import com.info.entity.Teacher;
import com.info.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    /**
     * 分页模糊查询教师信息
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<Teacher>> findAll(@RequestParam("page")Integer page,
                                                       @RequestParam("size")Integer size,
                                                       @RequestParam("keyword")String keyword
                                                       ){
        return ResponseEntity.ok(teacherService.findAll(page,size,keyword));

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
        teacherService.stateSwitch(flag,uid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除用户
     * @param tid
     * @return
     */
    @DeleteMapping("{uid}")
    public ResponseEntity<Void> deleteById(@PathVariable("uid")Long tid){
        teacherService.deleteById(tid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
