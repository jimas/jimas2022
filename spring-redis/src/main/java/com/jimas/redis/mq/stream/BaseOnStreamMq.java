package com.jimas.redis.mq.stream;

import com.alibaba.fastjson.JSON;
import com.jimas.redis.mq.MsgQueue;
import com.jimas.redis.mq.entity.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class BaseOnStreamMq implements MsgQueue, StreamListener<String, Record<String, Map<String, String>>> {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void produce(String topic, String msg) {
        final Record<String, String> objectRecord = StreamRecords.newRecord()
                .in(topic).ofObject(msg)
                .withId(RecordId.autoGenerate());

        final RecordId recordId = redisTemplate.opsForStream().add(objectRecord);
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


    @Override
    public void onMessage(Record<String, Map<String, String>> message) {
        try {
            log.info("message id:{}", message.getId());
            log.info("message stream:{}", message.getStream());
            log.info("message body:{}", message.getValue());
            String payload = message.getValue().get("payload");
            payload = payload.replace("\\", "");
            payload = payload.replace("\"{", "{");
            payload = payload.replace("}\"", "}");
            final Msg msg = JSON.parseObject(payload, Msg.class);
            System.out.println(msg.getVal());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
