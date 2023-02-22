package com.jimas.redis.controller;

import com.alibaba.fastjson.JSONObject;
import com.jimas.redis.mq.MsgQueue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author liuqj
 */
@RestController
public class MessageController {
    @Resource
    private List<MsgQueue> msgQueues;

    @RequestMapping("produce")
    public void test01() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UUID", UUID.randomUUID().toString());
        for (MsgQueue msgQueue : msgQueues) {
            msgQueue.produce(msgQueue.keyPrefix() + "1", jsonObject.toString());
        }

    }
}
