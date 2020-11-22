package com.lee.myblog.controller;

import com.lee.myblog.dao.PackageDao;
import com.lee.myblog.dao.UsersDao;
import com.lee.myblog.pojo.Packages;
import com.lee.myblog.pojo.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.rmi.server.UID;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private PackageDao packageDao;

    @PostMapping(value = "/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpSession session){
        User user = usersDao.getUserByUname(username);
        if(user!=null&&user.getPassword().equals(password)){
            session.setAttribute("uid",user.getUid());
            session.setAttribute("uname",user.getUname());
            return "redirect:/toAlist_1?uid="+user.getUid()+"&&pagenumb=0";
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
            model.addAttribute("info",0);
            List<Packages> allPackages = packageDao.getTheAllPackage();
            List<User> users = usersDao.getAllUsers();
            int pid = 1;
            int uid = 1;
            for(Packages p:allPackages){
                if(p.getPid()==pid){
                    pid++;
                }else {
                    break;
                }
            }
            for(User user:users){
                if(uid==user.getUid()){
                    uid++;
                }else{
                    break;
                }
            }
            Packages packages = new Packages(pid,"默认包", uid);
            packageDao.saveNewPackage(packages);
            usersDao.insertUser(uid,uname,password);
            return "index";
        }
        else {
            model.addAttribute("info",1);
            return "Register";
        }
    }

}
