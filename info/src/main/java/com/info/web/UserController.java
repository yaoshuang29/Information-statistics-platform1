package com.info.web;

import com.info.entity.User;
import com.info.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存用户
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/addUser")
    public ResponseEntity<Void> registerUser(@RequestBody User userInfo) {
        userService.saveUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
/**
     * 根据id查询用户的详细信息
     * @param session
     * @param mv
     * @return
     */
    @RequestMapping("/userInfo")
    public ModelAndView getUserInfo(HttpSession session, ModelAndView mv){
        Long id = (Long) session.getAttribute("id");
        String roleName = (String) session.getAttribute("roleName");
        if(roleName==null)
        {
            mv.setViewName("/login");
            return mv;
        }
        User user =userService.getUserInfo(id,roleName);
        mv.addObject("user",user);
        mv.setViewName("one-Info");
        return mv;
    }

    @RequestMapping("/showUser")
    public ModelAndView showUserById(HttpSession session, ModelAndView mv){
        Long id = (Long) session.getAttribute("id");
        String roleName = (String) session.getAttribute("roleName");
        if(roleName==null)
        {
            mv.setViewName("/login");
            return mv;
        }
        User user =userService.getUserInfo(id,roleName);
        mv.addObject("user",user);
        mv.setViewName("userInfo");
        return mv;
    }
    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(HttpSession session, User user){
        String roleName = (String) session.getAttribute("roleName");
        if(roleName==null)
        {
            return "/login";
        }
        userService.updateUser(user,roleName);
        return "redirect:/user/userInfo";
    }


   /**
     * 根据id查询用户信息
     * @param uid
     * @return
     */
    @GetMapping("userId/{uid}/{roleFlag}")
    public ResponseEntity<User> findUserById(@PathVariable("uid")Long uid,@PathVariable("roleFlag")Integer roleFlag){

        return ResponseEntity.ok(userService.findUserById(uid,roleFlag));

    }



}
