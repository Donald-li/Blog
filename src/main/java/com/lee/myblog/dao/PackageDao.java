package com.lee.myblog.dao;

import com.lee.myblog.pojo.Packages;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PackageDao {
    @Select("select * from package")
    List<Packages> getTheAllPackage();

    @Select("select * from package where uid = #{uid}")
    List<Packages> getAllPackage(int uid);

    @Select("select * from package where pid = #{pid}")
    Packages getPackageById(int pid);

    @Insert("insert into package(pid,pname,uid) values(#{pid},#{pname},#{uid})")
    void saveNewPackage(Packages packages);

    @Update("update package set pname = #{pname} where pid = #{pid}")
    void updatePackage(Packages packages);

    @Delete("delete from package where pid = #{pid}")
    void deletePackage(int pid);

}
