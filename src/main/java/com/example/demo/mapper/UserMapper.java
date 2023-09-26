package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Select("select * from sys_user")
//    List<User> findAll();
//
//    @Insert("insert into sys_user(username, password, phone, address, nickname, email) " +
//            "values(#{username}, #{password}, #{phone}, #{address}, #{nickname}, #{email})")
//    int insert(User user);
//
//    int update(User user);
//
//    @Delete("delete from sys_user where id=#{id}")
//    int deleteById(@Param("id") Integer id);
//
//    @Select("select * from sys_user limit #{pageNum}, #{pageSize}")
//    List<User> findPage(Integer pageNum, Integer pageSize);
//
//    @Select("select count(*) from sys_user")
//    int findTotal();
}
