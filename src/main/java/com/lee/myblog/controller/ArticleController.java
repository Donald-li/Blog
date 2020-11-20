package com.lee.myblog.controller;

import com.lee.myblog.dao.ArticleDao;
import com.lee.myblog.dao.PackageDao;
import com.lee.myblog.pojo.Article;
import com.lee.myblog.pojo.Packages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/AManage")
public class ArticleController {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private PackageDao packageDao;

    @PostMapping("/deleteArticleById")
    @ResponseBody
    public String deleteArticleById(@RequestParam("aid") int aid,
                                    @RequestParam("pid") int pid){
        articleDao.deleteArticleByAid(aid);
        List<Article> articles = articleDao.getAllArticlesByPid(pid);
        return "success";
    }

    @ResponseBody
    @GetMapping("/addpackage")
    public String addPackage(@RequestParam("pname") String pname,
                             @RequestParam("uid") int uid){
        //以下实现动态添加pid
        List<Packages> allPackage = packageDao.getAllPackage(uid);
        List<Integer> ids = new ArrayList<>();
        int pid = 0;
        for(Packages p:allPackage){
            ids.add(p.getPid());
        }
        for(int i=1;i<=ids.size();i++){
            if(!ids.contains(i)){
                pid = i;
                break;
            }else if(i==ids.size()){
                pid = i+1;
                break;
            }
        }

        Packages packages = new Packages(pid,pname,uid);
        packageDao.saveNewPackage(packages);
        return "添加成功！";
    }

    @ResponseBody
    @GetMapping("/doupdate")
    public String doupdate(@RequestParam("aid") int aid,
                           @RequestParam("pid") int pid,
                           @RequestParam("uid") int uid,
                           @RequestParam("text") String text,
                           @RequestParam("title") String title,
                           @RequestParam("info") String info){

        Article article = new Article(aid,pid,uid,text,new Date(),title,info);
        articleDao.updateArticle(article);

        return "修改成功！";
    }

    @ResponseBody
    @RequestMapping("/updatePackage")
    public String updatePackage(@RequestParam("pid") int pid,
                                @RequestParam("pname") String pname){
        Packages packages = packageDao.getPackageById(pid);
        packages.setPname(pname);
        packageDao.updatePackage(packages);
        return "修改成功！";
    }

    @ResponseBody
    @RequestMapping("/deletePackage")
    public String deletePackage(@RequestParam("pid") int pid,
                                @RequestParam("deleteArticles") boolean deleteArticles){
        List<Article> articles = articleDao.getAllArticlesByPid(pid);
        if(deleteArticles){
            for(Article article:articles){
                articleDao.deleteArticleByAid(article.getAid());
            }
            packageDao.deletePackage(pid);
        }else {
            for(Article article:articles){
                article.setPid(1);
                articleDao.updateArticle(article);
            }
            packageDao.deletePackage(pid);
        }
        return "删除成功！";
    }

    @RequestMapping("/search")
    public String searchforArticles(@RequestParam("searchtext") String searchtext,
                                           Model model){
        List<Article> articles = articleDao.searchArticles(searchtext);
        model.addAttribute("articles",articles);

        return "SearchArticles";
    }

}
