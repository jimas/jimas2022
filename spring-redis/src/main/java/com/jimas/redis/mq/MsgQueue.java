package com.jimas.redis.mq;

/**
 * @author liuqj
 */
public interface MsgQueue {
    /**
     * 生产消息
     *
     * @param topic 主题
     * @param msg   消息
     */
    void produce(String topic, String msg);

    /**
     * 消费消息
     *
     * @param topic
     * @return
     */
    String consumer(String topic);

    /**
     * key 前缀
     *
     * @return
     */
    String keyPrefix();
}
