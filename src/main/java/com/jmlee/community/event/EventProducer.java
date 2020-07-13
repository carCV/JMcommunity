package com.jmlee.community.event;

import com.alibaba.fastjson.JSONObject;
import com.jmlee.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description 事件生产者
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event) {
        // 将Event对象转换为JSON字符串进行传输
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }


}
