package com.info.web;

import com.info.entity.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(HttpSession session){
        //获取用户的信息保存到session中
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken))
        {
            UserInfo details = (UserInfo) authentication.getPrincipal();
            session.setAttribute("username",details.getUsername());
            session.setAttribute("id",details.getId());
            session.setAttribute("roleName",details.getRole().getRoleName());
        }
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/loginError")
    public String loginError(){
        return "loginError";
    }
    @RequestMapping("/403")
    public String authNOt(){
        return "403";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/userInfo")
    public String userInfo(){
        return "userInfo";
    }

    @RequestMapping("/infoStatistical")
    @PreAuthorize("hasRole('TEACHER')")
    public String infoStatical(){
        return "info-statistical-teacher";
    }

    @RequestMapping("/studentsManage")
    @PreAuthorize("hasRole('ADMIN')")
    public String students(){
        return "students-manage";
    }

    @RequestMapping("/releaseInfo")
    @PreAuthorize("hasRole('TEACHER')")
    public String  releaseInfo(){
        return "release-info";
    }

    @RequestMapping("/reportInfo")
    @PreAuthorize("hasRole('ADMIN')")
    public String reportInfoModel(){
        return "report-info-model";
    }

    @RequestMapping("/teacherManage")
    @PreAuthorize("hasRole('ADMIN')")
    public String teacherManage(){
        return "teachers-manage";
    }

    @RequestMapping("/report")
    @PreAuthorize("hasRole('STUDENT')")
    public String reportInfo(){
        return "report-info";
    }

    @RequestMapping("/infoStatisticalAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String infoStatisticalAdmin(){
        return "info-statistical-admin";
    }
}
