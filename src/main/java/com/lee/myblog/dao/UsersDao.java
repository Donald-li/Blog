package com.lee.myblog.dao;

import com.lee.myblog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UsersDao {
    @Select("select * from myblog_user")
    public List<User> getAllUsers();

    @Select("select * from myblog_user where uid = #{uid}")
    public User getuserbyid(Integer uid);

    @Select("select * from myblog_user where uname = #{uname}")
    public User getUserByUname(String uname);

    @Insert("insert into myblog_user(uid,uname,password) values(#{uid},#{uname},#{password})")
    public void insertUser(int uid,String uname,String password);
}
