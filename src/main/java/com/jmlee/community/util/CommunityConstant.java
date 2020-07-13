package com.jmlee.community.util;

import org.apache.kafka.common.protocol.types.Field;

import javax.xml.transform.sax.SAXTransformerFactory;

/**
 * 定义一个常量接口
 */
public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认状态的登录凭证超时时间：12h
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住我状态下的登录凭证超时时间: 100d
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 实体类型：帖子
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * 实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;


    /**
     * 实体类型：用户
     */
    int ENTITY_TYPE_USER = 3;

    /**
     * kafka主题：评论
     */
    String TOPIC_COMMENT = "comment";

    /**
     * kafka主题：点赞
     */
    String TOPIC_LIKE = "like";

    /**
     * kafka主题：关注
     */
    String TOPIC_FOLLOW = "follow";

    /**
     * 系统用户ID
     */
    int SYSTEM_USER_ID = 1;
}
