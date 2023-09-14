package com.jimas.redis.controller;

import com.alibaba.fastjson.JSON;
import com.jimas.redis.mq.MsgQueue;
import com.jimas.redis.mq.entity.Msg;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("rest")
    @Async
    public void test01() {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                String res = restTemplate.getForObject("https://pine-monitor-prod.mob.com/health", String.class);
                System.out.println(res);
            }).start();
        }
    }

    @RequestMapping("produce")
    public void test02() {

        for (int i = 0; i < 10; i++) {
            final Msg msg = new Msg(UUID.randomUUID().toString(), "\"sas中撒是否.");
            for (MsgQueue msgQueue : msgQueues) {
                msgQueue.produce(msgQueue.keyPrefix() + "1", JSON.toJSONString(msg));
            }
        }

    }
}
