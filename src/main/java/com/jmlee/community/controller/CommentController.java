package com.jmlee.community.controller;

import com.jmlee.community.entity.Comment;
import com.jmlee.community.entity.DiscussPost;
import com.jmlee.community.entity.Event;
import com.jmlee.community.event.EventProducer;
import com.jmlee.community.service.CommentService;
import com.jmlee.community.service.DiscussPostService;
import com.jmlee.community.util.CommunityConstant;
import com.jmlee.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @Description 评论、回复相关
 * @Author jmlee
 * @Date 2020/5/1 15:07
 * @Version 1.0
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    // 提交评论
    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment) {

        comment.setUserId(hostHolder.getUser().getId());
        // 默认评论为有效
        comment.setStatus(0);
        comment.setCreateTime(new Date());

        commentService.addComment(comment);

        // 触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", discussPostId);

        // 如果是针对帖子的评论
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        // 如果是针对评论的评论
        else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }

        eventProducer.fireEvent(event);


        return "redirect:/discuss/detail/" + discussPostId;
    }

}
