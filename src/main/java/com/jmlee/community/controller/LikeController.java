package com.jmlee.community.controller;

import com.jmlee.community.entity.User;
import com.jmlee.community.service.LikeService;
import com.jmlee.community.util.CommunityUtil;
import com.jmlee.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author jmlee
 * @Date 2020/5/16 20:07
 * @Version 1.0
 */
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;


    /**
     * 点赞（异步操作，页面不刷新）
     * @param entityType
     * @param entityId
     * @param entityUserId
     * @return
     */
    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId) {

        // 需要登录才能进行点赞操作，所以先到hostHolder中查看用户是否已登录
        User user = hostHolder.getUser();

        // 点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);

        // 获取点赞数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);

        // 获取用户的点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);


        return CommunityUtil.getJSONString(0, null, map);

    }





}
