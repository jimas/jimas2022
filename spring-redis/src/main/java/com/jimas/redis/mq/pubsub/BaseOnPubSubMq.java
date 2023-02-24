package com.jimas.redis.mq.pubsub;

import com.jimas.redis.mq.MsgQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class BaseOnPubSubMq extends MessageListenerAdapter implements MsgQueue {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void produce(String topic, String msg) {
//        redisTemplate.convertAndSend(topic, msg);
    }

    @Override
    public String consumer(String topic) {

        return null;
    }

    @Override
    public String keyPrefix() {
        return "pub_sub_";
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //value serializer
        Object msg = redisTemplate.getValueSerializer().deserialize(message.getBody());
        String topic = redisTemplate.getStringSerializer().deserialize(message.getChannel());
        log.info("TOPIC:{},MSG:{}", topic, msg);


    }
}
