package com.jmlee.community.service;

import com.jmlee.community.dao.UserMapper;
import com.jmlee.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(Integer id) {

        return userMapper.selectById(id);
    }


}
