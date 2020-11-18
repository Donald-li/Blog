package com.lee.myblog.controller;

import com.lee.myblog.dao.UsersDao;
import com.lee.myblog.pojo.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UsersDao usersDao;

    @PostMapping(value = "/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpSession session){
        User user = usersDao.getUserByUname(username);
        if(user!=null&&user.getPassword().equals(password)){
            session.setAttribute("uid",user.getUid());
            session.setAttribute("uname",user.getUname());
            return "redirect:/toAlist?uid="+user.getUid()+"&&pid=1&&pagenumb=0";
        }else{
            return "index";
        }
    }

    //注册页面跳转
    @GetMapping("/toRegister")
    public String toregister(){
        return "Register";
    }

    //注册
    @PostMapping("/Register")
    public String Register(@RequestParam("uname")String uname,
                           @RequestParam("password")String password,
                           Model model){
        if(usersDao.getUserByUname(uname)==null) {
            usersDao.insertUser(uname,password);
            return "index";
        }
        else {
            model.addAttribute("info",1);
            return "Register";
        }
    }

}
