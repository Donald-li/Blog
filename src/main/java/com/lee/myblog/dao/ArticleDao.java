package com.lee.myblog.dao;

import com.lee.myblog.pojo.Article;
import org.apache.ibatis.annotations.*;
import sun.awt.SunHints;

import java.util.List;

@Mapper
public interface ArticleDao {

    /**
     * 查询所有文章
     * @return list
     */
    @Select("select * from article")
    List<Article> getAllArticles();

    /**
     * 查询某一个包内的所有文章
     * @param pid：包id
     * @return
     */
    @Select("select * from article where pid = #{pid}")
    List<Article> getAllArticlesByPid(int pid);

    /**
     * 根据AID查询文章
     * @param aid：文章ID
     * @return
     */
    @Select("select * from article where aid = #{aid}")
    Article getArticleByAid(int aid);

    @Select("select * from article where uid = #{uid} and pid = #{pid} limit #{row},#{num}")
    List<Article> getArticlesByPageAndPid(int uid,int pid,int row,int num);

    @Select("select * from article where (title like '%${text}%') or (info like '%${text}%') or (date like '%${text}%') ")
    List<Article> searchArticles(String text);

    /**
     * 插入新的文章
     * @param article：文章对象
     */
    @Insert("insert into article(aid,pid,uid,content,date,title,info) values(#{aid},#{pid},#{uid},#{content},#{date},#{title},#{info})")
    void insertArticle(Article article);

    /**
     * 根据AID删除文章
     * @param aid：文章ID
     */
    @Delete("delete from article where aid = #{aid}")
    void deleteArticleByAid(int aid);

    @Update("update article " +
            "set pid = #{pid},content = #{content},date = #{date},title = #{title},info = #{info} where aid = #{aid}")
    void updateArticle(Article article);

}
