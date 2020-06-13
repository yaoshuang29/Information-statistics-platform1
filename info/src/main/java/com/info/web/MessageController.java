package com.info.web;

import com.info.advice.PageResult;
import com.info.entity.InfoTemplate;
import com.info.entity.Message;
import com.info.mapper.MessageMapper;
import com.info.service.MessageService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 保存发布的信息
     * @param msg
     * @return
     */
    @PostMapping("release")
    public ResponseEntity<Void> releaseInfo(HttpSession session, @RequestParam("msg")String msg){
        Long id = (Long) session.getAttribute("id");
        messageService.releaseInfo(msg,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<Message>> findAll(HttpSession session){
        //当前学生身份id
        Long id = (Long) session.getAttribute("id");
        return ResponseEntity.ok(messageService.findAllMessageById(id));
    }

    @GetMapping("info_template")
    public ResponseEntity<InfoTemplate> getInfoByUid(HttpSession session){
        Long uid = (Long) session.getAttribute("id");
         return ResponseEntity.ok(messageService.getInfoByUid(uid));
    }
}
