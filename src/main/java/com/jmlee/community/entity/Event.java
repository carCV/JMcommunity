package com.jmlee.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装触发事件
 * @author jmLee
 */
public class Event {

    private String topic;
    private int userId;   // 触发事件的用户Id
    private int entityType;
    private int entityId;
    private int entityUserId;  // 实体拥有者Id
    private Map<String, Object> data = new HashMap<>();  // 设计预留，方便后续扩展

    public String getTopic() {
        return topic;
    }

    // 这里的setter做了一些改造，方便链式编程
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    // 这里对传入的参数做了一些改造
    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
