package com.lee.myblog;

import com.lee.myblog.dao.ArticleDao;
import com.lee.myblog.dao.UsersDao;
import com.lee.myblog.pojo.Article;
import com.lee.myblog.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    UsersDao usersDao;
    @Autowired
    ArticleDao articleDao;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();

    }

    @Test
    public void daoseletetest(){
        List<User> allUsers = usersDao.getAllUsers();
        for(User user:allUsers){
            System.out.println(user.getUname());
        }
    }

    @Test
    public void articletest(){
        List<Article> articles = articleDao.getAllArticles();
        for(Article article:articles){
            System.out.println(article.getAid());
        }
    }

}
