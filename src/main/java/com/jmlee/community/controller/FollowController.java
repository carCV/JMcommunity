package com.jmlee.community.controller;

import com.jmlee.community.entity.Event;
import com.jmlee.community.entity.Page;
import com.jmlee.community.entity.User;
import com.jmlee.community.event.EventProducer;
import com.jmlee.community.service.FollowService;
import com.jmlee.community.service.UserService;
import com.jmlee.community.util.CommunityConstant;
import com.jmlee.community.util.CommunityUtil;
import com.jmlee.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description 关注相关
 * @Author jmlee
 * @Date 2020/5/17 20:12
 * @Version 1.0
 */
@Controller
public class FollowController implements CommunityConstant{

    @Autowired
    private FollowService followService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private EventProducer eventProducer;

    /**
     * 关注
     * @param entityType
     * @param entityId
     * @return
     */
    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType, int entityId) {

        // 需要登录才能进行关注操作，所以先到hostHolder中查看用户是否已登录
        User user = hostHolder.getUser();

        followService.follow(user.getId(), entityType, entityId);

        // 触发关注事件
        Event event = new Event()
                .setTopic(TOPIC_FOLLOW)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setEntityUserId(entityId);
        eventProducer.fireEvent(event);

        return CommunityUtil.getJSONString(0, "已关注");
    }


    /**
     * 取消关注
     * @param entityType
     * @param entityId
     * @return
     */
    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType, int entityId) {

        // 需要登录才能进行取关操作，所以先到hostHolder中查看用户是否已登录
        User user = hostHolder.getUser();

        followService.unfollow(user.getId(), entityType, entityId);

        return CommunityUtil.getJSONString(0, "已取消关注");
    }

    /**
     * 获取某一用户的关注列表
     * @param userId  待查看的用户Id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(path = "/followees/{userId}", method = RequestMethod.GET)
    public String getFollowees(@PathVariable("userId") int userId, Page page, Model model) {

        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }

        model.addAttribute("user", user);

        page.setLimit(5);
        page.setPath("/followees/" + userId);
        page.setRows((int) followService.findFolloweeCount(userId, ENTITY_TYPE_USER));

        List<Map<String, Object>> userList = followService.findFollowees(userId, page.getOffset(), page.getLimit());
        if (userList != null) {
            for (Map<String, Object> map : userList) {
                User u = (User) map.get("user");
                map.put("hasFollowed", hasFollowed(u.getId()));
            }
        }

        model.addAttribute("users", userList);

        return "/site/followee";
    }

    /**
     * 获取某一用户的粉丝列表
     * @param userId  待查看的用户Id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(path = "/followers/{userId}", method = RequestMethod.GET)
    public String getFollowers(@PathVariable("userId") int userId, Page page, Model model) {

        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }

        model.addAttribute("user", user);

        page.setLimit(5);
        page.setPath("/followers/" + userId);
        page.setRows((int) followService.findFollowerCount(ENTITY_TYPE_USER,userId));

        List<Map<String, Object>> userList = followService.findFollowers(userId, page.getOffset(), page.getLimit());
        if (userList != null) {
            for (Map<String, Object> map : userList) {

                // map中实际包含user和followTime两种key
                User u = (User) map.get("user");
                map.put("hasFollowed", hasFollowed(u.getId()));
            }
        }

        model.addAttribute("users", userList);

        return "/site/follower";
    }


    /**
     * 是否已关注
     * @param userId 待查看的用户Id
     * @return
     */
    private boolean hasFollowed(int userId) {
        if (hostHolder.getUser() == null) {
            // 当用户未登录时，即以游客的身份查看关注或粉丝列表显示的用户都是未关注状态，所有返回false
            return false;
        }
        return followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
    }
}
