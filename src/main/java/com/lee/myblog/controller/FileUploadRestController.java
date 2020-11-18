package com.lee.myblog.controller;

import com.lee.myblog.dao.ArticleDao;
import com.lee.myblog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class FileUploadRestController {
    @Value("${img.upload-path}")
    private String uploadPath;
    @Autowired
    private ArticleDao articleDao;

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            Files.write(Paths.get(uploadPath + file.getOriginalFilename()), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://www.donaldlee.cn/img/" + file.getOriginalFilename();
    }

    @GetMapping("/getText")
    public String getText(@RequestParam("pid") int pid,
                          @RequestParam("text") String text,
                          @RequestParam("title") String title,
                          @RequestParam("info") String info){
        Date day=new Date();
        //查修所有文章，以确认id
        List<Article> articles = articleDao.getAllArticles();
        List<Article> articlesINP = articleDao.getAllArticlesByPid(pid);
        List<Integer> ids = new ArrayList();
        List<String> titles = new ArrayList();
        Article article =null;
        //遍历所有文章的id
        for (Article a:articles) {
            ids.add(a.getAid());
        }
        //遍历同包内所有的文章题目
        for (Article a:articlesINP) {
            titles.add(a.getTitle());
        }
        if(titles.contains(title)){
            return "此文章已存在，请更换标题！";
        }else if(title.equals("")||info.equals("")||text.equals("")){
            return "请完善输入！";
        }else {
            //所有AID中的最大值
            int max = Collections.max(ids);
            //记录应采用的id
            int aid = 0;
            for (int i = 1;i<=max;i++){
                if(!ids.contains(i)){
                    //如果所有id中有断点，则直接设置为断点处的id
                    aid = i;
                    break;
                }
            }
            article = new Article(aid,pid,1,text,day,title,info);
            articleDao.insertArticle(article);
            return "保存成功";
        }
    }

//    @GetMapping("/updateArticle")
//    public String updateArticle(){
//
//    }

}
