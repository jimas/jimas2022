package com.jimas.redis.mq.list;

import com.jimas.redis.mq.MsgQueue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * https://blog.csdn.net/weixin_40918067/article/details/116582463
 * 基于 list 类型实现的消息队列
 * lpush-brpop(rpush-blpop)
 *
 * @author liuqj
 */
//@Component
public class BasedOnListMq implements MsgQueue {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void produce(String topic, String msg) {
        redisTemplate.opsForList().leftPush(topic, msg);
    }

    @Override
    public String consumer(String topic) {
        return redisTemplate.opsForList().rightPop(topic);
    }

    @Override
    public String keyPrefix() {
        return "list_";
    }
}
