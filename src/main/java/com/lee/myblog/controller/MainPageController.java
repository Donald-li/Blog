package com.lee.myblog.controller;

import com.lee.myblog.dao.ArticleDao;
import com.lee.myblog.dao.PackageDao;
import com.lee.myblog.pojo.Article;
import com.lee.myblog.pojo.Packages;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainPageController {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    PackageDao packageDao;

    @RequestMapping("/main/page")
    public String toMainPage(){
        return "mainPage";
    }

    //新建文章页面导航
    @RequestMapping("/mark2")
    public String tomark2(@RequestParam("uid")int uid,
                          Model model){
        List<Packages> packages = packageDao.getAllPackage(uid);
        model.addAttribute("packages",packages);
        return "MarkDown2";
    }

    //用于显示新建文章
    @GetMapping("/getText")
    public String getText(int aid,Model model) {
        Article article = articleDao.getArticleByAid(aid);
        model.addAttribute("article", article);
        return "showMarkDown";
    }

    //导航页导航到文章页面
    @GetMapping("/toAlist_1")
    public String toArtList(@RequestParam("pagenumb") int pagenumb,
                            @RequestParam("uid") int uid,
                            Model model) {
        List<Packages> packages = packageDao.getAllPackage(uid);
        int pid_default = 0;
        for(Packages p:packages){
            if(p.getPname().equals("默认包")){
                pid_default= p.getPid();
            }
        }
        String pname = packageDao.getPackageById(pid_default).getPname();
        List<Article> articles = articleDao.getArticlesByPageAndPid(uid,pid_default,pagenumb*6,6);
        List<Article> list = articleDao.getAllArticlesByPid(pid_default);
        int size = list.size();
        //用来储存最大页数
        int subsize = size / 6;
        if(size%6>0){
            subsize++;
        }
        model.addAttribute("pid",pid_default);
        model.addAttribute("maxpage",subsize);
        model.addAttribute("nowpage",pagenumb);
        model.addAttribute("articles", articles);
        model.addAttribute("packages",packages);
        model.addAttribute("pname",pname);
        return "Article_bote_List";
    }

    //文章浏览页面导航
    @GetMapping("/toAlist")
    public String toArtList(@RequestParam("pid") int pid,
                            @RequestParam("pagenumb") int pagenumb,
                            @RequestParam("uid") int uid,
                            Model model) {
        List<Packages> packages = packageDao.getAllPackage(uid);
        String pname = packageDao.getPackageById(pid).getPname();
        List<Article> articles = articleDao.getArticlesByPageAndPid(uid,pid,pagenumb*6,6);
        List<Article> list = articleDao.getAllArticlesByPid(pid);
        int size = list.size();
        //用来储存最大页数
        int subsize = size / 6;
        if(size%6>0){
            subsize++;
        }
        model.addAttribute("pid",pid);
        model.addAttribute("maxpage",subsize);
        model.addAttribute("nowpage",pagenumb);
        model.addAttribute("articles", articles);
        model.addAttribute("packages",packages);
        model.addAttribute("pname",pname);
        return "Article_bote_List";
    }

    //文章编辑页面导航
    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam("aid") int aid,
                           @RequestParam("uid") int uid,
                           Model model){
        List<Packages> allPackage = packageDao.getAllPackage(uid);
        Article article = articleDao.getArticleByAid(aid);
        model.addAttribute("initArticle",article);
        model.addAttribute("packages",allPackage);
        return "MarkDown1";
    }

    //导航条导航包及文章管理页面
    @RequestMapping("/toPManage_1")
    public String toPackageManager(@RequestParam("pagenumb") int pagenumb,
                                   @RequestParam("uid") int uid,
                                   Model model){
        Map<String,Integer> map = new HashMap<>();
        List<Packages> allPackage = packageDao.getAllPackage(uid);
        int maxpage = 0;
        int pid = allPackage.get(0).getPid();

        //分页设计
        for (Packages p:allPackage) {
            int size = articleDao.getAllArticlesByPid(p.getPid()).size();
//            map.put("psize"+p.getPid(),size);
            if(p.getPid()==pid){
                if(size<=10){
                    maxpage = 1;
                }else {
                    maxpage = size/10;
                    if(maxpage%10>0){
                        maxpage++;
                    }
                }
            }
        }
        List<Article> articlesByPageAndPid = articleDao.getArticlesByPageAndPid(uid,pid,pagenumb*10,10);
        model.addAttribute("maxpage",maxpage);
        model.addAttribute("nowpage",pagenumb);
        model.addAttribute("pid",pid);//用来指示此时所在包的id
        model.addAttribute("pname",packageDao.getPackageById(pid).getPname());//用来指示此时所在包的包名
        model.addAttribute("articles",articlesByPageAndPid);
//        model.addAttribute("map",map);
        model.addAttribute("packages",allPackage);
        return "PackageManage";
    }

    //包及文章管理页面导航
    @RequestMapping("/toPManage")
    public String toPackageManager(@RequestParam("pid") int pid,
                                   @RequestParam("pagenumb") int pagenumb,
                                   @RequestParam("uid") int uid,
                                   Model model){
        Map<String,Integer> map = new HashMap<>();
        List<Packages> allPackage = packageDao.getAllPackage(uid);
        int maxpage = 0;
        //分页设计
        for (Packages p:allPackage) {
            int size = articleDao.getAllArticlesByPid(p.getPid()).size();
//            map.put("psize"+p.getPid(),size);
            if(p.getPid()==pid){
                if(size<=10){
                    maxpage = 1;
                }else {
                    maxpage = size/10;
                    if(maxpage%10>0){
                        maxpage++;
                    }
                }
            }
        }
        List<Article> articlesByPageAndPid = articleDao.getArticlesByPageAndPid(uid,pid,pagenumb*10,10);
        model.addAttribute("maxpage",maxpage);
        model.addAttribute("nowpage",pagenumb);
        model.addAttribute("pid",pid);//用来指示此时所在包的id
        model.addAttribute("pname",packageDao.getPackageById(pid).getPname());//用来指示此时所在包的包名
        model.addAttribute("articles",articlesByPageAndPid);
//        model.addAttribute("map",map);
        model.addAttribute("packages",allPackage);
        return "PackageManage";
    }

}
