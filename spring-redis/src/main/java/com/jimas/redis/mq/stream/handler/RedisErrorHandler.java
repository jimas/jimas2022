package com.jimas.redis.mq.stream.handler;

import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * @author liuqj
 */
@Component
public class RedisErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {

        t.printStackTrace();

    }
}
