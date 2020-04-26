package com.jmlee.community.service;

import com.jmlee.community.dao.DiscussPostMapper;
import com.jmlee.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPost(Integer userId, Integer offset, Integer limit) {
        return discussPostMapper.selectDiscussPost(userId, offset, limit);
    }

    public Integer findDiscussPostRows(Integer userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
