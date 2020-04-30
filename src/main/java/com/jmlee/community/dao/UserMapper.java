package com.jmlee.community.dao;

import com.jmlee.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User selectById(int id);
    User selectByName(String username);
    User selectByEmail(String email);

    Integer insertUser(User user);
    Integer updateStatus(@Param("id") int id,@Param("status") int status);
    Integer updateHeader(@Param("id") int id, @Param("headerUrl") String headerUrl);
    Integer updatePassword(@Param("id") int id, @Param("password") String password);


}
