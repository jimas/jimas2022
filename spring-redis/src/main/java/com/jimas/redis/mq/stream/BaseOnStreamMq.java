package com.jimas.redis.mq.stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jimas.redis.mq.MsgQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class BaseOnStreamMq implements MsgQueue, StreamListener<String, Record<String, String>> {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void produce(String topic, String msg) {
        final ObjectRecord<String, String> objectRecord = StreamRecords.newRecord()
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
    public void onMessage(Record<String, String> message) {
        log.info("message id:{}", message.getId());
        log.info("message stream:{}", message.getStream());
        final JSONObject json = (JSONObject) JSON.toJSON(message.getValue());
        log.info(json.get("payload").toString());
    }


}
