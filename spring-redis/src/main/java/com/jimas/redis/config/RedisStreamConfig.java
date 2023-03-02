package com.jimas.redis.config;

import com.jimas.redis.mq.stream.BaseOnStreamMq;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Date: 2022/5/11
 * Time: 21:52
 * redis 的 stream 实现消息队列
 * <p>要求redis版本5.0以上，并且 spring-data-redis jedis版本并没有实现stream</p>
 *
 * @author jimas
 * @since redis 5.0+
 */
@Configuration
public class RedisStreamConfig {
    @Resource
    private BaseOnStreamMq streamMq;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    public static final String GROUP = "stream_group";

    /**
     * xreadgroup group stream_group namess block 10 count 10 streams stream_1 >
     * @return
     */
    @Bean
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions() {
        return StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                //batchSize 对应xreadgroup 中的 count，标识一次最多读10条
                .batchSize(10)
                //.executor() 慎用 外部线程池  防止 消费者饥饿（因为普通的外部线程池都有队列，多余的任务会扔进队列，导致队列中的消费者 得不到执行，因为StreamPollTask 为长轮询，不会消亡，一直占用线程）
                .errorHandler(t -> {
                    t.printStackTrace();
                })
                // 对应 对应xreadgroup 中的 block
                .pollTimeout(Duration.ofSeconds(1))
                .build();
    }

    @Bean
    public StreamMessageListenerContainer streamMessageListenerContainer(RedisConnectionFactory factory,
                                                                         StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions) {
        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory,
                streamMessageListenerContainerOptions);
        listenerContainer.start();
        return listenerContainer;
    }

    /**
     * 订阅者1，消费组group1，收到消息后自动确认，与订阅者2为竞争关系，消息仅被其中一个消费
     *
     * @param streamMessageListenerContainer
     * @return
     * @see org.springframework.data.redis.stream.StreamPollTask#doLoop()
     */
    @Bean
    public Subscription subscription(StreamMessageListenerContainer streamMessageListenerContainer) {
        createGroup("stream_1", GROUP);
        Predicate<Throwable> predicate = t -> false;
        StreamMessageListenerContainer.ConsumerStreamReadRequest<String> streamReadRequest = StreamMessageListenerContainer.StreamReadRequest
                .builder(StreamOffset.create("stream_1", ReadOffset.lastConsumed()))
                .consumer(Consumer.from(GROUP, "name1"))
                .autoAcknowledge(true)
                //出现异常 是否取消,不取消
                .cancelOnError(predicate)
                .build();
        return streamMessageListenerContainer.register(streamReadRequest, streamMq);
//        return streamMessageListenerContainer.receiveAutoAck(
//                Consumer.from(GROUP, "name1"),
//                StreamOffset.create("stream_1", ReadOffset.lastConsumed()),
//                streamMq
//        );
    }


    private void createGroup(String stream, String group) {
        boolean exist = false;
        if (redisTemplate.hasKey(stream)) {
            final StreamInfo.XInfoGroups infoGroups = redisTemplate.opsForStream().groups(stream);
            final Iterator<StreamInfo.XInfoGroup> iterator = infoGroups.iterator();
            while (iterator.hasNext()) {
                if (Objects.equals(iterator.next().groupName(), group)) {
                    exist = true;
                }
            }
        }
        if (!exist) {
            redisTemplate.opsForStream().createGroup(stream, group);
        }

    }
}
