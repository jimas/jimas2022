package com.jimas.redis.mq.stream;

import com.alibaba.fastjson.JSON;
import com.jimas.redis.mq.MsgQueue;
import com.jimas.redis.mq.entity.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStreamCommands;
import org.springframework.data.redis.connection.stream.ByteRecord;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class BaseOnStreamMq implements MsgQueue, StreamListener<String, Record<String, Map<String, String>>> {
    public static final String MESSAGE_KEY = "payload";
    /**
     * 限制 stream 大小
     */
    private static final long MAX_LEN = 50L;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void produce(String topic, String message) {
        Map<byte[], byte[]> hashMap = new HashMap<>(1);
        final RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        hashMap.put(stringSerializer.serialize(MESSAGE_KEY), stringSerializer.serialize(message));
        final ByteRecord byteRecord = StreamRecords.rawBytes(hashMap)
                .withStreamKey(stringSerializer.serialize(topic))
                .withId(RecordId.autoGenerate());
        redisTemplate.execute((RedisCallback) connection ->
                connection.xAdd(byteRecord, RedisStreamCommands.XAddOptions.maxlen(MAX_LEN))
        );
    }

    @Override
    public String consumer(String topic) {
        //TODO 待处理
        return null;
    }

    @Override
    public String keyPrefix() {
        return "stream_";
    }

    /**
     * 消息必须正确处理，自动 ack
     * 不能抛出异常，否则会导致消费者被移除 那是因为抛出异常是 默认 当前长轮询现场 被取消
     * @see org.springframework.data.redis.stream.StreamPollTask#doLoop()
     * @param message
     */
    @Override
    public void onMessage(Record<String, Map<String, String>> message) {
        try {
            count.addAndGet(1);
            log.info("message id:{}", message.getId());
            log.info("message stream:{}", message.getStream());
            log.info("message body:{}", message.getValue());
            String payload = message.getValue().get(MESSAGE_KEY);
            final Msg msg = JSON.parseObject(payload, Msg.class);
            log.info(msg.getAddr());
        } catch (Exception e) {
            log.error("onMessage fail", e);
        } finally {
            redisTemplate.opsForStream().delete(message);
            log.info("count[{}]", count.get());
        }

    }

}
