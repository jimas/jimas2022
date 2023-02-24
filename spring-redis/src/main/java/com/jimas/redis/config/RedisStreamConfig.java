package com.jimas.redis.config;

import com.jimas.redis.mq.stream.BaseOnStreamMq;
import com.jimas.redis.mq.stream.handler.RedisErrorHandler;
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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 */
@Configuration
public class RedisStreamConfig {
    @Resource
    private BaseOnStreamMq streamMq;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RedisErrorHandler errorHandler;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
    public static final String GROUP = "stream_group";

    @Bean
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions() {
        return StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
//                .batchSize(10)
//                .executor(executor)
                .errorHandler(errorHandler)
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
     */
    @Bean
    public Subscription subscription(StreamMessageListenerContainer streamMessageListenerContainer) {
        createGroup("stream_1", GROUP);
        return streamMessageListenerContainer.receiveAutoAck(
                Consumer.from(GROUP, "name1"),
                StreamOffset.create("stream_1", ReadOffset.lastConsumed()),
                streamMq
        );
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
