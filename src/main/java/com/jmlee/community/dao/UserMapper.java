package com.jmlee.community.dao;

import com.jmlee.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User selectById(Integer id);
    User selectByName(String username);
    User selectByEmail(String email);

    int insertUser(User user);
    int updateStatus(@Param("id") Integer id,@Param("status") Integer status);
    int updateHeader(@Param("id") Integer id, @Param("headerUrl") String headerUrl);
    int updatePassword(@Param("id") Integer id, @Param("password") String password);


}
